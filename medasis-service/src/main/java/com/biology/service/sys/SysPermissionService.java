package com.biology.service.sys;

import com.biology.commons.interact.bo.sys.permission.*;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.sys.permission.SysPermissionQueryVo;
import com.biology.commons.interact.vo.sys.permission.SysPermissionVo;
import com.biology.dao.mongo.sys.SysPermissionEntity;
import com.biology.commons.base.service.IBaseMongoService;

import java.util.List;
import java.util.Set;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:09
 * @desc:
 */
public interface SysPermissionService extends IBaseMongoService<SysPermissionEntity> {

    /**
     * getPermissionsByUserId
     *
     * @author: lichong
     * @param: userId
     * @return: SysPermissionEntity
     * @description: 根据用户id获取权限
     * @date: 2022/4/26
     */
    Set<SysPermissionEntity> getPermissionsByUserId(String userId);

    /**
     * getPermissionsByPermissionIds
     *
     * @author: lichong
     * @param: permissionIds
     * @return: SysPermissionEntity
     * @description: 根据权限id集合获取权限信息
     * @date: 2022/4/26
     */
    List<SysPermissionEntity> getPermissionsByPermissionIds(List<String> permissionIds);

    /**
     * findListByPath
     *
     * @author: lichong
     * @param: path
     * @return: SysPermissionEntity
     * @description: 根据路径查找权限
     * @date: 2022/4/26
     */
    List<SysPermissionEntity> findListByPath(String path);

    /**
     * save
     *
     * @author: lichong
     * @param: sysPermissionBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    void save(SysPermissionBo sysPermissionBo);

    /**
     * update
     *
     * @author: lichong
     * @param: sysPermissionEditBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    void update(SysPermissionEditBo sysPermissionEditBo);

    /**
     * queryByPage
     *
     * @author: lichong
     * @param: sysPermissionQueryBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    PageVo<SysPermissionQueryVo> queryByPage(SysPermissionQueryBo sysPermissionQueryBo);

    /**
     * queryDetail
     *
     * @author: lichong
     * @param: sysPermissionIdBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    SysPermissionVo queryDetail(SysPermissionIdBo sysPermissionIdBo);

    /**
     * delete
     *
     * @author: lichong
     * @param: sysPermissionIdsBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    void delete(SysPermissionIdsBo sysPermissionIdsBo);

    /**
     * findByRoleId
     *
     * @param roleId
     * @author: lichong
     * @date 2022/4/28
     */
    List<SysPermissionVo> findByRoleId(String roleId);

}
