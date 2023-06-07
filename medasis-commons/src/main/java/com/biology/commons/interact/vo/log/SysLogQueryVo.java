package com.biology.commons.interact.vo.log;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-31 12:33
 * @desc:
 */
@Data
@ApiModel("系统日志查询信息")
public class SysLogQueryVo implements Serializable {

    private static final long serialVersionUID = 773151271245266211L;

    @ApiModelProperty(value = "日志id")
    private String id;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "请求url")
    private String url;

    @ApiModelProperty(value = "操作类型 :新增、删除等等")
    private String type;

    @ApiModelProperty(value = "操作结果")
    private String result;

    @ApiModelProperty(value = "操作时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date createDate;
}
