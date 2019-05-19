package com.wangyu.talents.web.controller;

import com.google.common.collect.Maps;
import com.wangyu.talents.common.base.BaseController;
import com.wangyu.talents.common.enums.StatusEnum;
import com.wangyu.talents.common.exception.ServiceException;
import com.wangyu.talents.common.model.ResponseCode;
import com.wangyu.talents.common.model.ResponseModel;
import com.wangyu.talents.entity.SystemResourceEntity;
import com.wangyu.talents.entity.SystemRoleEntity;
import com.wangyu.talents.entity.SystemUserEntity;
import com.wangyu.talents.service.SystemRoleService;
import java.security.Principal;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wangyu
 * @Date 2019/5/17 11:57
 * @Version 1.0
 */
@RestController
@RequestMapping("/v1/role")
public class SystemRoleController extends BaseController {

  @Autowired
  private SystemRoleService roleService;

  /**
   * 条件分页查询
   *
   * @param roleName 角色名
   * @param status 状态
   */
  @GetMapping("/getPages")
  public ResponseModel getPages(@RequestParam(required = false) String roleName,
      @RequestParam(required = false) String status,
      @PageableDefault(value = 15, sort = {
          "createDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
    Map<String, Object> params = Maps.newHashMap();
    if (StringUtils.isNotEmpty(roleName)) {
      params.put("roleName", roleName);
    }
    if (StringUtils.isNotEmpty(status)) {
      if ("1".equals(status)) {
        params.put("status", StatusEnum.STATUS_NORMAL);
      } else {
        params.put("status", StatusEnum.STATUS_DISABLED);
      }
    }
    Page<SystemRoleEntity> result = roleService.getPages(params, pageable);
    return this
        .buildHttpResult(result, "users", "creator.roles", "creator.modifier", "creator.creator",
            "modifier.roles", "modifier.creator", "modifier.modifier");
  }

  /**
   * 账号启用/禁用
   */
  @PatchMapping("/disableOrUnDisable")
  public ResponseModel disableOrUnDisable(@RequestParam("userId") String userId,
      @RequestParam("flag") Boolean flag, Principal principal) {
    SystemUserEntity currentUser = this.verifyLogin(principal);
    if (currentUser == null) {
      return this.buildHttpResultForValidate(ResponseCode._1011, "登录信息已失效，请重新登陆");
    }
    if (StringUtils.isEmpty(userId) || flag == null) {
      throw new ServiceException(ResponseCode._1001, "规定的必传项没有传入");
    }
    roleService.disableOrUnDisable(userId, flag, currentUser);
    return this.buildHttpResult();
  }

  /**
   * 查询角色详情
   */
  @GetMapping("/{roleId}")
  public ResponseModel findRolesDetail(@PathVariable("roleId") String roleId) {
    if (StringUtils.isEmpty(roleId)) {
      return this.buildHttpResultForValidate(ResponseCode._1001, "参数不能为空");
    }
    SystemRoleEntity roleEntity = roleService.findById(roleId);
    return this.buildHttpResult(roleEntity, "users", "creator", "modifier");
  }
}
