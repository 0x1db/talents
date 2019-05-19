package com.wangyu.talents.entity;

import com.wangyu.talents.common.base.BaseEntity;
import com.wangyu.talents.common.enums.ResourceTypeEnum;
import com.wangyu.talents.common.enums.StatusEnum;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@org.hibernate.annotations.Table(appliesTo = "sys_resource", comment = "系统资源表")
public class SystemResourceEntity extends BaseEntity {

  private static final long serialVersionUID = -5541630050367795602L;

  /**
   * 权限URL串
   **/
  @Column(name = "resource", length = 100, nullable = false, columnDefinition = "varchar(100) COMMENT '权限URL串'")
  private String resource;

  /**
   * 涉及的方法描述 例如：POST或者POST|GET|DELETE|PATCH 等等
   */
  @Column(name = "methods", nullable = false, length = 10, columnDefinition = "varchar(10) COMMENT '方法'")
  private String methods;

  /**
   * 状态 1：正常, 0禁用（枚举）
   **/
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "status", nullable = false, columnDefinition = "int(2) COMMENT '状态 1：正常 0:禁用'")
  private StatusEnum status = StatusEnum.STATUS_NORMAL;

  /**
   * 创建人
   **/
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_id", columnDefinition = "varchar(36) COMMENT '创建人ID'")
  private SystemUserEntity creator;

  /**
   * 权限对应的角色信息
   */
  @ManyToMany(mappedBy = "systemResources", fetch = FetchType.LAZY)
  private Set<SystemRoleEntity> roles;

  /**
   * 描述
   **/
  @Column(name = "remark", length = 64, columnDefinition = "varchar(64) COMMENT '描述'")
  private String description;

  /**
   * 最后一次修改人
   */
  @ManyToOne
  @JoinColumn(name = "modifier_id", columnDefinition = "varchar(36) COMMENT '修改人ID'")
  private SystemUserEntity modifier;

  /**
   * 资源类型 0：导航组件 1：菜单 2：按钮(枚举)
   */
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "type", length = 2, columnDefinition = "int(2) COMMENT '资源类型'")
  private ResourceTypeEnum type;

  /**
   * 父级资源
   */
  @OneToOne
  @JoinColumn(name = "parent_id", columnDefinition = "varchar(36) COMMENT '父级ID'")
  private SystemResourceEntity parent;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public SystemUserEntity getModifier() {
    return modifier;
  }

  public void setModifier(SystemUserEntity modifier) {
    this.modifier = modifier;
  }

  public ResourceTypeEnum getType() {
    return type;
  }

  public void setType(ResourceTypeEnum type) {
    this.type = type;
  }

  public SystemResourceEntity getParent() {
    return parent;
  }

  public void setParent(SystemResourceEntity parent) {
    this.parent = parent;
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
