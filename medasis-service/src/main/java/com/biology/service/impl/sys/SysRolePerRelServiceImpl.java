package com.biology.service.impl.sys;

import com.biology.commons.constant.Constants;
import com.biology.commons.interact.vo.sys.LoginUserVo;
import com.biology.commons.utils.RedisUtil;
import com.biology.commons.utils.SecurityUtils;
import com.biology.dao.mongo.sys.SysRolePermissionRelationEntity;
import com.biology.commons.base.service.BaseMongoServiceImpl;
import com.biology.service.sys.SysRolePerRelService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:17
 * @desc:
 */
@Service
@Slf4j
public class SysRolePerRelServiceImpl extends BaseMongoServiceImpl<SysRolePermissionRelationEntity> implements SysRolePerRelService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<SysRolePermissionRelationEntity> getRolePerRelByRoleIds(List<String> roleIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("roleId").in(roleIds));
        return this.find(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRolePerByPerIds(List<String> perIds, String roleId) {
        LoginUserVo loginUser = SecurityUtils.getLoginUser();
        if (Objects.nonNull(perIds)) {
            this.deleteByCondition(SysRolePermissionRelationEntity.builder()
                    .roleId(roleId)
                    .build());
            List<SysRolePermissionRelationEntity> sysRolePermissionRelationEntities = Lists.newArrayList();
            perIds.forEach(r -> {
                SysRolePermissionRelationEntity sysRolePermissionRelationEntity = SysRolePermissionRelationEntity.builder().permissionId(r).roleId(roleId).build();
                sysRolePermissionRelationEntity.setCreateBy(loginUser.getId());
                sysRolePermissionRelationEntity.setCreateTime(new Date());
                sysRolePermissionRelationEntity.setUpdateBy(loginUser.getId());
                sysRolePermissionRelationEntity.setUpdateTime(new Date());
                sysRolePermissionRelationEntities.add(sysRolePermissionRelationEntity);
            });
            if (CollectionUtils.isNotEmpty(sysRolePermissionRelationEntities)) {
                this.insertCollection(sysRolePermissionRelationEntities);
            }
        }
        redisUtil.deleteObject(Constants.USER_PERMISSION);
    }
}
