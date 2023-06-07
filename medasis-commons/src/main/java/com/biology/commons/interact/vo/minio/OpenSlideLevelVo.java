package com.biology.commons.interact.vo.minio;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-11 17:12
 * @desc:
 */
@Data
@ApiModel("切割层次图片数据")
public class OpenSlideLevelVo implements Serializable {

    private static final long serialVersionUID = 2842520847387787290L;

    @ApiModelProperty(value = "层次")
    private Integer leveCount;

    @ApiModelProperty(value = "长(x)")
    private Integer x;

    @ApiModelProperty(value = "高(y)")
    private Integer y;

    @ApiModelProperty(value = "文件外链url(有时间限制)")
    private String temporaryURL;

}
