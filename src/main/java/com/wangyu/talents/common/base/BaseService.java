package com.wangyu.talents.common.base;

import java.util.List;
import org.springframework.data.domain.Sort;

/**
 * 服务层基类接口封装
 *
 * @author wangyu
 * @Date 2019/4/14 18:03
 * @Version 1.0
 **/
public interface BaseService<T extends BaseEntity> {

  /**
   * 保存当个实体
   */
  void create(T entity);

  /**
   * 修改单个实体
   */
  void update(T entity);

  /**
   * 根据ID删除 （逻辑删除）
   */
  void delete(String id);

  /**
   * 根据ID查询
   */
  T findById(String id);

  /**
   * 根据排序条件查询列表
   */
  List<T> findList(Sort sort);
}
