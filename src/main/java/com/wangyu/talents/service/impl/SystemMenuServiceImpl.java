package com.wangyu.talents.service.impl;

import com.wangyu.talents.common.base.BaseServiceImpl;
import com.wangyu.talents.dao.SystemMenuRepository;
import com.wangyu.talents.entity.SystemMenuEntity;
import com.wangyu.talents.entity.SystemRoleEntity;
import com.wangyu.talents.service.SystemMenuService;
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
@Service("menuService")
public class SystemMenuServiceImpl extends BaseServiceImpl<SystemMenuEntity> implements
    SystemMenuService {

  @Autowired
  private SystemMenuRepository menuRepository;

  @Override
  public List<SystemMenuEntity> findByRoleId(String id) {
    return null;
  }

  @Override
  public List<SystemMenuEntity> findListByStatus(Integer status) {
    Validate.notNull(status, "状态不能为空");
    return menuRepository.findListByStatus(status);
  }
}
