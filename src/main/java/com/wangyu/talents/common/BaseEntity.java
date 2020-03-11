package com.wangyu.talents.common;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import java.io.Serializable;

/**
 * 实体基类
 *
 * @author wangyu
 * @Date 2019/7/5 23:35
 * @Version 1.0
 **/
public class BaseEntity<T> implements Serializable {

  /**
   * ID
   */
  @TableId(type = IdType.AUTO)
  protected Long id;

  /**
   * 创建时间
   */
  @TableField("create_date")
  protected Long createDate;

  /**
   * 修改时间
   */
  @TableField("modify_date")
  protected Long modifyDate;

  /**
   * 删除标志 0:已删除 1：未删除
   */
  @TableLogic
  @TableField("del_flag")
  protected Integer delFlag;

  /**
   * 插入之前执行方法，需要手动调用
   */
  public void preInsert() {
    this.createDate = System.currentTimeMillis() / 1000;
  }

  /**
   * 更新之前执行方法，需要手动调用
   */
  public void preUpdate() {
    this.modifyDate = System.currentTimeMillis() / 1000;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Long createDate) {
    this.createDate = createDate;
  }

  public Long getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Long modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Integer getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(Integer delFlag) {
    this.delFlag = delFlag;
  }

}
