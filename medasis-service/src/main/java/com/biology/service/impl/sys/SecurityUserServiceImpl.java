package com.biology.service.impl.sys;

import com.biology.commons.constant.Constants;
import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.exception.SysException;
import com.biology.commons.interact.vo.sys.LoginUserVo;
import com.biology.commons.interact.vo.sys.user.SysUserVo;
import com.biology.dao.mongo.sys.SysPermissionEntity;
import com.biology.dao.mongo.sys.SysUserEntity;
import com.biology.service.sys.SecurityUserService;
import com.biology.service.sys.SysPermissionService;
import com.biology.service.sys.SysRoleService;
import com.biology.service.sys.SysUserService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:02
 * @desc:
 */
@Service
@Slf4j
public class SecurityUserServiceImpl implements SecurityUserService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private SysRoleService sysRoleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 用户名必须是唯一的，不允许重复
        SysUserEntity sysUserEntity = userService.findOne(SysUserEntity.builder().account(username).build());
        //用户是否存在
        SysException.throwException(Objects.isNull(sysUserEntity), ResultCodeEnum.USER_CREDENTIALS_ERROR.code(), "账号或密码错误！");
        SysException.throwException(!sysUserEntity.getEnabled(), ResultCodeEnum.USER_ACCOUNT_DISABLE.code(), ResultCodeEnum.USER_ACCOUNT_DISABLE.message());
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUserEntity, sysUserVo);
        sysUserVo.setSysRoleVos(sysRoleService.findByUserId(sysUserEntity.getId()));
        // 是否是管理员
        // 用户权限
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        if (sysUserVo.isAdmin()) {
            // *:*:*
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(Constants.ALL_PERMISSION);
            grantedAuthorities.add(grantedAuthority);
        } else {
            Set<SysPermissionEntity> sysPermissionEntities = permissionService.getPermissionsByUserId(sysUserEntity.getId());
            if (Objects.nonNull(sysPermissionEntities)) {
                sysPermissionEntities.forEach(permission -> {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPermissionCode());
                    grantedAuthorities.add(grantedAuthority);
                });
            }
        }
        // 记录登录时间
        SysUserEntity entity = new SysUserEntity();
        entity.setLastLoginTime(new Date());
        entity.setUpdateBy(sysUserEntity.getId());
        entity.setUpdateTime(new Date());
        userService.updateById(sysUserEntity.getId(), entity);
        return new LoginUserVo(sysUserEntity.getId(), sysUserVo, grantedAuthorities);
    }


}
