package com.wangyu.talents.web.controller;

import com.google.common.collect.Sets;
import com.wangyu.talents.common.base.BaseController;
import com.wangyu.talents.common.enums.StatusEnum;
import com.wangyu.talents.common.model.ResponseCode;
import com.wangyu.talents.common.model.ResponseModel;
import com.wangyu.talents.entity.SystemResourceEntity;
import com.wangyu.talents.entity.SystemRoleEntity;
import com.wangyu.talents.entity.SystemUserEntity;
import com.wangyu.talents.service.SystemResourceService;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单权限控制器
 *
 * @author wangyu
 * @Date 2019/4/14 23:26
 * @Version 1.0
 **/
@RestController
@RequestMapping("/v1/resource")
public class SystemResourceController extends BaseController {

  @Autowired
  private SystemResourceService resourceService;

  /**
   * 根据当前登录用户查询权限信息
   */
  @GetMapping(value = "/findByCurrentUser")
  public ResponseModel findByCurrentUser(Principal principal) {
    SystemUserEntity currentUser = this.verifyLogin(principal);
    if (currentUser == null) {
      return this.buildHttpResultForValidate(ResponseCode._1011, "登录信息已失效，请重新登录");
    }
    //装载权限列表
    Set<SystemResourceEntity> list = Sets.newHashSet();

    //获取当前登录用户的角色信息
    Set<SystemRoleEntity> roles = currentUser.getRoles();
    for (SystemRoleEntity role : roles) {
      //如果是超级管理员，则直接查询所有有效的权限信息并返回
      if ("admin".equalsIgnoreCase(role.getName())) {
        //查询所有状态正常的权限信息
        List<SystemResourceEntity> allList = resourceService
            .findListByStatus(StatusEnum.STATUS_NORMAL);
        list.addAll(allList);
        break;
      }

      List<SystemResourceEntity> roleList = resourceService.findByRoleId(role.getId());
      if (list != null) {
        list.addAll(roleList);
      }
    }
    return this.buildHttpResult(list, "creator", "modifier", "roles");
  }

}
