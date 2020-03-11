package com.wangyu.talents.web;

import com.wangyu.talents.domain.SystemUser;
import com.wangyu.talents.service.SystemUserService;
import com.wangyu.talents.web.model.ApiObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户控制层
 *
 * @author wangyu
 * @Date 2019/7/5 22:54
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class SystemUserController {

  @Autowired
  private SystemUserService userService;

  @PostMapping("/save")
  public String saveUser(SystemUser user) {
    userService.insert(user);
    return "true";
  }

  @GetMapping("/getById")
  public String getById(@RequestParam("userId") Integer userId) {
    SystemUser systemUser = userService.selectById(userId);
    return ApiObject.newOk(systemUser).toString();
  }
}
