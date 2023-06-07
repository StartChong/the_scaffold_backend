package com.biology.commons.interact.bo.log;

import com.biology.commons.interact.bo.PageBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-31 12:42
 * @desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLogQueryBo extends PageBo implements Serializable {

    private static final long serialVersionUID = 8908134768927079912L;

    @ApiModelProperty(value = "请求url")
    private String url;

    @ApiModelProperty(value = "操作类型 :新增、删除等等")
    private String type;

    @ApiModelProperty(value = "描述")
    private String description;

}
