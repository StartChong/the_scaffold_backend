package com.biology.service.sys;

import com.biology.commons.interact.bo.sys.UserPwdBo;
import com.biology.commons.interact.bo.sys.user.*;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.sys.user.SysUserQueryVo;
import com.biology.commons.interact.vo.sys.user.SysUserVo;
import com.biology.dao.mongo.sys.SysUserEntity;
import com.biology.commons.base.service.IBaseMongoService;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:04
 * @desc:
 */
public interface SysUserService extends IBaseMongoService<SysUserEntity> {

    /**
     * save
     *
     * @author: lichong
     * @param: sysUserBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    void save(SysUserBo sysUserBo);

    /**
     * update
     *
     * @author: lichong
     * @param: sysUserEditBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    void update(SysUserEditBo sysUserEditBo);

    /**
     * queryByPage
     *
     * @author: lichong
     * @param: sysUserQueryBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    PageVo<SysUserQueryVo> queryByPage(SysUserQueryBo sysUserQueryBo);

    /**
     * queryDetail
     *
     * @author: lichong
     * @param: sysUserIdBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    SysUserVo queryDetail(SysUserIdBo sysUserIdBo);

    /**
     * delete
     *
     * @author: lichong
     * @param: sysUserIdsBo
     * @return:
     * @description:
     * @date: 2022/4/27
     */
    void delete(SysUserIdsBo sysUserIdsBo);

    /**
     * updatePwd
     *
     * @param userPwdBo
     * @author: lichong
     * @description 修改密码
     * @date 2022/5/5
     */
    void updatePwd(UserPwdBo userPwdBo);
}
