package com.biology.api.controller.business.log;

import com.biology.commons.base.Result;
import com.biology.commons.interact.bo.log.SysLogIdBo;
import com.biology.commons.interact.bo.log.SysLogQueryBo;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.interact.vo.log.SysLogQueryVo;
import com.biology.commons.interact.vo.log.SysLogVo;
import com.biology.service.log.SysLogService;
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
 * @create: 2022-05-31 13:39
 * @desc:
 */
@RestController
@Api(tags = "操作日志信息")
@RequestMapping("/api/business/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping(value = "/list/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询操作日志列表", notes = "查询操作日志列表")
    public Result<PageVo<SysLogQueryVo>> query(@RequestBody @Valid SysLogQueryBo sysLogQueryBo) {

        return Result.success(sysLogService.queryByPage(sysLogQueryBo));
    }

    @RequestMapping(value = "/detail/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询操作日志详情", notes = "查询操作日志详情")
    public Result<SysLogVo> detail(@RequestBody @Valid SysLogIdBo sysLogIdBo) {

        return Result.success(sysLogService.queryDetail(sysLogIdBo));
    }

}
