package com.biology.api.controller.business.sys;

import com.biology.api.annotation.SysLogAnnotation;
import com.biology.commons.base.Result;
import com.biology.commons.interact.bo.sys.role.*;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.sys.role.SysRoleQueryVo;
import com.biology.commons.interact.vo.sys.role.SysRoleVo;
import com.biology.service.sys.SysRoleService;
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
@Api(tags = "系统角色管理")
@RequestMapping("/api/business/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增角色", notes = "新增角色", response = Result.class)
    @SysLogAnnotation(module = "系统角色管理", type = "新增", desc = "新增角色")
    public Result<?> add(@RequestBody @Valid SysRoleBo sysRoleBo) {

        sysRoleService.save(sysRoleBo);
        return Result.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改角色", notes = "修改角色", response = Result.class)
    @SysLogAnnotation(module = "系统角色管理", type = "修改", desc = "修改角色")
    public Result<?> update(@RequestBody @Valid SysRoleEditBo sysRoleEditBo) {

        sysRoleService.update(sysRoleEditBo);
        return Result.success();
    }

    @RequestMapping(value = "/list/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询角色列表", notes = "查询角色列表")
    public Result<PageVo<SysRoleQueryVo>> query(@RequestBody @Valid SysRoleQueryBo sysRoleQueryBo) {

        return Result.success(sysRoleService.queryByPage(sysRoleQueryBo));
    }

    @RequestMapping(value = "/detail/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询角色详情", notes = "查询角色详情")
    public Result<SysRoleVo> detail(@RequestBody @Valid SysRoleIdBo sysRoleIdBo) {

        return Result.success(sysRoleService.queryDetail(sysRoleIdBo));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @SysLogAnnotation(module = "系统角色管理", type = "删除", desc = "删除角色")
    public Result<SysRoleVo> delete(@RequestBody @Valid SysRoleIdsBo sysRoleIdsBo) {

        sysRoleService.delete(sysRoleIdsBo);
        return Result.success();
    }

}
