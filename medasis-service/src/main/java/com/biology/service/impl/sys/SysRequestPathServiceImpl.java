package com.biology.service.impl.sys;

import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.exception.SysException;
import com.biology.commons.interact.bo.sys.path.*;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.sys.LoginUserVo;
import com.biology.commons.interact.vo.sys.path.SysRequestPathQueryVo;
import com.biology.commons.interact.vo.sys.path.SysRequestPathVo;
import com.biology.commons.utils.SecurityUtils;
import com.biology.commons.utils.bean.BeanUtils;
import com.biology.dao.mongo.sys.SysRequestPathEntity;
import com.biology.dao.mongo.sys.SysRequestPathPermissionRelationEntity;
import com.biology.commons.base.service.BaseMongoServiceImpl;
import com.biology.service.sys.SysReqPerRelService;
import com.biology.service.sys.SysRequestPathService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:12
 * @desc:
 */
@Service
@Slf4j
public class SysRequestPathServiceImpl extends BaseMongoServiceImpl<SysRequestPathEntity> implements SysRequestPathService {

    @Autowired
    private SysReqPerRelService sysReqPerRelService;

    @Override
    public void save(SysRequestPathBo sysRequestPathBo) {
        LoginUserVo loginUser = SecurityUtils.getLoginUser();
        // 检查请求路径
        SysException.throwException(checkReqPath(sysRequestPathBo.getReqUrl()), ResultCodeEnum.DATA_EXIST.code(), "请求路径已存在！");
        SysRequestPathEntity entity = new SysRequestPathEntity();
        BeanUtils.copyProperties(sysRequestPathBo, entity);
        entity.setCreateBy(loginUser.getId());
        entity.setCreateTime(new Date());
        entity.setUpdateBy(loginUser.getId());
        entity.setUpdateTime(new Date());
        this.save(entity);
    }

    @Override
    public void update(SysRequestPathEditBo sysRequestPathEditBo) {
        LoginUserVo loginUser = SecurityUtils.getLoginUser();
        SysRequestPathEntity entity = this.findById(sysRequestPathEditBo.getId());
        SysException.throwException(Objects.isNull(entity), ResultCodeEnum.DATA_NOT_EXIST_ERROR.code(), "路径不存在！");
        if (!sysRequestPathEditBo.getReqUrl().equals(entity.getReqUrl())) {
            SysException.throwException(checkReqPath(sysRequestPathEditBo.getReqUrl()), ResultCodeEnum.DATA_EXIST.code(), "请求路径已存在！");
        }
        BeanUtils.copyProperties(sysRequestPathEditBo, entity);
        entity.setUpdateBy(loginUser.getId());
        entity.setUpdateTime(new Date());
        this.updateById(entity.getId(), entity);

    }

    @Override
    public PageVo<SysRequestPathQueryVo> queryByPage(SysRequestPathQueryBo sysRequestPathQueryBo) {
        SysRequestPathEntity entity = new SysRequestPathEntity();
        BeanUtils.copyProperties(sysRequestPathQueryBo, entity);
        PageVo<SysRequestPathEntity> entityPageVo = new PageVo<>();
        BeanUtils.copyProperties(sysRequestPathQueryBo, entityPageVo);
        // 排序
        List<Sort.Order> orders = Lists.newArrayList();
        orders.add(Sort.Order.desc("createTime"));
        entityPageVo = this.findPageVoByCondition(entityPageVo, entity, true, orders);
        PageVo<SysRequestPathQueryVo> pageVo = new PageVo<>();
        BeanUtils.copyProperties(entityPageVo, pageVo);
        List<SysRequestPathQueryVo> queryVos = Lists.newArrayList();
        entityPageVo.getRows().forEach(e -> {
            SysRequestPathQueryVo queryVo = new SysRequestPathQueryVo();
            BeanUtils.copyProperties(e, queryVo);
            queryVos.add(queryVo);
        });
        pageVo.setRows(queryVos);
        return pageVo;
    }

    @Override
    public SysRequestPathVo queryDetail(SysRequestPathIdBo sysRequestPathIdBo) {
        SysRequestPathEntity entity = this.findById(sysRequestPathIdBo.getId());
        SysRequestPathVo vo = new SysRequestPathVo();
        if (Objects.nonNull(entity)) {
            BeanUtils.copyProperties(entity, vo);
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(SysRequestPathIdsBo sysRequestPathIdsBo) {
        sysRequestPathIdsBo.getIds().forEach(e -> {
            this.deleteById(e);
            // 删除权限与路径的联系
            sysReqPerRelService.deleteByCondition(SysRequestPathPermissionRelationEntity.builder()
                    .urlId(e)
                    .build());
        });
    }

    @Override
    public List<SysRequestPathVo> findByPermissionId(String permissionId) {
        List<SysRequestPathPermissionRelationEntity> entities = sysReqPerRelService.findByCondition(SysRequestPathPermissionRelationEntity.builder()
                .permissionId(permissionId)
                .build());
        List<SysRequestPathVo> sysRequestPathVoList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(entities)) {
            List<String> reqPathIds = entities.stream().map(SysRequestPathPermissionRelationEntity::getUrlId).collect(Collectors.toList());
            List<SysRequestPathEntity> reqPathEntities = this.findByIds(reqPathIds);
            reqPathEntities.forEach(e -> {
                SysRequestPathVo sysRequestPathVo = new SysRequestPathVo();
                BeanUtils.copyProperties(e, sysRequestPathVo);
                sysRequestPathVoList.add(sysRequestPathVo);
            });
        }
        return sysRequestPathVoList;
    }

    // 检查请求路径
    private boolean checkReqPath(String reqPath) {
        return Objects.nonNull(this.findOne(SysRequestPathEntity.builder()
                .reqUrl(reqPath)
                .build()));
    }
}
