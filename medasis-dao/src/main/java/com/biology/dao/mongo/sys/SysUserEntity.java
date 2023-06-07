package com.biology.dao.mongo.sys;

import cn.hutool.core.date.DatePattern;
import com.biology.commons.base.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-25 14:27
 * @desc: 用户表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document(value = "sys_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserEntity extends BaseEntity {

    @Id
    String id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "性别(0:男；1:女)")
    private Integer sex;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "账号是否可用。默认为true（false:不可用；true：可用）")
    private Boolean enabled;

    @ApiModelProperty(value = "上一次登录时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date lastLoginTime;

}
