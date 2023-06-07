package com.biology.service.sys;

import com.biology.dao.mongo.sys.SysRolePermissionRelationEntity;
import com.biology.commons.base.service.IBaseMongoService;

import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:16
 * @desc:
 */
public interface SysRolePerRelService extends IBaseMongoService<SysRolePermissionRelationEntity> {

    /**
     * getRolePerRelByRoleIds
     *
     * @author: lichong
     * @param: roleIds
     * @return: SysRolePermissionRelationEntity
     * @description: 根据角色id获取角色权限
     * @date: 2022/4/26
     */
    List<SysRolePermissionRelationEntity> getRolePerRelByRoleIds(List<String> roleIds);

    /**
     * saveRolePerByPerIds
     *
     * @param: roleId
     * @author: lichong
     * @param: perIds
     * @return:
     * @description:
     * @date: 2022/4/28
     */
    void saveRolePerByPerIds(List<String> perIds, String roleId);

}
