package com.wangyu.talents.common.base;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 * 实体基类
 *
 * @Author wangyu
 * @Date 2019/2/18 16:01
 * @Version 1.0
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -400956278045524239L;

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(length = 36, unique = true, nullable = false, columnDefinition = "varchar(36) COMMENT 'ID'")
  private String id;

  /**
   * 创建时间
   */
  @Column(name = "create_date", columnDefinition = "datetime COMMENT '创建时间'")
  private Date createDate;

  /**
   * 修改时间
   */
  @Column(name = "modified_date", columnDefinition = "datetime COMMENT '最后一次修改时间'")
  private Date modifiedDate;

  /**
   * 删除标识 false : 已删除 true :正常
   */
  @Column(name = "delete_flag", columnDefinition = "bit COMMENT '删除标识 0:已删除，1：正常'")
  private Boolean deleteFlag;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public Boolean getDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(Boolean deleteFlag) {
    this.deleteFlag = deleteFlag;
  }
}
