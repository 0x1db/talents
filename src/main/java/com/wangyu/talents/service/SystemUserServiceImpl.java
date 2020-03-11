package com.wangyu.talents.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangyu.talents.domain.SystemUser;
import com.wangyu.talents.mapper.SystemUserMapper;
import org.springframework.stereotype.Service;

/**
 * 系统用户业务层实现
 *
 * @author wangyu
 * @Date 2019/7/5 22:49
 * @Version 1.0
 **/
@Service("userService")
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

}
