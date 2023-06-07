package com.biology.threeparty.security;

import cn.hutool.json.JSONUtil;
import com.biology.commons.base.Result;
import com.biology.commons.interact.vo.sys.LoginUserVo;
import com.biology.commons.utils.JwtUtils;
import com.biology.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 14:07
 * @desc: 退出登录
 */

@Component
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private JwtUtils tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        LoginUserVo loginUser = tokenService.getLoginUser(httpServletRequest);
        if (StringUtils.isNotNull(loginUser)) {
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
        }
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSONUtil.toJsonStr(Result.success()));
    }
}

