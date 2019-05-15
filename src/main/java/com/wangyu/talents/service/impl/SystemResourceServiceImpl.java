package com.wangyu.talents.service.impl;

import com.wangyu.talents.common.base.BaseServiceImpl;
import com.wangyu.talents.common.enums.StatusEnum;
import com.wangyu.talents.repository.SystemResourceRepository;
import com.wangyu.talents.entity.SystemResourceEntity;
import com.wangyu.talents.service.SystemResourceService;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统菜单业务层实现
 *
 * @author wangyu
 * @Date 2019/4/14 17:43
 * @Version 1.0
 **/
@Service("systemResourceService")
public class SystemResourceServiceImpl extends BaseServiceImpl<SystemResourceEntity> implements
    SystemResourceService {

  @Autowired
  private SystemResourceRepository menuRepository;

  @Override
  public List<SystemResourceEntity> findByRoleId(String id) {
    return null;
  }

  @Override
  public List<SystemResourceEntity> findListByStatus(StatusEnum status) {
    Validate.notNull(status, "状态不能为空");
    return menuRepository.findListByStatus(status);
  }

  @Override
  public List<SystemResourceEntity> findByResource(String requestUrl) {
    return menuRepository.findByResource(requestUrl);
  }
}
