package com.wangyu.talents.repository;

import com.wangyu.talents.common.base.BaseRepository;
import com.wangyu.talents.entity.SystemUserEntity;
import org.springframework.data.jpa.repository.Query;

/**
 * 系统用户数据持久层
 *
 * @author wangyu
 * @Date 2019/3/9 11:22
 * @Version 1.0
 **/
public interface SystemUserRepository extends BaseRepository<SystemUserEntity> {

  /**
   * 根据用户名查询
   *
   * @param username 用户名
   */
  @Query("from SystemUserEntity user where user.username = ?1 and user.status=?2")
  SystemUserEntity findByUsernameAndStatus(String username, String status);
}
