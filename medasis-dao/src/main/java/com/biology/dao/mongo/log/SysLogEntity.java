package com.biology.dao.mongo.log;

import com.biology.commons.base.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-31 10:35
 * @desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document(value = "sys_log")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLogEntity extends BaseEntity {

    @Id
    private String id;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "方法名")
    private String method;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "请求url")
    private String url;

    @ApiModelProperty(value = "操作类型 :新增、删除等等")
    private String type;

    @ApiModelProperty(value = "模块")
    private String model;

    @ApiModelProperty(value = "操作时间")
    private Date createDate;

    @ApiModelProperty(value = "操作结果")
    private String result;

    @ApiModelProperty(value = "描述")
    private String description;

}
