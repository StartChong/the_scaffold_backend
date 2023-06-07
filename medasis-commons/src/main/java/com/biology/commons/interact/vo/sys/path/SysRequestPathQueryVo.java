package com.biology.commons.interact.vo.sys.path;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 17:17
 * @desc:
 */
@Data
@ApiModel("请求路径信息")
public class SysRequestPathQueryVo implements Serializable {

    private static final long serialVersionUID = 8181359320099182001L;

    @ApiModelProperty(value = "权限id")
    private String id;

    @ApiModelProperty(value = "请求路径")
    private String reqUrl;

    @ApiModelProperty(value = "路径描述")
    private String reqDesc;

}
