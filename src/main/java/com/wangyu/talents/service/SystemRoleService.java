package com.wangyu.talents.service;

import com.wangyu.talents.common.base.BaseService;
import com.wangyu.talents.entity.SystemRoleEntity;
import java.util.List;

/**
 * 系统角色服务层
 *
 * @author wangyu
 * @Date 2019/3/9 12:01
 * @Version 1.0
 **/
public interface SystemRoleService extends BaseService<SystemRoleEntity> {

  /**
   * 根据用户ID查询角色信息
   */
  List<SystemRoleEntity> findByUserId(String id);

  /**
   * 根据资源ID查询
   */
  List<SystemRoleEntity> findByResourceId(String id);
}
