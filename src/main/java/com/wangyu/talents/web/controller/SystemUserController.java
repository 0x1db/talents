package com.wangyu.talents.web.controller;

import com.wangyu.talents.common.base.BaseController;
import com.wangyu.talents.common.model.ResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

  /**
   * 分页查询系统用户信息
   */
  @GetMapping("/getByCondition")
  public ResponseModel getByCondition() {
    return this.buildHttpResult();
  }
}
