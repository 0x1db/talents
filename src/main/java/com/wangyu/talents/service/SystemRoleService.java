package com.wangyu.talents.service;

import com.wangyu.talents.entity.SystemRoleEntity;
import com.wangyu.talents.entity.SystemUserEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 系统角色服务层
 *
 * @author wangyu
 * @Date 2019/3/9 12:01
 * @Version 1.0
 **/
public interface SystemRoleService {

  /**
   * 根据用户ID查询角色信息
   */
  List<SystemRoleEntity> findByUserId(String id);

  /**
   * 根据资源ID查询
   */
  List<SystemRoleEntity> findByResourceId(String id);

  /**
   * 条件分页查询
   */
  Page<SystemRoleEntity> getPages(Map<String, Object> params, Pageable pageable);

  /**
   * 启用/启用
   *
   * @param userId 用户ID
   * @param flag 操作标识
   */
  void disableOrUnDisable(String userId, Boolean flag, SystemUserEntity currentUser);
}
