package com.wangyu.talents.entity;

import com.wangyu.talents.common.base.BaseEntity;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class SystemRoleEntity extends BaseEntity {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1571980488758408855L;

  /**
   * 角色名
   */
  @Column(name = "name", nullable = false)
  private String name;

  /**
   * 1:正常  0: 禁用 默认为正常
   */
  @Column(name = "status", length = 32)
  private String status = "1";

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
  @JoinColumn(name = "creator_id")
  private SystemUserEntity creator;

  /**
   * 最后一次修改人
   */
  @ManyToOne
  @JoinColumn(name = "modifier_id")
  private SystemUserEntity modifier;

  /**
   * 是否删除 false : 已删除 true :正常
   */
  @Column(name = "delete_flag")
  private Boolean deleteFlag = true;

  /**
   * 备注
   */
  @Column(name = "remark", length = 64)
  private String remark;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
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

  public Boolean getDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(Boolean deleteFlag) {
    this.deleteFlag = deleteFlag;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Set<SystemResourceEntity> getSystemResources() {
    return systemResources;
  }

  public void setSystemResources(
      Set<SystemResourceEntity> systemResources) {
    this.systemResources = systemResources;
  }
}
