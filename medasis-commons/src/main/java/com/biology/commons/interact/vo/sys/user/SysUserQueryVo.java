package com.biology.commons.interact.vo.sys.user;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 13:54
 * @desc:
 */
@ApiModel("用户列表信息")
@Data
public class SysUserQueryVo implements Serializable {

    private static final long serialVersionUID = -5569412657944927129L;

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "性别(0:男；1:女)")
    private Integer sex;

    @ApiModelProperty(value = "上一次登录时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date lastLoginTime;

    @ApiModelProperty(value = "账号是否可用。默认为true（false:不可用；true：可用）")
    private Boolean enabled;

}
