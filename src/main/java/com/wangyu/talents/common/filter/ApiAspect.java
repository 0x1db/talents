package com.wangyu.talents.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author wangyu
 * @Date 2019/5/22 22:52
 * @Version 1.0
 **/
@Aspect
@Component
public class ApiAspect {

  @Pointcut("@annotation(com.wangyu.talents.common.filter.ApiAuth)")
  private void apiAspect() {

  }

  @After("apiAspect()")
  public void doBefore() {
    System.out.println("开始");
  }

  @Around("apiAspect()")
  public Object around(ProceedingJoinPoint pjp) throws Throwable {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String token = request.getHeader("token");
    System.out.println("请求token : " + token);
    System.out.println("-----------------拦截");
    HttpServletResponse response = attributes.getResponse();
    response.addHeader("token", "123456");
    Object proceed = pjp.proceed();
    return proceed;
  }
}
