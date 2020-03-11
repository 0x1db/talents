package com.wangyu.talents.web.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import java.util.Map;

/**
 * 接口返回值封装
 *
 * @author wangyu
 * @Date 2019/7/6 0:30
 * @Version 1.0
 **/
public class ApiObject {

  /**
   * 操作成功--200
   */
  public final static int STATUS_CODE_SUCCESS = 200;
  /**
   * 操作失败--300
   */
  public final static int STATUS_CODE_FAILURE = 300;
  /**
   * 会话超时--301
   */
  public final static int STATUS_CODE_TIMEOUT = 301;
  /**
   * 禁止操作--403
   */
  public final static int STATUS_CODE_FORBIDDEN = 403;
  /**
   * 违规参数--400
   */
  public final static int STATUS_CODE_VIOLATION = 400;


  /**
   * 时间戳
   */
  private Long timestamp;

  /**
   * 返回消息
   */
  private String message = "";

  /**
   * 状态码
   */
  private int code = STATUS_CODE_SUCCESS;

  /**
   * 数据
   */
  private Object data;

  public ApiObject() {

  }

  public ApiObject(String message) {
    this.timestamp = System.currentTimeMillis() / 1000;
    this.message = message;
  }

  /**
   * 构造函数
   */
  public ApiObject(int statusCode) {
    super();
    this.timestamp = System.currentTimeMillis() / 1000;
    this.code = statusCode;
  }

  /**
   * 构造函数
   */
  public ApiObject(int statusCode, String message) {
    super();
    this.timestamp = System.currentTimeMillis() / 1000;
    this.code = statusCode;
    this.message = message;
  }

  /**
   * 构造函数
   */
  public ApiObject(int statusCode, String message, Object data) {
    super();
    this.timestamp = System.currentTimeMillis() / 1000;
    this.code = statusCode;
    this.data = data;
    this.message = message;
  }

  /**
   * 构造函数
   */
  public ApiObject(int statusCode, String message, String data) {
    super();
    this.timestamp = System.currentTimeMillis() / 1000;
    this.code = statusCode;
    this.message = message;
    try {
      JSONObject jobj = JSONObject.parseObject(data);
      this.data = jobj;
    } catch (ClassCastException e) {
      this.data = JSONArray.parseArray(data);
    }
  }


  /**
   * 返回 statusCode 的值
   *
   * @return statusCode
   */
  public int getCode() {
    return this.code;
  }

  /**
   * 设置 statusCode 的值
   */
  public void setCode(int statusCode) {
    this.code = statusCode;
  }

  /**
   * 返回 message 的值
   *
   * @return message
   */
  public String getMessage() {
    return message;
  }

  /**
   * 设置 message 的值
   */
  public void setMessage(String message) {
    this.message = message;
  }


  /**
   * 返回操作成功状态码和消息
   */
  public static ApiObject newOk() {
    return new ApiObject(STATUS_CODE_SUCCESS, "success");
  }

  /**
   * 返回操作成功状态码和消息
   */
  public static ApiObject newOk(String message) {
    return new ApiObject(STATUS_CODE_SUCCESS, message);
  }

  /**
   * 返回操作成功状态码和消息内容
   */
  public static ApiObject newOk(String message, String data) {

    return new ApiObject(STATUS_CODE_SUCCESS, message, data);
  }

  /**
   * 返回操作成功状态码和消息
   */
  public static ApiObject newOk(String message, Object data) {
    return new ApiObject(STATUS_CODE_SUCCESS, message, data);
  }

  /**
   * 返回操作成功状态码和消息
   */
  public static ApiObject newOk(Object data) {
    return new ApiObject(STATUS_CODE_SUCCESS, "操作成功", data);
  }

  /**
   * 返回操作失败的状态码和消息
   */
  public static ApiObject newError(String message) {
    return new ApiObject(STATUS_CODE_FAILURE, message);
  }

  /**
   * 返回操作失败的状态码和消息，输入不合法
   */
  public static ApiObject newViolation(String message) {
    ApiObject apiObject = new ApiObject(STATUS_CODE_VIOLATION, message);
    return apiObject;
  }

  /**
   * 返回会话超时的状态码和消息
   */
  public static ApiObject newTimeout(String message) {
    return new ApiObject(STATUS_CODE_TIMEOUT, message);
  }


  /**
   * 返回禁止操作的状态码和消息
   */
  public static ApiObject newForbidden(String message) {
    return new ApiObject(STATUS_CODE_FORBIDDEN, message);
  }


  /**
   * @return json字符串
   */
  @Override
  public String toString() {
    JSONObject meta = new JSONObject();
    meta.put("code", this.getCode());
    meta.put("message", this.getMessage());

    if (data != null) {
      meta.put("data", data);
    }

    return meta.toJSONString();
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public Map<String, Object> getModelMap() {
    Map<String, Object> meta = Maps.newHashMap();
    meta.put("code", this.getCode());
    meta.put("message", this.getMessage());

    if (data != null) {
      meta.put("data", data);
    }
    return meta;
  }
}

