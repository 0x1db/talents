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
 * 系统机构实体
 *
 * @author wangyu
 * @Date 2019/3/11 21:15
 * @Version 1.0
 **/
@Entity
@Table(name = "sys_dept")
public class SystemDeptEntity extends BaseEntity {

  private static final long serialVersionUID = -6695863802888650777L;

  /**
   * 机构名称
   */
  @Column(name = "name", length = 50, nullable = false, columnDefinition = "varchar(50) COMMENT '机构名称'")
  private String name;

  /**
   * 上级机构
   */
  @ManyToOne
  @JoinColumn(name = "parent_id", columnDefinition = "varchar(200) COMMENT '上级机构ID'")
  private SystemDeptEntity parent;

  /**
   * 排序
   */
  @Column(name = "order_num", columnDefinition = "int(11) COMMENT '排序'")
  private Integer orderNum;

  /**
   * 创建人
   */
  @ManyToOne
  @JoinColumn(name = "creator_id", columnDefinition = "varchar(200) COMMENT '创建人ID'")
  private SystemUserEntity creator;

  /**
   * 最后一次修改人
   */
  @ManyToOne
  @JoinColumn(name = "modifier_id", columnDefinition = "varchar(200) COMMENT '最后一次修改人ID'")
  private SystemUserEntity modifier;

  /**
   * 是否删除 false : 已删除 true :正常
   */
  @Column(name = "delete_flag", columnDefinition = "tinyint(1) COMMENT '删除标志'")
  private Boolean deleteFlag = true;

  /**
   * 与角色多对多关联
   */
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "depts")
  private Set<SystemRoleEntity> roles;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SystemDeptEntity getParent() {
    return parent;
  }

  public void setParent(SystemDeptEntity parent) {
    this.parent = parent;
  }

  public Integer getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(Integer orderNum) {
    this.orderNum = orderNum;
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

  public Set<SystemRoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<SystemRoleEntity> roles) {
    this.roles = roles;
  }
}
