package com.biology.api.controller.sys;

import com.biology.api.annotation.SysLogAnnotation;
import com.biology.commons.base.Result;
import com.biology.commons.interact.bo.sys.LoginBo;
import com.biology.commons.interact.vo.sys.LoginUserVo;
import com.biology.service.login.SysLoginService;
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
@Api(tags = "系统用户登录")
@RequestMapping("/api/sys")
public class SysLoginController {

    @Autowired
    private SysLoginService sysLoginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "系统用户登录", notes = "系统用户登录")
    public Result<LoginUserVo> login(@RequestBody @Valid LoginBo loginBo) {

        return Result.success(sysLoginService.login(loginBo));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "退出登录", notes = "退出登录", response = Result.class)
    @SysLogAnnotation(module = "退出登录", type = "退出登录", desc = "退出登录")
    public Result<?> logout() {

        return Result.success();
    }

}
