package com.wangyu.talents.common.enums;

/**
 * 状态枚举类型
 *
 * @author wangyu
 * @Date 2019/5/6 22:34
 * @Version 1.0
 **/
public enum StatusEnum implements EnumTypeInterface {
  STATUS_DISABLED(0, "禁用"),
  STATUS_NORMAL(1, "正常");

  StatusEnum(int value, String desc) {
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

  public static StatusEnum get(int value) {
    for (StatusEnum me : StatusEnum.values()) {
      if (me.value == value) {
        return me;
      }
    }
    throw new IllegalArgumentException("argument error: " + value);
  }
}
