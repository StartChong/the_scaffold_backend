package com.biology.threeparty.security;

import cn.hutool.json.JSONUtil;
import com.biology.commons.base.ErrorResult;
import com.biology.commons.enums.sys.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 14:10
 * @desc: 没有权限
 */
@Component
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSONUtil.toJsonStr(ErrorResult.error(ResultCodeEnum.ROLE_RESTRICTED, "请求访问：" + request.getRequestURI() + "失败，权限不足！")));
    }
}
