package com.biology.api.controller.business.sys;

import com.biology.api.annotation.SysLogAnnotation;
import com.biology.commons.base.Result;
import com.biology.commons.interact.bo.sys.permission.*;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.sys.permission.SysPermissionQueryVo;
import com.biology.commons.interact.vo.sys.permission.SysPermissionVo;
import com.biology.service.sys.SysPermissionService;
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
@Api(tags = "系统权限管理")
@RequestMapping("/api/business/sys/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增权限", notes = "新增权限", response = Result.class)
    @SysLogAnnotation(module = "系统权限管理", type = "新增", desc = "新增权限")
    public Result<?> add(@RequestBody @Valid SysPermissionBo sysPermissionBo) {

        sysPermissionService.save(sysPermissionBo);
        return Result.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改权限", notes = "修改权限", response = Result.class)
    @SysLogAnnotation(module = "系统权限管理", type = "修改", desc = "修改权限")
    public Result<?> update(@RequestBody @Valid SysPermissionEditBo sysPermissionEditBo) {

        sysPermissionService.update(sysPermissionEditBo);
        return Result.success();
    }

    @RequestMapping(value = "/list/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询权限列表", notes = "查询权限列表")
    public Result<PageVo<SysPermissionQueryVo>> query(@RequestBody @Valid SysPermissionQueryBo sysPermissionQueryBo) {

        return Result.success(sysPermissionService.queryByPage(sysPermissionQueryBo));
    }

    @RequestMapping(value = "/detail/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询权限详情", notes = "查询权限详情")
    public Result<SysPermissionVo> detail(@RequestBody @Valid SysPermissionIdBo sysPermissionIdBo) {

        return Result.success(sysPermissionService.queryDetail(sysPermissionIdBo));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除权限", notes = "删除权限")
    @SysLogAnnotation(module = "系统权限管理", type = "删除", desc = "删除权限")
    public Result<SysPermissionVo> delete(@RequestBody @Valid SysPermissionIdsBo sysPermissionIdsBo) {

        sysPermissionService.delete(sysPermissionIdsBo);
        return Result.success();
    }

}
