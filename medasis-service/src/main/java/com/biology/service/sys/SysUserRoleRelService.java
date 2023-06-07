package com.biology.service.sys;

import com.biology.dao.mongo.sys.SysUserRoleRelationEntity;
import com.biology.commons.base.service.IBaseMongoService;

import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:18
 * @desc:
 */
public interface SysUserRoleRelService extends IBaseMongoService<SysUserRoleRelationEntity> {

    /**
     * saveUserRoleByRoleIds
     *
     * @param userId
     * @author: lichong
     * @param: roleIds
     * @return:
     * @description: 根据用户编号和角色编号保存关联信息
     * @date: 2022/4/28
     */
    void saveUserRoleByRoleIds(List<String> roleIds, String userId);

}
