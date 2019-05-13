package com.wangyu.talents.service;

import com.wangyu.talents.common.base.BaseService;
import com.wangyu.talents.entity.SystemUserEntity;

/**
 * 系统用户服务层
 *
 * @author wangyu
 * @Date 2019/3/9 17:59
 * @Version 1.0
 **/
public interface SystemUserService extends BaseService<SystemUserEntity> {

  /**
   * 通过用户名和状态查询
   */
  SystemUserEntity findByUsernameAndStatus(String account, String status);
}
