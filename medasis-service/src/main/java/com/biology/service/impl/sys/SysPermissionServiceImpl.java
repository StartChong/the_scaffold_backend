package com.biology.service.impl.sys;

import com.biology.commons.constant.Constants;
import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.exception.SysException;
import com.biology.commons.interact.bo.sys.permission.*;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.sys.LoginUserVo;
import com.biology.commons.interact.vo.sys.permission.SysPermissionQueryVo;
import com.biology.commons.interact.vo.sys.permission.SysPermissionVo;
import com.biology.commons.utils.RedisUtil;
import com.biology.commons.utils.SecurityUtils;
import com.biology.dao.mongo.sys.*;
import com.biology.commons.base.service.BaseMongoServiceImpl;
import com.biology.service.sys.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:10
 * @desc:
 */
@Service
@Slf4j
public class SysPermissionServiceImpl extends BaseMongoServiceImpl<SysPermissionEntity> implements SysPermissionService {

    @Autowired
    private SysUserRoleRelService sysUserRoleRelService;

    @Autowired
    private SysRolePerRelService sysRolePerRelService;

    @Autowired
    private SysReqPerRelService sysReqPerRelService;

    @Autowired
    private SysRequestPathService sysReqPathService;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Set<SysPermissionEntity> getPermissionsByUserId(String userId) {
        Map<String, Set<SysPermissionEntity>> map = redisUtil.getCacheMap(Constants.USER_PERMISSION);
        Set<SysPermissionEntity> entities = null;
        if (Objects.nonNull(map)) {
            entities = map.get(userId);
            if (!CollectionUtils.isEmpty(entities)) {
                return entities;
            }
        }
        List<SysUserRoleRelationEntity> sysUserRoleRelationEntities = sysUserRoleRelService.findByCondition(SysUserRoleRelationEntity.builder().userId(userId).build());
        if (!CollectionUtils.isEmpty(sysUserRoleRelationEntities)) {
            List<String> roleIds = sysUserRoleRelationEntities.stream().map(SysUserRoleRelationEntity::getRoleId).collect(Collectors.toList());
            List<SysRolePermissionRelationEntity> relationEntities = sysRolePerRelService.getRolePerRelByRoleIds(roleIds);
            if (!CollectionUtils.isEmpty(relationEntities)) {
                List<String> permissionIds = relationEntities.stream().map(SysRolePermissionRelationEntity::getPermissionId).collect(Collectors.toList());
                entities = new HashSet<>(this.getPermissionsByPermissionIds(permissionIds));
            }
        }
        map.put(userId, entities);
        redisUtil.setCacheMap(Constants.USER_PERMISSION, map);
        return entities;
    }

