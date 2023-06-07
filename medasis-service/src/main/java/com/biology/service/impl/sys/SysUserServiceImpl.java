package com.biology.service.impl.sys;

import com.biology.commons.constant.Constants;
import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.exception.SysException;
import com.biology.commons.interact.bo.sys.UserPwdBo;
import com.biology.commons.interact.bo.sys.user.*;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.sys.LoginUserVo;
import com.biology.commons.interact.vo.sys.role.SysRoleVo;
import com.biology.commons.interact.vo.sys.user.SysUserQueryVo;
import com.biology.commons.interact.vo.sys.user.SysUserVo;
import com.biology.commons.utils.JwtUtils;
import com.biology.commons.utils.SecurityUtils;
import com.biology.dao.mongo.sys.SysRoleEntity;
import com.biology.dao.mongo.sys.SysUserEntity;
import com.biology.dao.mongo.sys.SysUserRoleRelationEntity;
import com.biology.commons.base.service.BaseMongoServiceImpl;
import com.biology.service.sys.SysRoleService;
import com.biology.service.sys.SysUserRoleRelService;
import com.biology.service.sys.SysUserService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
 * @create: 2022-04-26 10:05
 * @desc:
 */
@Service
@Slf4j
public class SysUserServiceImpl extends BaseMongoServiceImpl<SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserRoleRelService sysUserRoleRelService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserBo sysUserBo) {
        LoginUserVo loginUser = SecurityUtils.getLoginUser();
        // 账号，手机号，邮箱是否存在
        SysException.throwException(checkAccount(sysUserBo.getAccount()), ResultCodeEnum.DATA_EXIST.code(), "账号已存在！");
        SysException.throwException(checkPhone(sysUserBo.getPhone()), ResultCodeEnum.DATA_EXIST.code(), "手机号已存在！");
        SysException.throwException(checkEmail(sysUserBo.getEmail()), ResultCodeEnum.DATA_EXIST.code(), "邮箱已存在！");
        SysUserEntity user = new SysUserEntity();
        BeanUtils.copyProperties(sysUserBo, user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setEnabled(true);
        user.setCreateBy(loginUser.getId());
        user.setCreateTime(new Date());
        user.setUpdateBy(loginUser.getId());
        user.setUpdateTime(new Date());
        this.save(user);
        // 保存用户角色联系
        if (!CollectionUtils.isEmpty(sysUserBo.getRoleIds())) {
            sysUserRoleRelService.saveUserRoleByRoleIds(sysUserBo.getRoleIds(), user.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserEditBo sysUserEditBo) {
        LoginUserVo loginUser = SecurityUtils.getLoginUser();
        SysUserEntity user = this.findById(sysUserEditBo.getId());
        SysException.throwException(Objects.isNull(user), ResultCodeEnum.DATA_NOT_EXIST_ERROR.code(), "用户不存在！");
        if (!sysUserEditBo.getPhone().equals(user.getPhone())) {
            SysException.throwException(checkPhone(sysUserEditBo.getPhone()), ResultCodeEnum.DATA_EXIST.code(), "手机号已存在！");
        }
        if (!sysUserEditBo.getEmail().equals(user.getEmail())) {
            SysException.throwException(checkEmail(sysUserEditBo.getEmail()), ResultCodeEnum.DATA_EXIST.code(), "邮箱已存在！");
        }
        BeanUtils.copyProperties(sysUserEditBo, user);
        user.setUpdateBy(loginUser.getId());
        user.setUpdateTime(new Date());
        this.updateById(user.getId(), user);

        if (!CollectionUtils.isEmpty(sysUserEditBo.getRoleIds())) {
            sysUserRoleRelService.saveUserRoleByRoleIds(sysUserEditBo.getRoleIds(), user.getId());
        }
    }

    @Override
    public PageVo<SysUserQueryVo> queryByPage(SysUserQueryBo sysUserQueryBo) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        BeanUtils.copyProperties(sysUserQueryBo, sysUserEntity);
        PageVo<SysUserEntity> sysUserEntityPageVo = new PageVo<>();
        sysUserEntityPageVo.setCurrent(sysUserQueryBo.getCurrent());
        sysUserEntityPageVo.setSize(sysUserQueryBo.getSize());
        // 排序
        List<Sort.Order> orders = Lists.newArrayList();
        orders.add(Sort.Order.desc("createTime"));
        sysUserEntityPageVo = this.findPageVoByCondition(sysUserEntityPageVo, sysUserEntity, true, orders);
        PageVo<SysUserQueryVo> pageVo = new PageVo<>();
        BeanUtils.copyProperties(sysUserEntityPageVo, pageVo);
        List<SysUserQueryVo> sysUserQueryVos = Lists.newArrayList();
        sysUserEntityPageVo.getRows().forEach(e -> {
            SysUserQueryVo sysUserQueryVo = new SysUserQueryVo();
            BeanUtils.copyProperties(e, sysUserQueryVo);
            sysUserQueryVos.add(sysUserQueryVo);
        });
        pageVo.setRows(sysUserQueryVos);
        return pageVo;
    }

    @Override
    public SysUserVo queryDetail(SysUserIdBo sysUserIdBo) {
        SysUserEntity user = this.findById(sysUserIdBo.getId());
        SysUserVo sysUserVo = new SysUserVo();
        if (Objects.nonNull(user)) {
            BeanUtils.copyProperties(user, sysUserVo);
            sysUserVo.setSysRoleVos(sysRoleService.findByUserId(user.getId()));
        }
        return sysUserVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(SysUserIdsBo sysUserIdsBo) {
        sysUserIdsBo.getIds().forEach(e -> {
            // 删除的是否系统管理员
            List<SysRoleVo> sysRoleVos = sysRoleService.findByUserId(e);
            sysRoleVos = sysRoleVos.stream().filter(vo -> vo.getRoleCode().equals(Constants.ADMIN)).collect(Collectors.toList());
            SysException.throwException(!CollectionUtils.isEmpty(sysRoleVos), ResultCodeEnum.FAIL.code(), "无法删除系统管理员！");
            this.deleteById(e);
            // 删除用户与角色联系
            sysUserRoleRelService.deleteByCondition(SysUserRoleRelationEntity.builder().userId(e).build());
        });

    }

    @Override
    public void updatePwd(UserPwdBo userPwdBo) {
        LoginUserVo loginUser = SecurityUtils.getLoginUser();
        String password = loginUser.getPassword();
        SysException.throwException(!SecurityUtils.matchesPassword(userPwdBo.getOldPwd(), password), ResultCodeEnum.USER_CREDENTIALS_ERROR.code(), "修改密码失败，旧密码错误!");
        SysException.throwException(SecurityUtils.matchesPassword(userPwdBo.getNewPwd(), password), ResultCodeEnum.USER_CREDENTIALS_ERROR.code(), "新密码不能与旧密码相同!");
        SysUserEntity sysUserEntity = SysUserEntity.builder()
                .password(SecurityUtils.encryptPassword(userPwdBo.getNewPwd()))
                .build();
        sysUserEntity.setUpdateBy(loginUser.getId());
        sysUserEntity.setUpdateTime(new Date());
        this.updateById(loginUser.getId(), sysUserEntity);
        // 更新缓存用户密码
        loginUser.getSysUserVo().setPassword(SecurityUtils.encryptPassword(userPwdBo.getNewPwd()));
        jwtUtils.setLoginUser(loginUser);
    }

    /**
     * checkAccount
     *
     * @author: lichong
     * @description 检查账号是否存在
     * @date 2022/4/29
     */
    private boolean checkAccount(String account) {
        return Objects.nonNull(this.findOne(SysUserEntity.builder().account(account).build()));
    }

    /**
     * checkPhone
     *
     * @author: lichong
     * @description 检查手机号是否存在
     * @date 2022/4/29
     */
    private boolean checkPhone(String phone) {
        return Objects.nonNull(this.findOne(SysUserEntity.builder().phone(phone).build()));
    }

    /**
     * checkEmail
     *
     * @author: lichong
     * @description 检查邮箱是否存在
     * @date 2022/4/29
     */
    private boolean checkEmail(String email) {
        return Objects.nonNull(this.findOne(SysUserEntity.builder().email(email).build()));
    }

    // 初始化管理员
    @Bean
    @Transactional(rollbackFor = Exception.class)
    protected void initAdmin() {
        log.info("初始化管理员...");
        SysUserEntity sysUserEntity = this.findOne(SysUserEntity.builder().account(Constants.ADMIN).build());
        if (Objects.isNull(sysUserEntity)) {
            sysUserEntity = SysUserEntity.builder().enabled(true).account(Constants.ADMIN).password(SecurityUtils.encryptPassword(Constants.DEFAULT_PASSWORD)).userName(Constants.ADMIN).build();
            sysUserEntity.setCreateBy(Constants.ADMIN);
            sysUserEntity.setCreateTime(new Date());
            sysUserEntity.setUpdateBy(Constants.ADMIN);
            sysUserEntity.setUpdateTime(new Date());
            this.save(sysUserEntity);
            sysUserEntity = this.findOne(SysUserEntity.builder().account(Constants.ADMIN).build());
            // 添加角色
            SysRoleEntity sysRoleEntity = sysRoleService.findOne(SysRoleEntity.builder().roleCode(Constants.ADMIN).build());
            if (Objects.isNull(sysRoleEntity)) {
                sysRoleEntity = SysRoleEntity.builder().roleCode(Constants.ADMIN).roleName(Constants.ADMIN).roleDesc("系统管理员，拥有所有权").build();
                sysRoleEntity.setCreateBy(Constants.ADMIN);
                sysRoleEntity.setCreateTime(new Date());
                sysRoleEntity.setUpdateBy(Constants.ADMIN);
                sysRoleEntity.setUpdateTime(new Date());
                sysRoleService.save(sysRoleEntity);
                sysRoleEntity = sysRoleService.findOne(SysRoleEntity.builder().roleCode(Constants.ADMIN).build());
            }
            // 添加用户角色联系
            SysUserRoleRelationEntity roleRelation = sysUserRoleRelService.findOne(SysUserRoleRelationEntity.builder().userId(sysUserEntity.getId()).roleId(sysRoleEntity.getId()).build());
            if (Objects.isNull(roleRelation)) {
                roleRelation = SysUserRoleRelationEntity.builder().userId(sysUserEntity.getId()).roleId(sysRoleEntity.getId()).build();
                roleRelation.setCreateBy(Constants.ADMIN);
                roleRelation.setCreateTime(new Date());
                roleRelation.setUpdateBy(Constants.ADMIN);
                roleRelation.setUpdateTime(new Date());
                sysUserRoleRelService.save(roleRelation);
            }
        }
        log.info("初始化管理员完毕!");
    }


}
