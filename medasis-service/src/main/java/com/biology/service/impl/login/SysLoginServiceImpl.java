package com.biology.service.impl.login;

import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.exception.SysException;
import com.biology.commons.interact.bo.sys.LoginBo;
import com.biology.commons.interact.vo.sys.LoginUserVo;
import com.biology.commons.utils.JwtUtils;
import com.biology.service.login.SysLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-29 10:19
 * @desc:
 */
@Service
@Slf4j
public class SysLoginServiceImpl implements SysLoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public LoginUserVo login(LoginBo loginBo) {
        // 用户验证
        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginBo.getAccount(), loginBo.getPassword()));
        } catch (BadCredentialsException e) {
            throw new SysException(ResultCodeEnum.USER_CREDENTIALS_ERROR.code(), "账号或密码错误！");
        }
        // 可以记录登录状态（成功）
        LoginUserVo loginUserVo = (LoginUserVo) authentication.getPrincipal();
        // 可以记录用户登录信息
        // 生成token信息
        // 生成token
        loginUserVo.setToken(jwtUtils.createToken(loginUserVo));
        return loginUserVo;
    }

}
