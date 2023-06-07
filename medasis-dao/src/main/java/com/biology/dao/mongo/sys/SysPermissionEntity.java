package com.biology.dao.mongo.sys;

import com.biology.commons.base.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-25 14:27
 * @desc: 权限表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document(value = "sys_permission")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysPermissionEntity extends BaseEntity {

    @Id
    String id;

    @ApiModelProperty(value = "权限编码")
    private String permissionCode;

    @ApiModelProperty(value = "权限名称")
    private String permissionName;

}
