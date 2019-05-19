package com.wangyu.talents.service;

import com.wangyu.talents.common.base.BaseService;
import com.wangyu.talents.common.enums.StatusEnum;
import com.wangyu.talents.entity.SystemResourceEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 系统菜单业务层
 *
 * @author wangyu
 * @Date 2019/4/14 17:36
 * @Version 1.0
 **/
public interface SystemResourceService {

  /**
   * 根据权限ID查询
   *
   * @param roleId 权限ID
   */
  List<SystemResourceEntity> findByRoleId(String roleId);

  /**
   * 按状态查询所有权限
   */
  List<SystemResourceEntity> findListByStatus(StatusEnum status);

  /**
   * 根据路径名查询
   */
  List<SystemResourceEntity> findByResource(String requestUrl);

  /**
   * 条件分页查询
   */
  Page<SystemResourceEntity> findPages(Map<String, Object> params, Pageable pageable);

  /**
   * 新增资源信息
   */
  void create(SystemResourceEntity resourceEntity);

  /**
   * 查询状态正常的所有资源信息
   */
  List<SystemResourceEntity> findAll();
}
