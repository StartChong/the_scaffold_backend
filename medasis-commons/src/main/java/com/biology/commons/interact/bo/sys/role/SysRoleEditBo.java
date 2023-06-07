package com.biology.commons.interact.bo.sys.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 16:10
 * @desc:
 */
@Data
public class SysRoleEditBo implements Serializable {

    private static final long serialVersionUID = -4726439042751033873L;

    @NotBlank(message = "角色id不能为空！")
    @ApiModelProperty(value = "角色id", required = true)
    private String id;

    @ApiModelProperty(value = "角色编码", required = true)
    @NotBlank(message = "角色编码不能为空！")
    private String roleCode;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空！")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    @ApiModelProperty(value = "权限编号集(为空不进行任何修改！)")
    private List<String> permissionIds;

}
