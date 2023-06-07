package com.biology.threeparty.filter;


import com.biology.commons.config.WhiteListConfig;
import com.biology.commons.constant.Constants;
import com.biology.commons.utils.RedisUtil;
import com.biology.dao.mongo.sys.SysPermissionEntity;
import com.biology.service.sys.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;


/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 14:10
 * @desc: 根据请求，查询数据库，看看这个请求是那些角色能访问
 */
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private WhiteListConfig whiteListConfig;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        // 放开无需验证的url
        if (!CollectionUtils.isEmpty(whiteListConfig.getInclude())) {
            for (String s : whiteListConfig.getInclude()) {
                if (requestUrl.contains(s)) {
                    return null;
                }
            }
        }
        // 查询具体某个接口的权限
        Map<String, List<SysPermissionEntity>> map = redisUtil.getCacheMap(Constants.PATH_CACHE);
        List<SysPermissionEntity> permissionList = null;
        if (Objects.nonNull(map)) {
            permissionList = map.get(requestUrl);
        } else {
            map = new HashMap<>();
        }
        if (CollectionUtils.isEmpty(permissionList)) {
            permissionList = sysPermissionService.findListByPath(requestUrl);
            map.put(requestUrl, permissionList);
            redisUtil.setCacheMap(Constants.PATH_CACHE, map);
        }
        if (permissionList == null || permissionList.size() == 0) {
            // 请求路径没有配置权限，表明该请求接口可以任意访问
            return null;
        }
        String[] attributes = new String[permissionList.size()];
        for (int i = 0; i < permissionList.size(); i++) {
            attributes[i] = permissionList.get(i).getPermissionCode();
        }
        return SecurityConfig.createList(attributes);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}