package com.wangyu.talents.common.base;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

/**
 * 服务层基类实现类封装
 *
 * @author wangyu
 * @Date 2019/4/14 18:04
 * @Version 1.0
 **/
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

  @Autowired
  private BaseRepository<T> baseRepository;

  @Override
  public void create(T entity) {
    baseRepository.save(entity);
  }

  @Override
  public void update(T entity) {
    baseRepository.saveAndFlush(entity);
  }

  @Override
  public void delete(String id) {
    T entity = baseRepository.findOne(id);
    if (entity != null) {
      entity.setDeleteFlag(false);
      baseRepository.saveAndFlush(entity);
    } else {

    }
  }

  @Override
  public T findById(String id) {
    return baseRepository.findOne(id);
  }

  @Override
  public List<T> findList(Sort sort) {
    return baseRepository.findAll(sort);
  }

}
