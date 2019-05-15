package com.wangyu.talents.repository;

import com.wangyu.talents.common.base.BaseRepository;
import com.wangyu.talents.common.enums.StatusEnum;
import com.wangyu.talents.entity.SystemResourceEntity;
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
public interface SystemResourceRepository extends BaseRepository<SystemResourceEntity> {

  /**
   * 按状态查询权限列表
   */
  @Query("select menu from SystemResourceEntity menu where menu.status=:status")
  List<SystemResourceEntity> findListByStatus(@Param("status") StatusEnum status);

  /**
   * 根据路径查询
   */
  List<SystemResourceEntity> findByResource(String requestUrl);
}
