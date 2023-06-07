package com.biology.commons.interact.vo.sys.user;

import cn.hutool.core.date.DatePattern;
import com.biology.commons.constant.Constants;
import com.biology.commons.interact.vo.sys.role.SysRoleVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 13:54
 * @desc:
 */
@ApiModel("用户信息")
public class SysUserVo implements Serializable {

    private static final long serialVersionUID = -5569412657944927129L;

    @ApiModelProperty(value = "用户id")
    private String id;

    @JsonIgnore
    @ApiModelProperty(value = "账号")
    private String account;

    @JsonIgnore
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "性别(0:男；1:女)")
    private Integer sex;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "账号是否可用。默认为true（false:不可用；true：可用）")
    private Boolean enabled;

    @ApiModelProperty(value = "是否是管理员")
    private boolean admin;

    @ApiModelProperty(value = "上一次登录时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date lastLoginTime;

    @ApiModelProperty(value = "角色信息集")
    private List<SysRoleVo> sysRoleVos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAdmin() {

        return admin;
    }


    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public List<SysRoleVo> getSysRoleVos() {
        return sysRoleVos;
    }

    public void setSysRoleVos(List<SysRoleVo> sysRoleVos) {
        List<SysRoleVo> roleVos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(sysRoleVos)) {
            roleVos = sysRoleVos.stream().filter(e -> e.getRoleCode().equals(Constants.ADMIN)).collect(Collectors.toList());
        }
        this.admin = !CollectionUtils.isEmpty(roleVos);
        this.sysRoleVos = sysRoleVos;
    }
}
