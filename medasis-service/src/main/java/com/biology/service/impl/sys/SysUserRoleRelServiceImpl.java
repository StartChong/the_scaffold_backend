package com.biology.service.impl.sys;

import com.biology.commons.constant.Constants;
import com.biology.commons.interact.vo.sys.LoginUserVo;
import com.biology.commons.utils.RedisUtil;
import com.biology.commons.utils.SecurityUtils;
import com.biology.dao.mongo.sys.SysUserRoleRelationEntity;
import com.biology.commons.base.service.BaseMongoServiceImpl;
import com.biology.service.sys.SysUserRoleRelService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:18
 * @desc:
 */
@Service
@Slf4j
public class SysUserRoleRelServiceImpl extends BaseMongoServiceImpl<SysUserRoleRelationEntity> implements SysUserRoleRelService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserRoleByRoleIds(List<String> roleIds, String userId) {
        LoginUserVo loginUser = SecurityUtils.getLoginUser();
        if (Objects.nonNull(roleIds)) {
            // 删除原本的联系
            this.deleteByCondition(SysUserRoleRelationEntity.builder()
                    .userId(userId)
                    .build());
            List<SysUserRoleRelationEntity> sysRoleRelationList = Lists.newArrayList();
            roleIds.forEach(r -> {
                SysUserRoleRelationEntity sysUserRoleRelationEntity = SysUserRoleRelationEntity.builder().roleId(r).userId(userId).build();
                sysUserRoleRelationEntity.setCreateBy(loginUser.getId());
                sysUserRoleRelationEntity.setCreateTime(new Date());
                sysUserRoleRelationEntity.setUpdateBy(loginUser.getId());
                sysUserRoleRelationEntity.setUpdateTime(new Date());
                sysRoleRelationList.add(sysUserRoleRelationEntity);
            });
            if (CollectionUtils.isNotEmpty(sysRoleRelationList)) {
                this.insertCollection(sysRoleRelationList);
            }
        }
        redisUtil.deleteObject(Constants.USER_ROLE + userId);
        redisUtil.deleteObject(Constants.USER_PERMISSION);
    }

}
