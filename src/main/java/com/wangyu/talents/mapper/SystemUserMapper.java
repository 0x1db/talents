package com.wangyu.talents.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wangyu.talents.domain.SystemUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 系统用户持久层
 *
 * @author wangyu
 * @Date 2019/7/4 23:58
 * @Version 1.0
 **/
@Mapper
@Repository("userMapper")
public interface SystemUserMapper extends BaseMapper<SystemUser> {

}
