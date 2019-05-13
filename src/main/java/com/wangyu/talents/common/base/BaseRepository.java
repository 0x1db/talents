package com.wangyu.talents.common.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 持久层基类接口封装
 *
 * @author wangyu
 * @NoRepositoryBean ：保添加了该注解的 repository 接口不会在运行时被创建实例。 也就是说，使用了该注解的接口不会被单独创建实例，只会作为其他接口的父接口而被使用
 * @Date 2019/4/14 21:09
 * @Version 1.0
 **/
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {

}
