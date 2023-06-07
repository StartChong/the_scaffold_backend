package com.biology.service.sys;

import com.biology.dao.mongo.sys.SysRequestPathPermissionRelationEntity;
import com.biology.commons.base.service.IBaseMongoService;

import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:14
 * @desc:
 */
public interface SysReqPerRelService extends IBaseMongoService<SysRequestPathPermissionRelationEntity> {

    /**
     * saveReqPerByReqs
     *
     * @param reqIds
     * @param permissionId
     * @return
     * @author: lichong
     * @description 根据请求路径id和权限id保存关联
     * @date 2022/4/28
     */
    void saveReqPerByReqs(List<String> reqIds, String permissionId);

}