    @Override
    public List<SysPermissionEntity> getPermissionsByPermissionIds(List<String> permissionIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(permissionIds));
        return this.find(query);
    }

    @Override
    public List<SysPermissionEntity> findListByPath(String path) {
        SysRequestPathEntity sysRequestEntity = sysReqPathService.findOne(SysRequestPathEntity.builder().reqUrl(path).build());
        if (Objects.nonNull(sysRequestEntity)) {
            List<SysRequestPathPermissionRelationEntity> sysRequestPathPermissionRelationEntities = sysReqPerRelService.findByCondition(SysRequestPathPermissionRelationEntity.builder().urlId(sysRequestEntity.getId()).build());
            if (!CollectionUtils.isEmpty(sysRequestPathPermissionRelationEntities)) {
                List<String> permissionIds = sysRequestPathPermissionRelationEntities.stream().map(SysRequestPathPermissionRelationEntity::getPermissionId).collect(Collectors.toList());
                return this.getPermissionsByPermissionIds(permissionIds);
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysPermissionBo sysPermissionBo) {
        LoginUserVo loginUser = SecurityUtils.getLoginUser();
        // 检查权限编码，权限名称
        SysException.throwException(checkCode(sysPermissionBo.getPermissionCode()), ResultCodeEnum.DATA_EXIST.code(), "权限编码已存在！");
        SysException.throwException(checkCode(sysPermissionBo.getPermissionName()), ResultCodeEnum.DATA_EXIST.code(), "权限名称已存在！");
        SysException.throwException(Constants.ALL_PERMISSION.equals(sysPermissionBo.getPermissionCode()), ResultCodeEnum.PARAMETER_EXCEPTION.code(), "该权限编码无法使用！");
        SysPermissionEntity entity = new SysPermissionEntity();
        BeanUtils.copyProperties(sysPermissionBo, entity);
        entity.setCreateBy(loginUser.getId());
        entity.setCreateTime(new Date());
        entity.setUpdateBy(loginUser.getId());
        entity.setUpdateTime(new Date());
        this.save(entity);
        // 保存权限与请求路径的联系
        if (!CollectionUtils.isEmpty(sysPermissionBo.getReqPathIds())) {
            sysReqPerRelService.saveReqPerByReqs(sysPermissionBo.getReqPathIds(), entity.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysPermissionEditBo sysPermissionEditBo) {
        LoginUserVo loginUser = SecurityUtils.getLoginUser();
        SysPermissionEntity entity = this.findById(sysPermissionEditBo.getId());
        SysException.throwException(Objects.isNull(entity), ResultCodeEnum.DATA_NOT_EXIST_ERROR.code(), "权限不存在！");
        SysException.throwException(Constants.ALL_PERMISSION.equals(sysPermissionEditBo.getPermissionCode()), ResultCodeEnum.PARAMETER_EXCEPTION.code(), "该权限编码无法使用！");
        if (!sysPermissionEditBo.getPermissionCode().equals(entity.getPermissionCode())) {
            SysException.throwException(checkCode(sysPermissionEditBo.getPermissionCode()), ResultCodeEnum.DATA_EXIST.code(), "权限编码已存在！");
        }
        if (!sysPermissionEditBo.getPermissionName().equals(entity.getPermissionName())) {
            SysException.throwException(checkCode(sysPermissionEditBo.getPermissionName()), ResultCodeEnum.DATA_EXIST.code(), "权限名称已存在！");
        }
        BeanUtils.copyProperties(sysPermissionEditBo, entity);
        entity.setUpdateBy(loginUser.getId());
        entity.setUpdateTime(new Date());
        this.updateById(entity.getId(), entity);
        if (!CollectionUtils.isEmpty(sysPermissionEditBo.getReqPathIds())) {
            sysReqPerRelService.saveReqPerByReqs(sysPermissionEditBo.getReqPathIds(), entity.getId());
        }
    }

    @Override
    public PageVo<SysPermissionQueryVo> queryByPage(SysPermissionQueryBo sysPermissionQueryBo) {
        SysPermissionEntity entity = new SysPermissionEntity();
        BeanUtils.copyProperties(sysPermissionQueryBo, entity);
        PageVo<SysPermissionEntity> entityPageVo = new PageVo<>();
        BeanUtils.copyProperties(sysPermissionQueryBo, entityPageVo);
        // 排序
        List<Sort.Order> orders = Lists.newArrayList();
        orders.add(Sort.Order.desc("createTime"));
        entityPageVo = this.findPageVoByCondition(entityPageVo, entity, true, orders);
        PageVo<SysPermissionQueryVo> pageVo = new PageVo<>();
        BeanUtils.copyProperties(entityPageVo, pageVo);
        List<SysPermissionQueryVo> queryVos = Lists.newArrayList();
        entityPageVo.getRows().forEach(e -> {
            SysPermissionQueryVo sysPermissionQueryVo = new SysPermissionQueryVo();
            BeanUtils.copyProperties(e, sysPermissionQueryVo);
            queryVos.add(sysPermissionQueryVo);
        });
        pageVo.setRows(queryVos);
        return pageVo;
    }

    @Override
    public SysPermissionVo queryDetail(SysPermissionIdBo sysPermissionIdBo) {
        SysPermissionEntity entity = this.findById(sysPermissionIdBo.getId());
        SysPermissionVo vo = new SysPermissionVo();
        if (Objects.nonNull(entity)) {
            BeanUtils.copyProperties(entity, vo);
            vo.setSysRequestPathVos(sysReqPathService.findByPermissionId(entity.getId()));
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(SysPermissionIdsBo sysPermissionIdsBo) {
        sysPermissionIdsBo.getIds().forEach(e -> {
            this.deleteById(e);
            // 删除权限与路径联系
            sysReqPerRelService.deleteByCondition(SysRequestPathPermissionRelationEntity.builder()
                    .permissionId(e)
                    .build());
            // 删除权限与角色联系
            sysRolePerRelService.deleteByCondition(SysRolePermissionRelationEntity.builder()
                    .permissionId(e)
                    .build());
        });
    }

    @Override
    public List<SysPermissionVo> findByRoleId(String roleId) {
        List<SysRolePermissionRelationEntity> relationEntities = sysRolePerRelService.findByCondition(SysRolePermissionRelationEntity.builder()
                .roleId(roleId)
                .build());
        List<SysPermissionVo> vos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(relationEntities)) {
            List<String> permissionIds = relationEntities.stream().map(SysRolePermissionRelationEntity::getPermissionId).collect(Collectors.toList());
            List<SysPermissionEntity> entities = this.findByIds(permissionIds);
            entities.forEach(entity -> {
                SysPermissionVo sysPermissionVo = new SysPermissionVo();
                BeanUtils.copyProperties(entity, sysPermissionVo);
                vos.add(sysPermissionVo);
            });
        }
        return vos;
    }

    // 检查权限编码
    private boolean checkCode(String code) {
        return Objects.nonNull(this.findOne(SysPermissionEntity.builder()
                .permissionCode(code)
                .build()));
    }

    // 检查权限名称
    private boolean checkName(String name) {
        return Objects.nonNull(this.findOne(SysPermissionEntity.builder()
                .permissionName(name)
                .build()));
    }

}
