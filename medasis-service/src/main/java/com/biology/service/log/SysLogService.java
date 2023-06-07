package com.biology.service.log;

import com.biology.commons.base.service.IBaseMongoService;
import com.biology.commons.interact.bo.log.SysLogIdBo;
import com.biology.commons.interact.bo.log.SysLogQueryBo;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.log.SysLogQueryVo;
import com.biology.commons.interact.vo.log.SysLogVo;
import com.biology.dao.mongo.log.SysLogEntity;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-31 10:41
 * @desc:
 */
public interface SysLogService extends IBaseMongoService<SysLogEntity> {


    /**
     * queryByPage
     *
     * @param sysLogQueryBo
     * @return
     * @author: lichong
     * @description
     * @date 2022/5/31
     */
    PageVo<SysLogQueryVo> queryByPage(SysLogQueryBo sysLogQueryBo);

    /**
     * queryDetail
     *
     * @param sysLogIdBo
     * @return
     * @author: lichong
     * @description
     * @date 2022/5/31
     */
    SysLogVo queryDetail(SysLogIdBo sysLogIdBo);

}
