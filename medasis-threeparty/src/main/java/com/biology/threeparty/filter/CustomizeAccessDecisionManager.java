package com.biology.threeparty.filter;

import com.biology.commons.constant.Constants;
import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.exception.SysException;
import com.google.common.collect.Lists;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 14:10
 * @desc: 匹对权限
 */
@Component
public class CustomizeAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // 用户是否登录
        if (authentication.getPrincipal().toString().equals("anonymousUser")) {
            throw new InsufficientAuthenticationException(ResultCodeEnum.USER_NOT_LOGIN.message());
        }
        // 当前用户所具有的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> resultAuthority = Lists.newArrayList();
        for (GrantedAuthority authority : authorities) {
            resultAuthority.add(authority.getAuthority());
        }
        // 管理员有所有权
        if (resultAuthority.contains(Constants.ALL_PERMISSION)) {
            return;
        }
        // 非管理获取权限
        for (ConfigAttribute ca : collection) {
            // 当前请求需要的权限
            if (resultAuthority.contains(ca.getAttribute())) {
                return;
            }
        }
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
