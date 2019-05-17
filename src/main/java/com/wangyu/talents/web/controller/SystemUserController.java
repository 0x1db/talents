package com.wangyu.talents.web.controller;

import com.google.common.collect.Maps;
import com.wangyu.talents.common.base.BaseController;
import com.wangyu.talents.common.enums.StatusEnum;
import com.wangyu.talents.common.enums.UserTypeEnum;
import com.wangyu.talents.common.exception.ServiceException;
import com.wangyu.talents.common.model.ResponseCode;
import com.wangyu.talents.common.model.ResponseModel;
import com.wangyu.talents.entity.SystemUserEntity;
import com.wangyu.talents.service.SystemUserService;
import java.security.Principal;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户控制层
 *
 * @author wangyu
 * @Date 2019/4/21 14:54
 * @Version 1.0
 **/
@RestController
@RequestMapping("/v1/user")
public class SystemUserController extends BaseController {

  @Autowired
  private SystemUserService userService;

  /**
   * 条件分页查询系统用户信息
   */
  @GetMapping("/getPages")
  public ResponseModel getByCondition(String username, String nickname, String type,
      @PageableDefault(sort = {"modifiedDate"}, direction = Direction.DESC)
          Pageable pageable,
      Principal principal) {
    //封装分页条件
    Map<String, Object> params = Maps.newHashMap();
    //用户昵称
    if (StringUtils.isNotEmpty(nickname)) {
      params.put("nickname", nickname);
    }
    //用户账号
    if (StringUtils.isNotEmpty(username)) {
      params.put("username", username);
    }
    //用户类型
    if (StringUtils.isNotEmpty(type)) {
      if ("1".equals(type)) {
        params.put("type", UserTypeEnum.USER_ADMIN);
      }
    }
    //默认查询状态正常的用户
    params.put("deleteFlag", true);

    Page<SystemUserEntity> pages = userService.findPages(params, pageable);
    return this.buildHttpResult(pages, "roles", "creator", "modifier");
  }

  /**
   * 新增后台用户
   */
  @PostMapping("/register")
  public ResponseModel create(@RequestBody SystemUserEntity userEntity, Principal principal) {
    SystemUserEntity currentUser = this.verifyLogin(principal);
    if (currentUser == null) {
      return this.buildHttpResultForValidate(ResponseCode._1011, "登录信息已失效，请重新登陆");
    }
    userEntity.setCreator(currentUser);
    userService.create(userEntity);
    return this.buildHttpResult();
  }

  /**
   * 修改后台用户
   */
  @PatchMapping("/")
  public ResponseModel update(@RequestBody SystemUserEntity userEntity, Principal principal) {
    SystemUserEntity currentUser = this.verifyLogin(principal);
    if (currentUser == null) {
      return this.buildHttpResultForValidate(ResponseCode._1011, "登录信息已失效，请重新登陆");
    }

    userService.updateUser(userEntity, currentUser);
    return this.buildHttpResult();
  }

  /**
   * 删除后台用户（逻辑删除）
   */
  @DeleteMapping("/{userId}")
  public ResponseModel delete(@PathVariable("userId") String userId, Principal principal) {
    SystemUserEntity currentUser = this.verifyLogin(principal);
    if (currentUser == null) {
      return this.buildHttpResultForValidate(ResponseCode._1011, "登录信息已失效，请重新登陆");
    }

    userService.deleteUser(userId, currentUser);
    return this.buildHttpResult();
  }

  /**
   * 账号启用/禁用
   */
  @PatchMapping("disableOrUnDisable")
  public ResponseModel disableOrUnDisable(@RequestParam("userId") String userId,
      @RequestParam("flag") Boolean flag, Principal principal) {
    SystemUserEntity currentUser = this.verifyLogin(principal);
    if (currentUser == null) {
      return this.buildHttpResultForValidate(ResponseCode._1011, "登录信息已失效，请重新登陆");
    }

    if (StringUtils.isEmpty(userId) || flag == null) {
      throw new ServiceException(ResponseCode._1001, "规定的必传项没有传入");
    }
    userService.disableOrUnDisable(userId, flag, currentUser);
    return this.buildHttpResult();
  }

  /**
   * 账号唯一性校验
   */
  @GetMapping("/usernameValidateUnique/{username}")
  public ResponseModel usernameValidateUnique(@PathVariable("username") String username) {
    if (StringUtils.isEmpty(username)) {
      return this.buildHttpResultForValidate(ResponseCode._1001, "账号不能为空");
    }

    boolean flag = userService.usernameValidateUnique(username);
    return this.buildHttpResult(flag);
  }

  /**
   * 查询后台用户详情
   */
  @GetMapping("/{userId}")
  public ResponseModel findById(@PathVariable("userId") String userId) {
    if (StringUtils.isEmpty(userId)) {
      return this.buildHttpResultForValidate(ResponseCode._1001, "userId不能为空");
    }
    SystemUserEntity systemUserEntity = userService.findById(userId);
    return this.buildHttpResult(systemUserEntity, "roles", "creator", "modifier");
  }

  /**
   * 用户重置密码
   */
  @PatchMapping("/resetPassword/{userId}")
  public ResponseModel resetPassword(@PathVariable("userId") String userId, Principal principal) {
    SystemUserEntity currentUser = this.verifyLogin(principal);
    if (currentUser == null) {
      return this.buildHttpResultForValidate(ResponseCode._1011, "登录信息已失效，请重新登陆");
    }

    if (StringUtils.isEmpty(userId)) {
      return this.buildHttpResultForValidate(ResponseCode._1001, "userId不能为空");
    }
    String password = userService.resetPassword(userId, currentUser);
    return this.buildHttpResult(password);
  }
}
