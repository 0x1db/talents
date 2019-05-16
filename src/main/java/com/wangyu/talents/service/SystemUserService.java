package com.wangyu.talents.service;

import com.wangyu.talents.common.enums.StatusEnum;
import com.wangyu.talents.entity.SystemUserEntity;
import java.util.Date;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 系统用户服务层
 *
 * @author wangyu
 * @Date 2019/3/9 17:59
 * @Version 1.0
 **/
public interface SystemUserService {

  /**
   * 通过用户名和状态查询
   */
  SystemUserEntity findByUsernameAndStatus(String account, StatusEnum status);

  /**
   * 条件分页查询
   */
  Page<SystemUserEntity> findPages(Map<String, Object> params, Pageable pageable);

  /**
   * （逻辑）删除用户
   *
   * @param userId 用户ID
   * @param currentUser 操作人
   */
  void deleteUser(String userId, SystemUserEntity currentUser);

  /**
   * 修改用户，只修改用户昵称和密码
   *
   * @param userEntity 用户实体对象
   * @param currentUser 操作人
   */
  void updateUser(SystemUserEntity userEntity, SystemUserEntity currentUser);

  /**
   * 新增用户
   */
  void create(SystemUserEntity userEntity);

  /**
   * 用户启用/禁用
   *
   * @param userId 用户ID
   * @param flag 启用/禁用标识
   */
  void disableOrUnDisable(String userId, Boolean flag);

  /**
   * 用户账号唯一性校验
   *
   * @param username 账号
   */
  boolean usernameValidateUnique(String username);

  /**
   * 更新用户登录时间
   *
   * @param username 用户账号
   * @param date 登录时间
   */
  void updateLoginTime(String username, Date date);
}
