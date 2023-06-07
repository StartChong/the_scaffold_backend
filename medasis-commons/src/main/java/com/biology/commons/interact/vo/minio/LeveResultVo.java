package com.biology.commons.interact.vo.minio;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-11 19:48
 * @desc:
 */
@Data
@ApiModel("切割层次图片结果")
public class LeveResultVo implements Serializable {

    private static final long serialVersionUID = 2927602738557658872L;

    @ApiModelProperty(value = "层次")
    private Integer leveCount;

    @ApiModelProperty(value = "层次")
    private List<OpenSlideLevelVo> openSlideLevelVos;

}
