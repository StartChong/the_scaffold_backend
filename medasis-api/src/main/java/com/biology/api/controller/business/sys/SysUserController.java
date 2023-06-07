package com.biology.api.controller.business.sys;

import com.biology.api.annotation.SysLogAnnotation;
import com.biology.commons.base.Result;
import com.biology.commons.interact.bo.sys.UserPwdBo;
import com.biology.commons.interact.bo.sys.user.*;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.sys.user.SysUserQueryVo;
import com.biology.commons.interact.vo.sys.user.SysUserVo;
import com.biology.service.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-28 11:08
 * @desc:
 */
@RestController
@Api(tags = "系统用户管理")
@RequestMapping("/api/business/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户", notes = "新增用户", response = Result.class)
    public Result<?> add(@RequestBody @Valid SysUserBo sysUserBo) {

        sysUserService.save(sysUserBo);
        return Result.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户", notes = "修改用户", response = Result.class)
    @SysLogAnnotation(module = "系统用户管理", type = "修改", desc = "修改用户")
    public Result<?> update(@RequestBody @Valid SysUserEditBo sysUserEditBo) {

        sysUserService.update(sysUserEditBo);
        return Result.success();
    }

    @RequestMapping(value = "/list/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询用户列表", notes = "查询用户列表")
    public Result<PageVo<SysUserQueryVo>> query(@RequestBody @Valid SysUserQueryBo sysUserQueryBo) {

        return Result.success(sysUserService.queryByPage(sysUserQueryBo));
    }

    @RequestMapping(value = "/detail/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询用户详情", notes = "查询用户详情")
    public Result<SysUserVo> detail(@RequestBody @Valid SysUserIdBo sysUserIdBo) {

        return Result.success(sysUserService.queryDetail(sysUserIdBo));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @SysLogAnnotation(module = "系统用户管理", type = "删除", desc = "删除用户")
    public Result<SysUserVo> delete(@RequestBody @Valid SysUserIdsBo sysUserIdsBo) {

        sysUserService.delete(sysUserIdsBo);
        return Result.success();
    }

    @RequestMapping(value = "/pwd/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public Result<?> login(@RequestBody @Valid UserPwdBo userPwdBo) {

        sysUserService.updatePwd(userPwdBo);
        return Result.success();
    }

}
