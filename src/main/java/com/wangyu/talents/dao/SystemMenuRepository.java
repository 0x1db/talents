package com.wangyu.talents.dao;

import com.wangyu.talents.common.base.BaseRepository;
import com.wangyu.talents.entity.SystemMenuEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 系统菜单数据持久层
 *
 * @author wangyu
 * @Date 2019/4/14 22:47
 * @Version 1.0
 **/
public interface SystemMenuRepository extends BaseRepository<SystemMenuEntity> {

  /**
   * 根据用户名查询
   */
  @Query(value = "select m.* from sys_menu m ,sys_user u, sys_user_role ur, sys_role_menu rm where u.user_name=?1 and u.id = ur.user_id and ur.role_id = rm.role_id and rm.menu_id = m.id", nativeQuery = true)
  List<SystemMenuEntity> findByUserName(String userName);

  /**
   * 按状态查询权限列表
   */
  @Query("select menu from SystemMenuEntity menu where menu.status=:status")
  List<SystemMenuEntity> findListByStatus(@Param("status") Integer status);
}
