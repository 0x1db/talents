package com.wangyu.talents.web.controller;

import com.wangyu.talents.common.base.BaseController;
import com.wangyu.talents.common.model.ResponseCode;
import com.wangyu.talents.common.model.ResponseModel;
import com.wangyu.talents.entity.SystemUserEntity;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后端登录请求处理控制器
 *
 * @author wangyu
 * @Date 2019/3/9 17:30
 * @Version 1.0
 **/
@RestController
@RequestMapping("/v1/security")
public class SecurityController extends BaseController {

  /**
   * 当登陆成功后，默认跳转到这个URL，并且返回登录成功后的用户基本信息
   */
  @RequestMapping(value = "/loginSuccess", method = RequestMethod.POST)
  public ResponseModel loginSuccess(HttpServletRequest request, HttpServletResponse response,
      Principal logUser) {
    String account = logUser.getName();
    if (StringUtils.isEmpty(account)) {
      return this.buildHttpResultForValidate(ResponseCode._1011, "账号不能为空");
    }

    // 查询用户基本信息
    SystemUserEntity currentUser = this.verifyLogin(logUser);
    // 如果条件成立，说明这个用户存在数据问题。抛出异常
    if (currentUser == null) {
      return this.buildHttpResultForValidate(ResponseCode._1011, "登录信息已失效，请重新登录");
    }
    //更新登陆时间
//      systemUserService.updateLoginTime(currentUser.getAccount(), new Date());

    // 返回登录者账号
    return this.buildHttpResult(currentUser.getUsername());
  }

  /**
   * 由于后端提供的都是restful接口，并没有直接跳转的页面<br> 所以只要访问的url没有通过权限认证，就跳到这个请求上，并直接排除权限异常
   */
  @RequestMapping(value = "/loginFail", method = {RequestMethod.GET, RequestMethod.POST})
  public ResponseModel loginFail() throws IllegalAccessException {
    return this.buildHttpResultForValidate(ResponseCode._1011, "账号或密码错误，或者账号被禁用");

  }

  /**
   * 成功登出
   */
  @RequestMapping(value = "/logoutSuccess", method = RequestMethod.GET)
  public void logoutSuccess() {

  }

}
