package com.biology.api.controller.business.sys;

import com.biology.api.annotation.SysLogAnnotation;
import com.biology.commons.base.Result;

import com.biology.commons.interact.bo.sys.path.*;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.sys.path.SysRequestPathQueryVo;
import com.biology.commons.interact.vo.sys.path.SysRequestPathVo;
import com.biology.service.sys.SysRequestPathService;
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
@Api(tags = "系统请求路径管理")
@RequestMapping("/api/business/sys/path")
public class SysReqPathController {

    @Autowired
    private SysRequestPathService sysRequestPathService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增请求路径", notes = "新增请求路径", response = Result.class)
    @SysLogAnnotation(module = "系统请求路径管理", type = "新增", desc = "新增请求路径")
    public Result<?> add(@RequestBody @Valid SysRequestPathBo sysRequestPathBo) {

        sysRequestPathService.save(sysRequestPathBo);
        return Result.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改请求路径", notes = "修改请求路径", response = Result.class)
    @SysLogAnnotation(module = "系统请求路径管理", type = "修改", desc = "修改请求路径")
    public Result<?> update(@RequestBody @Valid SysRequestPathEditBo sysRequestPathEditBo) {

        sysRequestPathService.update(sysRequestPathEditBo);
        return Result.success();
    }

    @RequestMapping(value = "/list/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询请求路径列表", notes = "查询请求路径列表")
    public Result<PageVo<SysRequestPathQueryVo>> query(@RequestBody @Valid SysRequestPathQueryBo sysRequestPathQueryBo) {

        return Result.success(sysRequestPathService.queryByPage(sysRequestPathQueryBo));
    }

    @RequestMapping(value = "/detail/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询请求路径详情", notes = "查询请求路径详情")
    public Result<SysRequestPathVo> detail(@RequestBody @Valid SysRequestPathIdBo sysRequestPathIdBo) {

        return Result.success(sysRequestPathService.queryDetail(sysRequestPathIdBo));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除请求路径", notes = "删除请求路径")
    @SysLogAnnotation(module = "系统请求路径管理", type = "删除", desc = "删除请求路径")
    public Result<SysRequestPathVo> delete(@RequestBody @Valid SysRequestPathIdsBo sysRequestPathIdsBo) {

        sysRequestPathService.delete(sysRequestPathIdsBo);
        return Result.success();
    }

}
