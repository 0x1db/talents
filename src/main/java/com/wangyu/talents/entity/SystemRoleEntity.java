package com.wangyu.talents.entity;

import com.wangyu.talents.common.base.BaseEntity;
import com.wangyu.talents.common.enums.StatusEnum;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 系统角色实体
 *
 * @Author wangyu
 * @Date 2019/2/18 16:22
 * @Version 1.0
 */
@Entity
@Table(name = "sys_role")
@org.hibernate.annotations.Table(appliesTo = "sys_role", comment = "系统角色表")
public class SystemRoleEntity extends BaseEntity {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1571980488758408855L;

  /**
   * 角色名
   */
  @Column(name = "name", length = 32, nullable = false, columnDefinition = "varchar(64) COMMENT '角色名'")
  private String name;

  /**
   * 描述
   */
  @Column(name = "remark", length = 64, columnDefinition = "varchar(64) COMMENT '描述'")
  private String description;

  /**
   * 1:正常  0: 禁用 默认为正常
   */
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "status", length = 2, columnDefinition = "int(2) COMMENT '状态 1：正常 0：禁用'")
  private StatusEnum status = StatusEnum.STATUS_NORMAL;

  /**
   * 与用户多对多关联
   */
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "sys_role_user", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
  private Set<SystemUserEntity> users;

  /**
   * 与权限多对多
   */
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "sys_role_resource", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "resource_id")})
  private Set<SystemResourceEntity> systemResources;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Set<SystemUserEntity> getUsers() {
    return users;
  }

  public void setUsers(Set<SystemUserEntity> users) {
    this.users = users;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<SystemResourceEntity> getSystemResources() {
    return systemResources;
  }

  public void setSystemResources(
      Set<SystemResourceEntity> systemResources) {
    this.systemResources = systemResources;
  }
}
