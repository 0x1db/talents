package com.wangyu.talents.common.enums;

/**
 * 资源类型枚举
 *
 * @Author wangyu
 * @Date 2019/5/18 14:30
 * @Version 1.0
 */
public enum ResourceTypeEnum implements EnumTypeInterface {
  RESOURCE_COMPONENT(0, "导航组件"),
  RESOURCE_MENU(1, "菜单"),
  RESOURCE_BUTTON(2, "按钮");

  private int value;
  private String desc;

  ResourceTypeEnum(int value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  @Override
  public int getValue() {
    return this.value;
  }

  @Override
  public String getDesc() {
    return this.desc;
  }

  public static ResourceTypeEnum get(int value) {
    for (ResourceTypeEnum resource : ResourceTypeEnum.values()) {
      if (resource.value == value) {
        return resource;
      }
    }
    throw new IllegalArgumentException("argument error: " + value);
  }
}
