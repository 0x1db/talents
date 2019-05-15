package com.wangyu.talents.repository;

import com.wangyu.talents.common.base.BaseRepository;
import com.wangyu.talents.entity.SystemRoleEntity;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 系统角色数据持久层
 *
 * @author wangyu
 * @Date 2019/3/9 12:04
 * @Version 1.0
 **/
public interface SystemRoleRepository extends BaseRepository<SystemRoleEntity> {

  /**
   * 根据用户ID查询所属角色列表
   */
  @Query("from SystemRoleEntity r left join fetch r.users u where u.id=:userId")
  Set<SystemRoleEntity> findByUserId(@Param("userId") String userId);
}
