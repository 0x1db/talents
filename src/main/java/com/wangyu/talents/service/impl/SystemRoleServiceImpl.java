package com.wangyu.talents.service.impl;

import com.google.common.collect.Lists;
import com.wangyu.talents.common.enums.StatusEnum;
import com.wangyu.talents.common.exception.ServiceException;
import com.wangyu.talents.common.model.ResponseCode;
import com.wangyu.talents.entity.SystemRoleEntity;
import com.wangyu.talents.entity.SystemUserEntity;
import com.wangyu.talents.repository.SystemRoleRepository;
import com.wangyu.talents.service.SystemRoleService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * 系统角色服务层实现
 *
 * @author wangyu
 * @Date 2019/3/9 12:02
 * @Version 1.0
 **/
@Service("roleService")
public class SystemRoleServiceImpl implements SystemRoleService {

  @Autowired
  private SystemRoleRepository roleRepository;

  @Override
  public List<SystemRoleEntity> findByUserId(String id) {
    Validate.notBlank(id, "用户ID不能为空");

    Set<SystemRoleEntity> roles = roleRepository.findByUserId(id);
    if (roles == null || roles.isEmpty()) {
      return Collections.emptyList();
    }

    List<SystemRoleEntity> roleList = Lists.newArrayList(roles);
    return roleList;
  }

  @Override
  public List<SystemRoleEntity> findByResourceId(String id) {
    //TODO
    return null;
  }

  @Override
  public Page<SystemRoleEntity> getPages(Map<String, Object> params, Pageable pageable) {
    Page<SystemRoleEntity> list = roleRepository.findAll(new Specification<SystemRoleEntity>() {
      @Override
      public Predicate toPredicate(Root<SystemRoleEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        Class<?> clazz = query.getResultType();
        if (clazz.equals(SystemRoleEntity.class)) {
          root.fetch("creator", JoinType.LEFT);
          root.fetch("modifier", JoinType.LEFT);
        }
        List<Predicate> predicates = new ArrayList<Predicate>();
        // 查询角色名称
        if (params.get("roleName") != null) {
          predicates.add(cb.like(root.get("name").as(String.class),
              "%" + String.valueOf(params.get("roleName") + "%")));
        }

        // 查询状态
        if (params.get("status") != null) {
          StatusEnum status = StatusEnum.valueOf(params.get("status").toString());
          predicates.add(cb.equal(root.get("status").as(Integer.class), status));
        }
        // 遍历查询条件，查询语句
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return null;
      }
    }, pageable);
    return list;
  }

  @Override
  public void disableOrUnDisable(String roleId, Boolean flag, SystemUserEntity currentUser) {
    Validate.notBlank(roleId, "roleId不能为空");
    SystemRoleEntity roleEntity = roleRepository.findOne(roleId);
    if (roleEntity == null) {
      throw new ServiceException(ResponseCode._1004, "未查询到id为" + roleId + "的角色信息");
    }

    roleEntity.setModifiedDate(new Date());
    roleEntity.setModifier(currentUser);
    if (flag) {
      //启用
      roleEntity.setStatus(StatusEnum.STATUS_NORMAL);
    } else {
      roleEntity.setStatus(StatusEnum.STATUS_DISABLED);
    }
    roleRepository.save(roleEntity);
  }
}
