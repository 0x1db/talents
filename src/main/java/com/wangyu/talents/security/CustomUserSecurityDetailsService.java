package com.wangyu.talents.security;

import com.wangyu.talents.dao.SystemUserRepository;
import com.wangyu.talents.entity.SystemRoleEntity;
import com.wangyu.talents.entity.SystemUserEntity;
import com.wangyu.talents.service.SystemRoleService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author wangyu
 * @Date 2019/3/9 11:26
 * @Version 1.0
 **/
@Service
public class CustomUserSecurityDetailsService implements UserDetailsService {

  @Autowired
  private SystemUserRepository userDao;

  @Autowired
  private SystemRoleService roleService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SystemUserEntity currentUser = userDao.findByUsernameAndStatus(username, "1");
    if (currentUser == null) {
      throw new UsernameNotFoundException("未发现指定账号，或账号被禁用");
    }
    List<SystemRoleEntity> roleList = null;

    roleList = roleService.findByUserId(currentUser.getId());
    if (roleList == null || roleList.isEmpty()) {
      throw new UsernameNotFoundException("用户权限状态错误");
    }
    List<SimpleGrantedAuthority> authorities = new LinkedList<>();
    for (SystemRoleEntity role : roleList) {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
      authorities.add(authority);
    }

    UserDetails securityDetails = new User(username, currentUser.getPassword(), authorities);
    return securityDetails;
  }
}
