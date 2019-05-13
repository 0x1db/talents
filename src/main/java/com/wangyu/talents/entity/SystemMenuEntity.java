package com.wangyu.talents.entity;

import com.wangyu.talents.common.base.BaseEntity;
import com.wangyu.talents.common.enums.StatusEnum;
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
@Table(name = "sys_menu")
public class SystemMenuEntity extends BaseEntity {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 6646908492537789225L;

  /**
   * 菜单名称
   */
  @Column(name = "name", length = 64, nullable = false)
  private String name;

  /**
   * 权限URL
   */
  @Column(name = "url", length = 128, nullable = false)
  private String url = "";

  /**
   * 授权（多个用逗号分割，如：user:view,user:create）
   */
  @Column(name = "perms", length = 64)
  private String perms;

  /**
   * 类型 0：目录 1：菜单 2:按钮
   */
  @Column(name = "type", length = 2)
  private String type;

  /**
   * 权限描述
   */
  @Column(name = "description", length = 100)
  private String description;

  /**
   * 状态
   */
  @Column(name = "status", length = 2)
  @Enumerated(EnumType.ORDINAL)
  private StatusEnum status = StatusEnum.NORMAL;

  /**
   * 对应角色
   */
  @ManyToMany(mappedBy = "menus", fetch = FetchType.LAZY)
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPerms() {
    return perms;
  }

  public void setPerms(String perms) {
    this.perms = perms;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<SystemRoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<SystemRoleEntity> roles) {
    this.roles = roles;
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

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SystemMenuEntity)) {
      return false;
    }
    SystemMenuEntity that = (SystemMenuEntity) o;
    return name.equals(that.name) &&
        url.equals(that.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, url);
  }
}
