package com.biology.service.impl.sys;

import com.biology.commons.constant.Constants;
import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.exception.SysException;
import com.biology.commons.interact.bo.sys.role.*;
import com.biology.commons.interact.bo.sys.user.SysUserIdBo;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.sys.LoginUserVo;
import com.biology.commons.interact.vo.sys.role.SysRoleQueryVo;
import com.biology.commons.interact.vo.sys.role.SysRoleVo;
import com.biology.commons.utils.JwtUtils;
import com.biology.commons.utils.RedisUtil;
import com.biology.commons.utils.SecurityUtils;
import com.biology.commons.utils.StringUtils;
import com.biology.dao.mongo.sys.SysPermissionEntity;
import com.biology.dao.mongo.sys.SysRoleEntity;
import com.biology.dao.mongo.sys.SysRolePermissionRelationEntity;
import com.biology.dao.mongo.sys.SysUserRoleRelationEntity;
import com.biology.commons.base.service.BaseMongoServiceImpl;
import com.biology.service.sys.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 10:05
 * @desc:
 */
@Service
@Slf4j
public class SysRoleServiceImpl extends BaseMongoServiceImpl<SysRoleEntity> implements SysRoleService {

    @Autowired
    private SysRolePerRelService sysRolePerRelService;

    @Autowired
    private SysUserRoleRelService sysUserRoleRelService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    @Lazy
    private SysUserService sysUserService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleBo sysRoleBo) {
        LoginUserVo loginUser = SecurityUtils.getLoginUser();
        // 检查角色编码、名称
        SysException.throwException(checkRoleCode(sysRoleBo.getRoleCode()), ResultCodeEnum.DATA_EXIST.code(), "角色编码已存在！");
        SysException.throwException(checkRoleName(sysRoleBo.getRoleName()), ResultCodeEnum.DATA_EXIST.code(), "角色名称已存在！");
        SysRoleEntity sysRoleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(sysRoleBo, sysRoleEntity);
        sysRoleEntity.setCreateBy(loginUser.getId());
        sysRoleEntity.setCreateTime(new Date());
        sysRoleEntity.setUpdateBy(loginUser.getId());
        sysRoleEntity.setUpdateTime(new Date());
        this.save(sysRoleEntity);
        // 保存角色与权限的联系
        if (!CollectionUtils.isEmpty(sysRoleBo.getPermissionIds())) {
            sysRolePerRelService.saveRolePerByPerIds(sysRoleBo.getPermissionIds(), sysRoleEntity.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEditBo sysRoleEditBo) {
        LoginUserVo loginUser = SecurityUtils.getLoginUser();
        SysRoleEntity sysRoleEntity = this.findById(sysRoleEditBo.getId());
        SysException.throwException(Objects.isNull(sysRoleEntity), ResultCodeEnum.DATA_NOT_EXIST_ERROR.code(), "角色不存在！");
        if (!loginUser.getSysUserVo().isAdmin()) {
            SysException.throwException(sysRoleEntity.getRoleCode().equals(Constants.ADMIN), ResultCodeEnum.ROLE_RESTRICTED.code(), ResultCodeEnum.ROLE_RESTRICTED.message());
        }
        if (!sysRoleEditBo.getRoleCode().equals(sysRoleEntity.getRoleCode())) {
            SysException.throwException(checkRoleCode(sysRoleEditBo.getRoleCode()), ResultCodeEnum.DATA_EXIST.code(), "角色编码已存在！");
        }
        if (!sysRoleEditBo.getRoleName().equals(sysRoleEntity.getRoleName())) {
            SysException.throwException(checkRoleName(sysRoleEditBo.getRoleName()), ResultCodeEnum.DATA_EXIST.code(), "角色名称已存在！");
        }
        BeanUtils.copyProperties(sysRoleEditBo, sysRoleEntity);
        sysRoleEntity.setUpdateBy(loginUser.getId());
        sysRoleEntity.setUpdateTime(new Date());
        this.updateById(sysRoleEntity.getId(), sysRoleEntity);

        if (!CollectionUtils.isEmpty(sysRoleEditBo.getPermissionIds())) {
            sysRolePerRelService.saveRolePerByPerIds(sysRoleEditBo.getPermissionIds(), sysRoleEntity.getId());
        }

        // 更新缓存用户权限
        if (StringUtils.isNotNull(loginUser.getSysUserVo()) && !loginUser.getSysUserVo().isAdmin()) {
            List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
            Set<SysPermissionEntity> sysPermissionEntities = sysPermissionService.getPermissionsByUserId(loginUser.getId());
            if (Objects.nonNull(sysPermissionEntities)) {
                sysPermissionEntities.forEach(permission -> {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPermissionCode());
                    grantedAuthorities.add(grantedAuthority);
                });
            }
            loginUser.setGrantedAuthorities(grantedAuthorities);
            SysUserIdBo sysUserIdBo = new SysUserIdBo();
            sysUserIdBo.setId(loginUser.getId());
            loginUser.setSysUserVo(sysUserService.queryDetail(sysUserIdBo));
            jwtUtils.setLoginUser(loginUser);
        }

    }

    @Override
    public PageVo<SysRoleQueryVo> queryByPage(SysRoleQueryBo sysRoleQueryBo) {
        SysRoleEntity sysRoleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(sysRoleQueryBo, sysRoleEntity);
        PageVo<SysRoleEntity> sysRoleEntityPageVo = new PageVo<>();
        BeanUtils.copyProperties(sysRoleQueryBo, sysRoleEntityPageVo);
        // 排序
        List<Sort.Order> orders = Lists.newArrayList();
        orders.add(Sort.Order.desc("createTime"));
        sysRoleEntityPageVo = this.findPageVoByCondition(sysRoleEntityPageVo, sysRoleEntity, true, orders);
        PageVo<SysRoleQueryVo> pageVo = new PageVo<>();
        List<SysRoleQueryVo> sysRoleQueryVos = Lists.newArrayList();
        BeanUtils.copyProperties(sysRoleEntityPageVo, pageVo);
        sysRoleEntityPageVo.getRows().forEach(e -> {
            SysRoleQueryVo sysRoleQueryVo = new SysRoleQueryVo();
            BeanUtils.copyProperties(e, sysRoleQueryVo);
            sysRoleQueryVos.add(sysRoleQueryVo);
        });
        pageVo.setRows(sysRoleQueryVos);
        return pageVo;
    }

    @Override
    public SysRoleVo queryDetail(SysRoleIdBo sysRoleIdBo) {
        SysRoleEntity sysRoleEntity = this.findById(sysRoleIdBo.getId());
        SysRoleVo sysRoleVo = new SysRoleVo();
        if (Objects.nonNull(sysRoleEntity)) {
            BeanUtils.copyProperties(sysRoleEntity, sysRoleVo);
            sysRoleVo.setSysPermissionVos(sysPermissionService.findByRoleId(sysRoleVo.getId()));
        }
        return sysRoleVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(SysRoleIdsBo sysRoleIdsBo) {
        sysRoleIdsBo.getIds().forEach(e -> {
            SysRoleEntity sysRoleEntity = this.findById(e);
            SysException.throwException(Objects.isNull(sysRoleEntity), ResultCodeEnum.DATA_NOT_EXIST_ERROR.code(), "id为：" + e + " 数据不存在！");
            SysException.throwException(sysRoleEntity.getRoleCode().equals(Constants.ADMIN), ResultCodeEnum.ROLE_RESTRICTED.code(), "管理员角色无法删除！");
            this.deleteById(e);
            // 删除角色与权限关系
            sysRolePerRelService.deleteByCondition(SysRolePermissionRelationEntity.builder().roleId(e).build());
            // 删除角色与用户关系
            sysUserRoleRelService.deleteByCondition(SysUserRoleRelationEntity.builder().roleId(e).build());
        });
    }

    @Override
    public List<SysRoleVo> findByUserId(String userId) {

        List<SysRoleVo> sysRoleVoList = redisUtil.getCacheList(Constants.USER_ROLE + userId);
        if (!CollectionUtils.isEmpty(sysRoleVoList)) {
            return sysRoleVoList;
        }
        List<SysUserRoleRelationEntity> sysRoleEntityList = sysUserRoleRelService.findByCondition(SysUserRoleRelationEntity.builder().userId(userId).build());
        if (!CollectionUtils.isEmpty(sysRoleEntityList)) {
            List<String> roleIdList = sysRoleEntityList.stream().map(SysUserRoleRelationEntity::getRoleId).collect(Collectors.toList());
            List<SysRoleEntity> sysRoleEntities = this.findByIds(roleIdList);
            sysRoleEntities.forEach(sysRoleEntity -> {
                SysRoleVo sysRoleVo = new SysRoleVo();
                BeanUtils.copyProperties(sysRoleEntity, sysRoleVo);
                sysRoleVoList.add(sysRoleVo);
            });
        }
        redisUtil.setCacheList(Constants.USER_ROLE + userId, sysRoleVoList);
        return sysRoleVoList;
    }

    // 检查角色编码
    private boolean checkRoleCode(String roleCode) {
        return Objects.nonNull(this.findOne(SysRoleEntity.builder().roleCode(roleCode).build()));
    }

    // 检查角色名称
    private boolean checkRoleName(String roleName) {
        return Objects.nonNull(this.findOne(SysRoleEntity.builder().roleName(roleName).build()));
    }
}
