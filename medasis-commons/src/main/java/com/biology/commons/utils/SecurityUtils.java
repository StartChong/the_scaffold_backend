package com.biology.commons.utils;

import com.biology.commons.constant.Constants;
import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.exception.SysException;
import com.biology.commons.interact.vo.sys.LoginUserVo;
import org.omg.CORBA.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全服务工具类
 */
public class SecurityUtils {
    /**
     * 用户ID
     **/
    public static String getUserId() {
        try {
            return getLoginUser().getId();
        } catch (Exception e) {
            throw new SysException(ResultCodeEnum.DATA_NOT_EXIST_ERROR.code(), "用户id未找到！");
        }
    }

    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new SysException(ResultCodeEnum.DATA_NOT_EXIST_ERROR.code(), "用户账号未找到！");
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUserVo getLoginUser() {
        try {
            return (LoginUserVo) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new SysException(ResultCodeEnum.USER_NOT_LOGIN.code(), "用户未登录！");
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param account 用户账号
     * @return 结果
     */
    public static boolean isAdmin(String account) {
        return account.equals(Constants.ADMIN);
    }
}
