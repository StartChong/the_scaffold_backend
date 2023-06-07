package com.biology.commons.exception;

import com.biology.commons.enums.sys.ResultCodeEnum;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-24 16:53
 * @desc: 自定义系统异常
 */
public class SysException extends BaseException {

    public SysException(ResultCodeEnum resultCode) {
        super(resultCode);
    }

    public SysException(Integer code, String message) {
        super(code, message);
    }

    public static void throwException(boolean result, Integer code, String message) {
        if (result) {
            throw new SysException(code, message);
        }
    }

}
