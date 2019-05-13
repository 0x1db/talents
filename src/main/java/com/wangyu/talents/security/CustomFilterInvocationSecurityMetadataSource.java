package com.wangyu.talents.security;

import com.wangyu.talents.service.SystemMenuService;
import com.wangyu.talents.service.SystemRoleService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

/**
 * @author yinwenjie
 */
@Service("CustomFilterInvocationSecurityMetadataSource")
public class CustomFilterInvocationSecurityMetadataSource implements
    FilterInvocationSecurityMetadataSource {

  @Autowired
  private SystemMenuService competenceService;

  @Autowired
  private SystemRoleService roleService;

  /**
   * 忽略权限判断的url
   */
  @Value("${author.ignoreUrls}")
  private String[] ignoreUrls;

  /* (non-Javadoc)
   * @see org.springframework.security.access.SecurityMetadataSource#getAttributes(java.lang.Object)
   */
  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    return null;
  }

  /**
   * 查询当前权限设置中，符合当前http访问路径的角色信息。（注意没有通过method过滤）
   */

  /* (non-Javadoc)
   * @see org.springframework.security.access.SecurityMetadataSource#getAllConfigAttributes()
   */
  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.access.SecurityMetadataSource#supports(java.lang.Class)
   */
  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}