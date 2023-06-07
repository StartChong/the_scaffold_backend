package com.biology.service.sys;

import com.biology.commons.interact.bo.sys.role.*;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.sys.role.SysRoleQueryVo;
import com.biology.commons.interact.vo.sys.role.SysRoleVo;
import com.biology.dao.mongo.sys.SysRoleEntity;
import com.biology.commons.base.service.IBaseMongoService;

import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:04
 * @desc:
 */
public interface SysRoleService extends IBaseMongoService<SysRoleEntity> {

    /**
     * save
     *
     * @author: lichong
     * @param: sysRoleBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    void save(SysRoleBo sysRoleBo);

    /**
     * update
     *
     * @author: lichong
     * @param: sysRoleEditBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    void update(SysRoleEditBo sysRoleEditBo);

    /**
     * queryByPage
     *
     * @author: lichong
     * @param: sysRoleQueryBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    PageVo<SysRoleQueryVo> queryByPage(SysRoleQueryBo sysRoleQueryBo);

    /**
     * queryDetail
     *
     * @author: lichong
     * @param: sysRoleIdBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    SysRoleVo queryDetail(SysRoleIdBo sysRoleIdBo);

    /**
     * delete
     *
     * @author: lichong
     * @param: sysRoleIdsBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    void delete(SysRoleIdsBo sysRoleIdsBo);

    /**
     * findByUserId
     *
     * @param userId
     * @return
     * @author: lichong
     * @description
     * @date 2022/4/28
     */
    List<SysRoleVo> findByUserId(String userId);

}
