package com.wangyu.talents.common.exception;

import com.wangyu.talents.common.model.ResponseCode;

/**
 * 定义业务异常，所有业务层抛出异常，根据不同的状态码确定异常类型
 *
 * @author wangyu
 * @Date 2019/4/16 23:02
 * @Version 1.0
 **/
public class ServiceException extends RuntimeException {

  /**
   * 异常代码
   */
  private ResponseCode responseCode;

  /**
   * 异常描述
   */
  private String message;

  public ServiceException() {

  }

  public ServiceException(ResponseCode responseCode) {
    this.responseCode = responseCode;
    this.message = responseCode.getDesc();
  }

  public ServiceException(ResponseCode responseCode, String message) {
    super(message);
    this.responseCode = responseCode;
    this.message = message;
  }

  /**
   * 没有传递状态码则默认为 1010：未知异常
   */
  public ServiceException(String message) {
    super(message);
    this.responseCode = ResponseCode._1010;
    this.message = message;
  }

  public ResponseCode getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(ResponseCode responseCode) {
    this.responseCode = responseCode;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * 重写堆栈填充，不填充错误堆栈信息，提高性能
   */
  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
