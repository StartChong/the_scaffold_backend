package com.biology.threeparty.security;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.biology.commons.base.ErrorResult;
import com.biology.commons.base.Result;
import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-29 21:06
 * @desc: 认证失败处理类 返回未授权
 */
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        ErrorResult errorResult;
        if (e instanceof InsufficientAuthenticationException) {
            errorResult = ErrorResult.error(ResultCodeEnum.USER_NOT_LOGIN.code(), e.getMessage());
        } else {
            errorResult = ErrorResult.error(ResultCodeEnum.ROLE_RESTRICTED.code(), e.getMessage());
        }
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSONUtil.toJsonStr(errorResult));
    }
}
