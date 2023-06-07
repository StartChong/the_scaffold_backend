package com.biology.threeparty.security;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.biology.commons.base.ErrorResult;
import com.biology.commons.enums.sys.ResultCodeEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 14:10
 * @desc: 用户未登录
 */
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSONUtil.toJsonStr(ErrorResult.error(ResultCodeEnum.USER_NOT_LOGIN, ResultCodeEnum.USER_NOT_LOGIN.message())));
    }
}