package com.wangyu.talents.service.impl;

import com.wangyu.talents.common.enums.ResourceTypeEnum;
import com.wangyu.talents.common.enums.StatusEnum;
import com.wangyu.talents.common.exception.ServiceException;
import com.wangyu.talents.common.model.ResponseCode;
import com.wangyu.talents.entity.SystemResourceEntity;
import com.wangyu.talents.entity.SystemRoleEntity;
import com.wangyu.talents.repository.SystemResourceRepository;
import com.wangyu.talents.service.SystemResourceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * 系统菜单业务层实现
 *
 * @author wangyu
 * @Date 2019/4/14 17:43
 * @Version 1.0
 **/
@Service("systemResourceService")
public class SystemResourceServiceImpl implements SystemResourceService {

  @Autowired
  private SystemResourceRepository resourceRepository;

  @Override
  public List<SystemResourceEntity> findByRoleId(String id) {
    return null;
  }

  @Override
  public List<SystemResourceEntity> findListByStatus(StatusEnum status) {
    Validate.notNull(status, "状态不能为空");
    return resourceRepository.findListByStatus(status);
  }

  @Override
  public List<SystemResourceEntity> findByResource(String requestUrl) {
    return resourceRepository.findByResource(requestUrl);
  }

  @Override
  public Page<SystemResourceEntity> findPages(Map<String, Object> params, Pageable pageable) {
    Page<SystemResourceEntity> list = resourceRepository
        .findAll(new Specification<SystemResourceEntity>() {
          @Override
          public Predicate toPredicate(Root<SystemResourceEntity> root, CriteriaQuery<?> query,
              CriteriaBuilder cb) {
            Class<?> clazz = query.getResultType();
            if (clazz.equals(SystemResourceEntity.class)) {
              root.fetch("creator", JoinType.LEFT);
              root.fetch("modifier", JoinType.LEFT);
              root.fetch("parent", JoinType.LEFT);
            }
            List<Predicate> predicates = new ArrayList<Predicate>();
            // 查询角色名称
            if (params.get("description") != null) {
              predicates.add(cb.like(root.get("description").as(String.class),
                  "%" + String.valueOf(params.get("description") + "%")));
            }

            // 查询状态
            if (params.get("status") != null) {
              StatusEnum status = StatusEnum.valueOf(params.get("status").toString());
              predicates.add(cb.equal(root.get("status").as(StatusEnum.class), status));
            }

            // 查询状态
            if (params.get("type") != null) {
              ResourceTypeEnum type = ResourceTypeEnum.valueOf(params.get("type").toString());
              predicates.add(cb.equal(root.get("type").as(ResourceTypeEnum.class), type));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
          }
        }, pageable);
    return list;
  }

  @Override
  public void create(SystemResourceEntity resourceEntity) {
    if (StringUtils.isEmpty(resourceEntity.getResource())) {
      throw new ServiceException(ResponseCode._1001, "资源路径不能为空");
    }
    if (StringUtils.isEmpty(resourceEntity.getMethods())) {
      throw new ServiceException(ResponseCode._1001, "请求方式不能为空");
    }

    if (resourceEntity.getParent() == null) {
      resourceEntity.setParent(null);
    }
    //将methods转为大写
    resourceEntity.setMethods(resourceEntity.getMethods().toUpperCase());
    resourceRepository.save(resourceEntity);
  }

  @Override
  public List<SystemResourceEntity> findAll() {
    return resourceRepository.findListByStatus(StatusEnum.STATUS_NORMAL);
  }
}
