package com.wangyu.talents.service.impl;

import com.wangyu.talents.common.enums.StatusEnum;
import com.wangyu.talents.common.enums.UserTypeEnum;
import com.wangyu.talents.common.exception.ServiceException;
import com.wangyu.talents.common.model.ResponseCode;
import com.wangyu.talents.common.model.ResponseModel;
import com.wangyu.talents.entity.SystemUserEntity;
import com.wangyu.talents.repository.SystemUserRepository;
import com.wangyu.talents.service.SystemUserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 系统用户服务层实现
 *
 * @author wangyu
 * @Date 2019/3/9 18:01
 * @Version 1.0
 **/
@Service("userService")
public class SystemUserServiceImpl implements SystemUserService {

  @Autowired
  private SystemUserRepository userRepository;
  private static Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

  @Override
  public SystemUserEntity findByUsernameAndStatus(String account, StatusEnum status) {
    return userRepository.findByUsernameAndStatus(account, status);
  }

  @Override
  public Page<SystemUserEntity> findPages(Map<String, Object> params, Pageable pageable) {
    Page<SystemUserEntity> list = userRepository.findAll(new Specification<SystemUserEntity>() {
      @Override
      public Predicate toPredicate(Root<SystemUserEntity> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        Class<?> clazz = criteriaQuery.getResultType();
        if (clazz.equals(SystemUserEntity.class)) {
          root.fetch("creator", JoinType.LEFT);
          root.fetch("modifier", JoinType.LEFT);
        }
        if (params.get("username") != null) {
          String username = String.valueOf(params.get("username"));
          predicates.add(cb.like(root.get("username").as(String.class),
              "%" + username + "%"));
        }

        if (params.get("nickname") != null) {
          String nickname = String.valueOf(params.get("nickname"));
          predicates.add(cb.like(root.get("nickname").as(String.class),
              "%" + nickname + "%"));
        }

        if (params.get("type") != null) {
          UserTypeEnum type = UserTypeEnum.valueOf(params.get("type").toString());
          predicates.add(cb.equal(root.get("type").as(UserTypeEnum.class), type));
        }

        if (params.get("status") != null) {
          StatusEnum status = StatusEnum.valueOf(params.get("status").toString());
          predicates.add(cb.equal(root.get("status").as(StatusEnum.class), status));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);
    return list;
  }

  @Override
  public void deleteUser(String userId, SystemUserEntity currentUser) {
    //验证参数传递是否为空
    if (StringUtils.isEmpty(userId)) {
      throw new ServiceException(ResponseCode._1001, "userId不能为空");
    }
    //查询userId是否存在
    SystemUserEntity user = userRepository.findOne(userId);
    if (user == null) {
      throw new ServiceException(ResponseCode._1004, "未查询到ID为" + userId + "的用户信息");
    }

    //写入当前操作人和时间
    user.setModifiedDate(new Date());
    user.setModifier(currentUser);
    user.setStatus(StatusEnum.STATUS_DISABLED);
    user.setDeleteFlag(true);
    //将用户账号添加_bak后缀，防止逻辑删除之后账号验重复的操作出错
    StringBuilder sb = new StringBuilder(user.getUsername());
    sb.append("_bak");
    user.setUsername(sb.toString());

    //保存更改
    userRepository.save(user);
  }

  @Override
  public void updateUser(SystemUserEntity userEntity, SystemUserEntity currentUser) {
    String userId = userEntity.getId();
    SystemUserEntity user = userRepository.findOne(userId);
    Validate.notNull(user, "未查询到Id的用户信息");

    user.setModifiedDate(new Date());
    user.setModifier(currentUser);
    //对修改的密码加密
    user.setPassword(passwordEncoder.encodePassword(userEntity.getPassword(), ""));
    user.setNickname(userEntity.getNickname());
    //保存更改
    userRepository.save(user);
  }

  @Override
  public void create(SystemUserEntity userEntity) {
    //必填字段验证
    validateNotNull(userEntity);
    //账号验重
    boolean flag = validateUnique(userEntity.getUsername());
    if (flag) {
      throw new ServiceException(ResponseCode._1005, "当前账号已存在");
    }
    //密码加密
    userEntity.setPassword(passwordEncoder.encodePassword(userEntity.getPassword(), ""));
    userEntity.setType(UserTypeEnum.USER_ADMIN);
    //保存更改
    userRepository.save(userEntity);
  }

  @Override
  public void disableOrUnDisable(String userId, Boolean flag) {
    Validate.notBlank(userId, "用户ID不能为空");
    SystemUserEntity user = userRepository.findOne(userId);
    Validate.notNull(user, "Id为" + userId + "的对象不存在");

    if (flag) {
      user.setStatus(StatusEnum.STATUS_NORMAL);
    } else {
      user.setStatus(StatusEnum.STATUS_DISABLED);
    }

    userRepository.save(user);
  }

  @Override
  public boolean usernameValidateUnique(String username) {
    return validateUnique(username);
  }

  @Override
  public void updateLoginTime(String username, Date date) {
    SystemUserEntity userEntity = userRepository.findByUsername(username);
    userEntity.setLastLoginTime(date);
    userRepository.save(userEntity);
  }

  /**
   * 新增后台用户必填字段校验
   */
  private void validateNotNull(SystemUserEntity userEntity) {
    if (StringUtils.isEmpty(userEntity.getUsername())) {
      throw new ServiceException(ResponseCode._1001, "用户账号不能为空");
    }
  }

  /**
   * 账号验重
   */
  private boolean validateUnique(String username) {
    SystemUserEntity user = userRepository.findByUsername(username);
    if (user != null) {
      return true;
    }
    return false;
  }
}
