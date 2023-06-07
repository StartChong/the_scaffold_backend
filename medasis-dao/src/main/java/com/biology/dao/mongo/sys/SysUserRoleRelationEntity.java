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
 * @desc: 用户角色关联关系表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document(value = "sys_user_role_relation")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRoleRelationEntity extends BaseEntity {

    @Id
    String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "角色id")
    private String roleId;

}
