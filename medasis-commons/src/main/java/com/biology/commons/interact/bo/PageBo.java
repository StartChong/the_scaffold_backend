package com.biology.commons.interact.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-11 13:40
 * @desc: 分页对象
 */
@Data
public class PageBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 每页显示条数，默认 10
     */
    @Max(value = 100, message = "显示条数size最大不能超过100条")
    @Min(value = 1, message = "显示条数size最小不能少于1条")
    @NotNull(message = "显示条数size不能为空！")
    @ApiModelProperty(value = "每页显示条数，默认 10", required = true)
    protected Long size = 10L;

    /**
     * 当前页
     */
    @Min(value = 1, message = "当前页current最小不能少于1页")
    @NotNull(message = "当前页current不能为空！")
    @ApiModelProperty(value = "当前页", required = true)
    protected Long current = 1L;


}
