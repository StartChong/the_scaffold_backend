package com.biology.commons.interact.bo.sys.user;

import com.biology.commons.interact.bo.PageBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 15:39
 * @desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserQueryBo extends PageBo implements Serializable {

    private static final long serialVersionUID = -1766529190019882820L;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "账号是否可用。默认为true（false:不可用；true：可用）")
    private Boolean enabled;

}
