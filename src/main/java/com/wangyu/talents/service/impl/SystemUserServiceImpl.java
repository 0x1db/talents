package com.wangyu.talents.service.impl;

import com.wangyu.talents.common.base.BaseServiceImpl;
import com.wangyu.talents.repository.SystemUserRepository;
import com.wangyu.talents.entity.SystemUserEntity;
import com.wangyu.talents.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统用户服务层实现
 *
 * @author wangyu
 * @Date 2019/3/9 18:01
 * @Version 1.0
 **/
@Service("userService")
public class SystemUserServiceImpl extends BaseServiceImpl<SystemUserEntity> implements
    SystemUserService {

  @Autowired
  private SystemUserRepository userDao;

  @Override
  public SystemUserEntity findByUsernameAndStatus(String account, String status) {
    return userDao.findByUsernameAndStatus(account, status);
  }
}
