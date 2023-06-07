package com.biology.commons.interact.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 15:41
 * @desc:
 */
@Data
public class IdStrBo implements Serializable {

    private static final long serialVersionUID = -1195681814375700490L;

    @NotBlank(message = "id不能为空！")
    @ApiModelProperty(value = "id", required = true)
    private String id;

}
