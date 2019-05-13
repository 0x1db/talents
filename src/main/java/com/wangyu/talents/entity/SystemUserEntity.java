package com.wangyu.talents.entity;

import com.wangyu.talents.common.base.BaseEntity;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class SystemUserEntity extends BaseEntity {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 5494607584383665640L;

  /**
   * 用户名
   */
  @Column(name = "username", length = 32, unique = true, nullable = false)
  private String username;

  /**
   * 密码
   */
  @Column(name = "password", length = 64)
  private String password;

  /**
   * 真实姓名
   */
  @Column(name = "true_name", length = 32)
  private String trueName;

  /**
   * 状态 0：禁用 1：正常 TODO 使用枚举
   */
  @Column(name = "status", length = 2)
  private String status = "1";

  /**
   * 与对象多对多关联
   */
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
  private Set<SystemRoleEntity> roles;

  /**
   * 创建人
   */
  @ManyToOne
  @JoinColumn(name = "creator_id")
  private SystemUserEntity creator;

  /**
   * 最后一次修改人
   */
  @ManyToOne
  @JoinColumn(name = "modifier_id")
  private SystemUserEntity modifier;

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

  public String getTrueName() {
    return trueName;
  }

  public void setTrueName(String trueName) {
    this.trueName = trueName;
  }

  public Set<SystemRoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<SystemRoleEntity> roles) {
    this.roles = roles;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
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
}
