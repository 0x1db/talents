package com.wangyu.talents.common.enums;

/**
 * 用户类型枚举
 *
 * @Author wangyu
 * @Date 2019/5/16 10:16
 * @Version 1.0
 */
public enum UserTypeEnum implements EnumTypeInterface {
  DEFAULT(0, "初始"),
  USER_ADMIN(1, "后台用户"),
  USER_FRONT(2, "前端用户");

  UserTypeEnum(int value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  private int value;
  private String desc;

  @Override
  public int getValue() {
    return this.value;
  }

  @Override
  public String getDesc() {
    return this.desc;
  }

  public static UserTypeEnum get(int value) {
    for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
      if (userTypeEnum.value == value) {
        return userTypeEnum;
      }
    }
    throw new IllegalArgumentException("argument error: " + value);
  }
}
