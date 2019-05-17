package com.wangyu.talents.entity;

import com.wangyu.talents.common.base.BaseEntity;
import com.wangyu.talents.common.enums.StatusEnum;
import com.wangyu.talents.common.enums.UserTypeEnum;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 系统用户实体
 *
 * @Author wangyu
 * @Date 2019/2/18 16:00
 * @Version 1.0
 */
@Entity
@Table(name = "sys_user")
@org.hibernate.annotations.Table(appliesTo = "sys_user", comment = "系统用户表")
public class SystemUserEntity extends BaseEntity {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 5494607584383665640L;

  /**
   * 用户名
   */
  @Column(name = "username", length = 32, unique = true, nullable = false, columnDefinition = "varchar(32) COMMENT '用户名'")
  private String username;

  /**
   * 密码
   */
  @Column(name = "password", length = 32, columnDefinition = "varchar(32) COMMENT '密码'")
  private String password;

  /**
   * 昵称
   */
  @Column(name = "nickname", length = 32, columnDefinition = "varchar(32) COMMENT '昵称'")
  private String nickname;

  /**
   * 用户头像
   */
  @Column(name = "head_img", length = 100, columnDefinition = "varchar(100) COMMENT '头像'")
  private String head_img;

  /**
   * 状态 0：禁用 1：正常
   */
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "status", length = 2, columnDefinition = "int(2) COMMENT '状态 1：正常 0：禁用'")
  private StatusEnum status = StatusEnum.STATUS_NORMAL;

  /**
   * 用户类型 1:后台用户 2：前端用户
   */
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "type", length = 2, columnDefinition = "int(2) COMMENT '用户类型 1：后台用户 2：前端用户'")
  private UserTypeEnum type;

  /**
   * 与对象多对多关联
   */
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
  private Set<SystemRoleEntity> roles;

  /**
   * 创建人
   */
  @ManyToOne
  @JoinColumn(name = "creator_id", columnDefinition = "varchar(36) COMMENT '创建人'")
  private SystemUserEntity creator;

  /**
   * 最后一次修改人
   */
  @ManyToOne
  @JoinColumn(name = "modifier_id", columnDefinition = "varchar(36) COMMENT '最后一次修改人'")
  private SystemUserEntity modifier;

  /**
   * 最后登录时间
   */
  @Column(name = "last_login_time", columnDefinition = "datetime COMMENT '最后一次登录时间'")
  private Date lastLoginTime;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<SystemRoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<SystemRoleEntity> roles) {
    this.roles = roles;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public SystemUserEntity getCreator() {
    return creator;
  }

  public void setCreator(SystemUserEntity creator) {
    this.creator = creator;
  }

  public SystemUserEntity getModifier() {
    return modifier;
  }

  public void setModifier(SystemUserEntity modifier) {
    this.modifier = modifier;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getHead_img() {
    return head_img;
  }

  public void setHead_img(String head_img) {
    this.head_img = head_img;
  }

  public UserTypeEnum getType() {
    return type;
  }

  public void setType(UserTypeEnum type) {
    this.type = type;
  }

  public Date getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }
}
