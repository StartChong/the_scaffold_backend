package com.biology.service.impl.log;

import com.biology.commons.base.service.BaseMongoServiceImpl;
import com.biology.commons.interact.bo.log.SysLogIdBo;
import com.biology.commons.interact.bo.log.SysLogQueryBo;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.log.SysLogQueryVo;
import com.biology.commons.interact.vo.log.SysLogVo;
import com.biology.commons.interact.vo.sys.permission.SysPermissionVo;
import com.biology.dao.mongo.log.SysLogEntity;
import com.biology.service.log.SysLogService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-31 10:42
 * @desc:
 */
@Service
@Slf4j
public class SysLogServiceImpl extends BaseMongoServiceImpl<SysLogEntity> implements SysLogService {

    @Override
    public PageVo<SysLogQueryVo> queryByPage(SysLogQueryBo sysLogQueryBo) {
        SysLogEntity entity = new SysLogEntity();
        BeanUtils.copyProperties(sysLogQueryBo, entity);
        PageVo<SysLogEntity> entityPageVo = new PageVo<>();
        BeanUtils.copyProperties(sysLogQueryBo, entityPageVo);
        // 排序
        List<Sort.Order> orders = Lists.newArrayList();
        orders.add(Sort.Order.desc("createTime"));
        entityPageVo = this.findPageVoByCondition(entityPageVo, entity, true, orders);
        PageVo<SysLogQueryVo> pageVo = new PageVo<>();
        BeanUtils.copyProperties(entityPageVo, pageVo);
        List<SysLogQueryVo> queryVos = Lists.newArrayList();
        entityPageVo.getRows().forEach(e -> {
            SysLogQueryVo sysLogQueryVo = new SysLogQueryVo();
            BeanUtils.copyProperties(e, sysLogQueryVo);
            queryVos.add(sysLogQueryVo);
        });
        pageVo.setRows(queryVos);
        return pageVo;
    }

    @Override
    public SysLogVo queryDetail(SysLogIdBo sysLogIdBo) {
        SysLogEntity entity = this.findById(sysLogIdBo.getId());
        SysLogVo vo = new SysLogVo();
        if (Objects.nonNull(entity)) {
            BeanUtils.copyProperties(entity, vo);
        }
        return vo;
    }


}
