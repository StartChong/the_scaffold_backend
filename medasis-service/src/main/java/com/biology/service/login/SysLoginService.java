package com.biology.service.login;

import com.biology.commons.interact.bo.sys.LoginBo;

import com.biology.commons.interact.vo.sys.LoginUserVo;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-28 16:04
 * @desc:
 */
public interface SysLoginService {

    /**
     * login
     *
     * @param loginBo
     * @return LoginUserVo
     * @author: lichong
     * @description 用户登录
     * @date 2022/4/29
     */
    LoginUserVo login(LoginBo loginBo);

}
