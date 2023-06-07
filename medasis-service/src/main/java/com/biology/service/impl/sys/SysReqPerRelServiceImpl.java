package com.biology.service.impl.sys;

import com.biology.commons.constant.Constants;
import com.biology.commons.interact.vo.sys.LoginUserVo;
import com.biology.commons.utils.RedisUtil;
import com.biology.commons.utils.SecurityUtils;
import com.biology.dao.mongo.sys.SysRequestPathPermissionRelationEntity;
import com.biology.commons.base.service.BaseMongoServiceImpl;
import com.biology.service.sys.SysReqPerRelService;
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
 * @create: 2022-04-26 10:15
 * @desc:
 */
@Service
@Slf4j
public class SysReqPerRelServiceImpl extends BaseMongoServiceImpl<SysRequestPathPermissionRelationEntity> implements SysReqPerRelService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveReqPerByReqs(List<String> reqIds, String permissionId) {
        LoginUserVo loginUser = SecurityUtils.getLoginUser();
        if (Objects.nonNull(reqIds)) {
            this.deleteByCondition(SysRequestPathPermissionRelationEntity.builder()
                    .permissionId(permissionId)
                    .build());
            List<SysRequestPathPermissionRelationEntity> entities = Lists.newArrayList();
            reqIds.forEach(r -> {
                SysRequestPathPermissionRelationEntity entity = SysRequestPathPermissionRelationEntity.builder().urlId(r).permissionId(permissionId).build();
                entity.setCreateBy(loginUser.getId());
                entity.setCreateTime(new Date());
                entity.setUpdateBy(loginUser.getId());
                entity.setUpdateTime(new Date());
                entities.add(entity);
            });
            if (CollectionUtils.isNotEmpty(entities)) {
                this.insertCollection(entities);
            }
        }
        redisUtil.deleteObject(Constants.PATH_CACHE);
    }

}
