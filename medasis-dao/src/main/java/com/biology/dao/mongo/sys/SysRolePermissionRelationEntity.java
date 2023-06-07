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
 * @desc: 角色-权限关联关系表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document(value = "sys_role_permission_relation")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRolePermissionRelationEntity extends BaseEntity {

    @Id
    String id;

    @ApiModelProperty(value = "用户id")
    private String roleId;

    @ApiModelProperty(value = "权限id")
    private String permissionId;

}
