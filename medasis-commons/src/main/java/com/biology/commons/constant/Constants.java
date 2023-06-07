package com.biology.commons.constant;

import io.jsonwebtoken.Claims;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-11 18:31
 * @desc: 常量
 */
public class Constants {

    public static final String SYS = "sys";

    public static final String ADMIN = "admin";

    public static final String DEFAULT_PASSWORD = "123456";

    public static final String EMPTY_STR = "";

    public static final String UNDERSCORE = "_";

    public static final String LINE = "-";

    public static final String SLANT = "/";

    public static final Integer ZERO = 0;

    public static final String COMMA = ",";

    public static final String SEMICOLON = ";";

    public static final String RESUBMIT_MSG = "请勿重复提交！";

    public static final String COLON = ":";

    public static final String POINT = ".";

    public static final String ALL_PERMISSION = "*:*:*";

    public static final String PATH_CACHE = "path_cache";

    public static final String USER_ROLE = "user_role:";

    public static final String USER_PERMISSION = "user_permission";

    public static final String LEVEL_RESULT = "level_result:";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 正则表达式
     */
    /**
     * Email地址校验规则
     */
    public static final String REG_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * PASS_WORD 验证用户密码规则(以字母开头，长度在6~18之间，只能包含字符、数字和下划线)
     */
    public static final String REG_PASS_WORD = "^[a-zA-Z]\\w{5,17}$";

    /**
     * CHINESE 只能是汉字校验规则
     */
    public static final String REG_CHINESE = "^[\\u4e00-\\u9fa5]{0,}$";

    /**
     * NUMBER 只能是数字校验规则
     */
    public static final String REG_NUMBER = "^[0-9]*$";

    /**
     * PHONE 手机号码校验规则
     */
    public static final String REG_PHONE = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    /**
     * PHONE 身份证号码校验规则
     */
    public static final String REG_ID_CARD = "^\\d{15}|\\d{18}$";

    /**
     * IMG 图片校验规则
     */
    public static final String REG_IMG = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";

    /**
     * IPv4地址校验规则
     */
    public static final String IPV4 = "^((25[0-5]|2[0-4]\\d|[1]{1}\\d{1}\\d{1}|[1-9]{1}\\d{1}|\\d{1})($|(?!\\.$)\\.)){4}$";

    /**
     * IPv6地址校验规则
     */
    public static final String IPV6 = "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))";


}
