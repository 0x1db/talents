package com.wangyu.talents.web.controller;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.wangyu.talents.common.base.BaseController;
import com.wangyu.talents.common.enums.ResourceTypeEnum;
import com.wangyu.talents.common.enums.StatusEnum;
import com.wangyu.talents.common.enums.UserTypeEnum;
import com.wangyu.talents.common.exception.ServiceException;
import com.wangyu.talents.common.model.ResponseCode;
import com.wangyu.talents.common.model.ResponseModel;
import com.wangyu.talents.entity.SystemResourceEntity;
import com.wangyu.talents.entity.SystemRoleEntity;
import com.wangyu.talents.entity.SystemUserEntity;
import com.wangyu.talents.service.SystemResourceService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
      if (roleList != null) {
        list.addAll(roleList);
      }
    }
    return this.buildHttpResult(list, "creator", "modifier", "roles");
  }

  /**
   * 条件分页查询
   */
  @GetMapping("/getPages")
  private ResponseModel getPages(String status, String type, String description,
      @PageableDefault(sort = {"modifiedDate"}, direction = Direction.DESC)
          Pageable pageable,
      Principal principal) {
    SystemUserEntity currentUser = this.verifyLogin(principal);
    if (currentUser == null) {
      return this.buildHttpResultForValidate(ResponseCode._1011, "登录信息已失效，请重新登录");
    }
    //封装分页条件
    Map<String, Object> params = Maps.newHashMap();
    //用户昵称
    if (StringUtils.isNotEmpty(type)) {
      params.put("type", type);
    }
    //用户账号
    if (StringUtils.isNotEmpty(status)) {
      params.put("status", status);
    }
    //用户类型
    if (StringUtils.isNotEmpty(description)) {
      params.put("description", description);
    }

    //默认查询状态正常的用户
    params.put("deleteFlag", true);

    Page<SystemResourceEntity> pages = resourceService.findPages(params, pageable);
    return this
        .buildHttpResult(pages, "roles", "creator.roles", "creator.modifier", "creator.creator",
            "modifier.roles", "modifier.creator", "modifier.modifier", "parent");
  }

  /**
   * 新增资源
   */
  @PostMapping("/")
  public ResponseModel create(@RequestBody SystemResourceEntity resourceEntity,
      Principal principal) {
    SystemUserEntity currentUser = this.verifyLogin(principal);
    if (currentUser == null) {
      return this.buildHttpResultForValidate(ResponseCode._1011, "登录信息已失效，请重新登录");
    }
    //参数校验
    if (resourceEntity == null) {
      throw new ServiceException(ResponseCode._1001, "参数不能为空");
    }
    resourceEntity.setCreator(currentUser);
    resourceService.create(resourceEntity);
    return this.buildHttpResult();
  }

  /**
   * 查询所有状态正常的资源信息
   */
  @GetMapping("/findAll")
  public ResponseModel findAll() {
    List<SystemResourceEntity> list = resourceService.findAll();
    return this
        .buildHttpResult(list, "roles", "creator", "modifier", "parent");
  }
}
