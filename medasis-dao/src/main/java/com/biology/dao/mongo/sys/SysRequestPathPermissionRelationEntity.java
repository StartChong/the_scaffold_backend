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
 * @desc: 路径权限关联表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document(value = "sys_request_path_permission_relation")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRequestPathPermissionRelationEntity extends BaseEntity {

    @Id
    String id;

    @ApiModelProperty(value = "请求路径id")
    private String urlId;

    @ApiModelProperty(value = "权限id")
    private String permissionId;

}
