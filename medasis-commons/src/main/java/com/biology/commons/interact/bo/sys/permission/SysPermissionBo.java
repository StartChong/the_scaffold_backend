package com.biology.commons.interact.bo.sys.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 16:18
 * @desc:
 */
@Data
public class SysPermissionBo implements Serializable {

    private static final long serialVersionUID = -657142405100598992L;

    @ApiModelProperty(value = "权限编码", required = true)
    @NotBlank(message = "权限编码不能为空！")
    private String permissionCode;

    @ApiModelProperty(value = "权限名称", required = true)
    @NotBlank(message = "权限名称不能为空！")
    private String permissionName;

    @ApiModelProperty(value = "请求路径编号集")
    private List<String> reqPathIds;

}
