package com.wangyu.talents.common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应编码，记录了所有执行者向调用者返回的处理结果编码
 *
 * @author wenjie
 * @modifier wangyu
 * @ModifiedDate 2019-04-10
 */
public enum ResponseCode {
  //HTTP状态码
  _200("200", "请求成功"),
  _301("301", "请求次数已经超过本周期设置的最大值"),
  _302("302", "请求频率已超过设定的最大值"),
  _303("303", "该接口工作繁忙，产生拥堵，请稍后再试"),
  _304("304", "未修改,缓存失效"),
  _400("400", "服务器不理解请求的语法"),
  _401("401", "未授权，请客户端进行身份认证"),
  //  _402("402", "未使用，为未来预留"),
  _403("403", "服务器拒绝请求"),
  _404("404", "服务器找不到请求的路径"),
  _405("405", "禁用请求中指定的方法"),
  _406("406", "无法接受"),
  _407("407", "需要代理授权"),
  _408("408", "请求超时"),
  _409("409", "服务器在完成请求时发生冲突"),
  _500("500", "服务器内部错误"),
  _501("501", "服务器不具备完成请求的功能"),
  _502("502", "错误网关"),
  _503("503", "服务不可用"),
  _504("504", "网关超时"),

  //业务状态码
  _1001("1001", "规定的必传项没有传入"),
  _1002("1002", "传入的参数项格式不符合规定"),
  _1003("1003", "传入参数项至少有一项超出规定的长度"),
  _1004("1004", "没有查询到符合条件的数据"),
  _1005("1005", "查询到重复数据"),
  _1006("1006", "传入的参数编码格式失效"),
  _1007("1007", "查询到的数据不可用"),
  _1008("1008", "空指针异常"),
  _1009("1009", "IO异常"),
  _1010("1010", "非法参数异常"),
  _1011("1011", "登录信息失效");

  private String code;
  private String desc;

  ResponseCode(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  ResponseCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }

  public String getDesc() {
    return this.desc;
  }

  /**
   * 该私有静态方法用于映射字符串和枚举信息的关系
   */
  private static final Map<ResponseCode, String> stringToEnum = new HashMap<ResponseCode, String>();

  static {
    for (ResponseCode blah : values()) {
      stringToEnum.put(blah, blah.toString());
    }
  }

  /**
   *
   */
  public static String fromString(ResponseCode symbol) {
    return stringToEnum.get(symbol);
  }

  @Override
  public String toString() {
    return code;
  }
}