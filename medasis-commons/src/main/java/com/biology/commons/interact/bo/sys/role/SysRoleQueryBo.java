package com.biology.commons.interact.bo.sys.role;

import com.biology.commons.interact.bo.PageBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 16:10
 * @desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleQueryBo extends PageBo implements Serializable {

    private static final long serialVersionUID = -4726439042751033873L;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

}
