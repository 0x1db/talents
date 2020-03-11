package com.wangyu.talents.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wangyu.talents.common.BaseEntity;

/**
 * 系统用户实体
 *
 * @author wangyu
 * @Date 2019/7/4 23:57
 * @Version 1.0
 **/
@TableName(value = "sys_user")
public class SystemUser extends BaseEntity<SystemUser> {

  /**
   * 用户名
   */
  @TableField("user_name")
  private String userName;

  /**
   * 密码
   */
  @TableField("password")
  private String password;

  /**
   * 真实姓名
   */
  @TableField("true_name")
  private String trueName;

  /**
   * 昵称
   */
  @TableField("nick_name")
  private String nickName;

  /**
   * 创建人
   */
  @TableField("creator")
  protected Long creator;

  /**
   * 修改人
   */
  @TableField("modifier")
  protected Long modifier;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTrueName() {
    return trueName;
  }

  public void setTrueName(String trueName) {
    this.trueName = trueName;
  }

  public Long getCreator() {
    return creator;
  }

  public void setCreator(Long creator) {
    this.creator = creator;
  }

  public Long getModifier() {
    return modifier;
  }

  public void setModifier(Long modifier) {
    this.modifier = modifier;
  }
}
