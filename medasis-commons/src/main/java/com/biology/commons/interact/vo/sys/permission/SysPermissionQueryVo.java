package com.biology.commons.interact.vo.sys.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 17:07
 * @desc:
 */
@Data
@ApiModel("权限列表信息")
public class SysPermissionQueryVo implements Serializable {

    private static final long serialVersionUID = -2893192353299181244L;

    @ApiModelProperty(value = "权限id")
    private String id;

    @ApiModelProperty(value = "权限编码")
    private String permissionCode;

    @ApiModelProperty(value = "权限名称")
    private String permissionName;

}
