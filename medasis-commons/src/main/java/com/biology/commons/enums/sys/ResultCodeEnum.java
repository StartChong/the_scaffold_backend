package com.biology.commons.enums.sys;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-24 16:05
 * @desc:
 */
public enum ResultCodeEnum {

    /* 成功状态码 */
    SUCCESS(200, "请求成功！"),

    /* 错误状态码 */
    NOT_FOUND(404, "请求的资源不存在！"),

    INTERNAL_ERROR(500, "服务器内部错误，请联系管理员！"),

    PARAMETER_EXCEPTION(501, "请求参数校验异常！"),

    FAIL(9999, "系统繁忙,请联系管理员！"),

    /* 业务状态码 */
    DATA_NOT_EXIST_ERROR(10001, "数据不存在！"),

    SUBMIT_REPEAT(10004, "请勿重复提交！"),

    DATA_EXIST(10005, "数据已存在！"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),
    ROLE_RESTRICTED(2010, "当前账号没有此权限！"),
    ;

    private final Integer code;
    private final String message;

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
