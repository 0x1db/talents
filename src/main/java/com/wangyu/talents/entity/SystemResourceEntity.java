package com.wangyu.talents.entity;

import com.wangyu.talents.common.base.BaseEntity;
import com.wangyu.talents.common.enums.StatusEnum;
import java.util.Date;
import java.util.Objects;
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
 * 系统菜单权限实体
 *
 * @author wangyu
 * @Date 2019/2/19 17:31
 * @Version 1.0
 **/
@Entity
@Table(name = "sys_resource")
public class SystemResourceEntity extends BaseEntity {

  /**
   * 权限URL串.
   **/
  @Column(name = "resource", nullable = false)
  private String resource = "";

  /**
   * 涉及的方法描述<br> 例如：POST或者POST|GET|DELETE|PATCH 等等
   */
  @Column(name = "methods", nullable = false)
  private String methods = "";

  /**
   * 创建时间.
   **/
  @Column(name = "create_date", nullable = false)
  private Date createDate = new Date();

  /**
   * 修改时间.
   **/
  @Column(name = "modified_date")
  private Date modifiedDate;

  /**
   * 状态 1正常, 0禁用（枚举）.
   **/
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "status", nullable = false)
  private StatusEnum status = StatusEnum.NORMAL;

  /**
   * 创建人.
   **/
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_id", nullable = false)
  private SystemUserEntity creator;

  /**
   * 权限对应的角色信息
   */
  @ManyToMany(mappedBy = "systemResources", fetch = FetchType.LAZY)
  private Set<SystemRoleEntity> roles;

  /**
   * 备注.
   **/
  @Column(name = "remark", nullable = false)
  private String remark = "";

  /**
   * 最后一次修改人
   */
  @ManyToOne
  @JoinColumn(name = "modifier_id")
  private SystemUserEntity modifier;

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public String getMethods() {
    return methods;
  }

  public void setMethods(String methods) {
    this.methods = methods;
  }

  @Override
  public Date getCreateDate() {
    return createDate;
  }

  @Override
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Override
  public Date getModifiedDate() {
    return modifiedDate;
  }

  @Override
  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = modifiedDate;
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

  public Set<SystemRoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<SystemRoleEntity> roles) {
    this.roles = roles;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public SystemUserEntity getModifier() {
    return modifier;
  }

  public void setModifier(SystemUserEntity modifier) {
    this.modifier = modifier;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SystemResourceEntity)) {
      return false;
    }

    SystemResourceEntity that = (SystemResourceEntity) o;

    if (!resource.equals(that.resource)) {
      return false;
    }
    return methods.equals(that.methods);
  }

  @Override
  public int hashCode() {
    int result = resource.hashCode();
    result = 31 * result + methods.hashCode();
    return result;
  }
}
