package com.biology.commons.interact.bo.sys.path;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 16:27
 * @desc:
 */
@Data
public class SysRequestPathBo implements Serializable {

    private static final long serialVersionUID = 8840823026261729014L;

    @ApiModelProperty(value = "请求路径", required = true)
    @NotBlank(message = "请求路径不能为空！")
    private String reqUrl;

    @ApiModelProperty(value = "路径描述", required = true)
    @NotBlank(message = "路径描述不能为空！")
    private String reqDesc;

}
