package com.wangyu.talents.service.impl;

import com.google.common.collect.Lists;
import com.wangyu.talents.common.base.BaseServiceImpl;
import com.wangyu.talents.dao.SystemRoleRepository;
import com.wangyu.talents.entity.SystemRoleEntity;
import com.wangyu.talents.service.SystemRoleService;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统角色服务层实现
 *
 * @author wangyu
 * @Date 2019/3/9 12:02
 * @Version 1.0
 **/
@Service("roleService")
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRoleEntity> implements
    SystemRoleService {

  @Autowired
  private SystemRoleRepository roleDao;

  @Override
  public List<SystemRoleEntity> findByUserId(String id) {
    Validate.notBlank(id, "用户ID不能为空");

    Set<SystemRoleEntity> roles = roleDao.findByUserId(id);
    if (roles == null || roles.isEmpty()) {
      return Collections.emptyList();
    }

    List<SystemRoleEntity> roleList = Lists.newArrayList(roles);
    return roleList;
  }
}
