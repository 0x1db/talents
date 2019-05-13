package com.wangyu.talents.common.exception;

import com.wangyu.talents.common.model.ResponseCode;
import com.wangyu.talents.common.model.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常捕获
 *
 * @author wangyu
 * @Date 2019/4/16 23:00
 * @Version 1.0
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * slf4j
   */
  private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * 拦截捕捉自定义异常 BusinessException
   */
  @ExceptionHandler(value = ServiceException.class)
  public ResponseModel businessExceptionHandler(ServiceException ex) {
    logger.error(ex.getMessage(), ex);
    return new ResponseModel(System.currentTimeMillis(), null, ex.getResponseCode(),
        ex.getMessage());
  }

  /**
   * 捕捉空指针异常
   */
  @ExceptionHandler({NullPointerException.class})
  public ResponseModel nullPointerExceptionHandler(NullPointerException ex) {
    ex.printStackTrace();
    logger.error(ex.getMessage(), ex);
    return new ResponseModel(System.currentTimeMillis(), null, ResponseCode._1008, ex.getMessage());
  }

  /**
   * 捕捉IllegalArgumentException 非法参数异常
   */
  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseModel illegalArgumentExceptionHandler(IllegalArgumentException ex) {
    ex.printStackTrace();
    logger.error(ex.getMessage(), ex);
    return new ResponseModel(System.currentTimeMillis(), null, ResponseCode._1010, ex.getMessage());
  }

  /**
   * 捕捉全局异常
   */
  @ExceptionHandler(value = Exception.class)
  public ResponseModel globalExceptionHandler(Exception ex) {
    logger.error(ex.getMessage(), ex);
    return new ResponseModel(System.currentTimeMillis(), null, ResponseCode._500,
        ex.getMessage());
  }

  /**
   * 拦截参数不匹配异常 400 getPropertyName()获取数据类型不匹配参数名称  getRequiredType()实际要求客户端传递的数据类型
   */
  @ExceptionHandler(value = TypeMismatchException.class)
  public ResponseModel requestTypeMismatch(TypeMismatchException ex) {
    //拼接详细错误信息
    StringBuilder sb = new StringBuilder();
    sb.append("参数类型不匹配,参数：").append(ex.getPropertyName());
    sb.append("类型应该为：" + ex.getRequiredType());
    logger.error(ex.getMessage(), ex);
    ex.printStackTrace();
    return new ResponseModel(System.currentTimeMillis(), null, ResponseCode._400, sb.toString());
  }

  /**
   * 缺少参数异常 getParameterName() 缺少的参数名称 400
   */
  @ExceptionHandler(value = MissingServletRequestParameterException.class)
  public ResponseModel requestMissingServletRequest(MissingServletRequestParameterException ex) {
    logger.error(ex.getMessage(), ex);
    ex.printStackTrace();
    return new ResponseModel(System.currentTimeMillis(), null, ResponseCode._400,
        "缺少必要参数,参数名称为：" + ex.getParameterName());
  }

  /**
   * 404 请求地址不存在或错误
   */
  @ExceptionHandler(value = NoHandlerFoundException.class)
  public ResponseModel noSuchRequestHandlingRequest(NoHandlerFoundException ex) {
    logger.error(ex.getMessage(), ex);
    ex.printStackTrace();
    return new ResponseModel(System.currentTimeMillis(), null, ResponseCode._404,
        "请求地址不存在或错误");
  }

  /**
   * 405 不正确的请求方式
   */
  @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
  public ResponseModel methodNotSupportedRequest(HttpRequestMethodNotSupportedException ex) {
    logger.error(ex.getMessage(), ex);
    ex.printStackTrace();
    return new ResponseModel(System.currentTimeMillis(), null, ResponseCode._405,
        ex.getMessage());
  }
}