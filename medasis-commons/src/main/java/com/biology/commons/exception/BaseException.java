package com.biology.commons.exception;

import com.biology.commons.enums.sys.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-24 16:51
 * @desc: 自定义异常处理
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {

    private static final int BASE_EXCEPTION_CODE = ResultCodeEnum.INTERNAL_ERROR.code();

    private static final String BASE_EXCEPTION_MESSAGE = ResultCodeEnum.INTERNAL_ERROR.message();

    private Integer code;

    private String message;

    public BaseException() {
        super(BASE_EXCEPTION_MESSAGE);
        this.code = BASE_EXCEPTION_CODE;
        this.message = BASE_EXCEPTION_MESSAGE;
    }

    public BaseException(String message) {
        super(message);
        this.code = BASE_EXCEPTION_CODE;
        this.message = message;
    }

    public BaseException(ResultCodeEnum resultCode) {
        super(resultCode.message());
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.code = BASE_EXCEPTION_CODE;
        this.message = BASE_EXCEPTION_MESSAGE;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.code = BASE_EXCEPTION_CODE;
        this.message = message;
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}
