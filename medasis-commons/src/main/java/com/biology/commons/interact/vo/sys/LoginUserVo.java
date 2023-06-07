package com.biology.commons.interact.vo.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.biology.commons.interact.vo.sys.user.SysUserVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 18:05
 * @desc:
 */
@ApiModel("登录用户信息")
@Data
public class LoginUserVo implements UserDetails {

    private static final long serialVersionUID = -1708529795884306067L;

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "token令牌")
    private String token;

    @JsonIgnore
    @ApiModelProperty(value = "登录时间")
    private Long loginTime;

    @JsonIgnore
    @ApiModelProperty(value = "过期时间")
    private Long expireTime;

    @JsonIgnore
    @ApiModelProperty(value = "注册权限集")
    List<GrantedAuthority> grantedAuthorities;

    @ApiModelProperty(value = "用户信息")
    private SysUserVo sysUserVo;

    LoginUserVo() {

    }

    public LoginUserVo(String id, SysUserVo sysUserVo, List<GrantedAuthority> grantedAuthorities) {
        this.id = id;
        this.sysUserVo = sysUserVo;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return sysUserVo.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return sysUserVo.getAccount();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return sysUserVo.getEnabled();
    }

}
