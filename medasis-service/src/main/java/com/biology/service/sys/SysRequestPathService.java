package com.biology.service.sys;

import com.biology.commons.interact.bo.sys.path.*;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.sys.path.SysRequestPathQueryVo;
import com.biology.commons.interact.vo.sys.path.SysRequestPathVo;
import com.biology.dao.mongo.sys.SysRequestPathEntity;
import com.biology.commons.base.service.IBaseMongoService;

import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:11
 * @desc:
 */
public interface SysRequestPathService extends IBaseMongoService<SysRequestPathEntity> {

    /**
     * save
     *
     * @author: lichong
     * @param: sysRequestPathBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    void save(SysRequestPathBo sysRequestPathBo);

    /**
     * update
     *
     * @author: lichong
     * @param: sysRequestPathEditBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    void update(SysRequestPathEditBo sysRequestPathEditBo);

    /**
     * queryByPage
     *
     * @author: lichong
     * @param: queryByPage
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    PageVo<SysRequestPathQueryVo> queryByPage(SysRequestPathQueryBo sysRequestPathQueryBo);

    /**
     * queryDetail
     *
     * @author: lichong
     * @param: sysRequestPathIdBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    SysRequestPathVo queryDetail(SysRequestPathIdBo sysRequestPathIdBo);

    /**
     * delete
     *
     * @author: lichong
     * @param: sysRequestPathIdsBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    void delete(SysRequestPathIdsBo sysRequestPathIdsBo);

    /**
     * findByPermissionId
     *
     * @param permissionId
     * @author: lichong
     * @date 2022/4/28
     */
    List<SysRequestPathVo> findByPermissionId(String permissionId);

}
