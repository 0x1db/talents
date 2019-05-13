package com.wangyu.talents.service;

import com.wangyu.talents.common.base.BaseService;
import com.wangyu.talents.entity.SystemMenuEntity;
import java.util.List;

/**
 * 系统菜单业务层
 *
 * @author wangyu
 * @Date 2019/4/14 17:36
 * @Version 1.0
 **/
public interface SystemMenuService extends BaseService<SystemMenuEntity> {

  /**
   * 根据权限ID查询
   *
   * @param roleId 权限ID
   */
  List<SystemMenuEntity> findByRoleId(String roleId);

  /**
   * 按状态查询所有权限
   */
  List<SystemMenuEntity> findListByStatus(Integer status);
}
