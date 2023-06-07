package com.biology.commons.interact.vo.sys.role;

import com.biology.commons.interact.vo.sys.permission.SysPermissionVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 17:02
 * @desc:
 */
@ApiModel("角色信息")
@Data
public class SysRoleVo implements Serializable {

    private static final long serialVersionUID = -4337630255945089454L;

    @ApiModelProperty(value = "角色id")
    private String id;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    @ApiModelProperty(value = "权限信息集")
    private List<SysPermissionVo> sysPermissionVos;
}
