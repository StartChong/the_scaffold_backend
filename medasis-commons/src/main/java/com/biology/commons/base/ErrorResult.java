package com.biology.commons.base;

import com.biology.commons.enums.sys.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-24 16:27
 * @desc:
 */
@Data
@ApiModel("通用错误返回类")
public class ErrorResult implements Serializable {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "是否成功")
    private boolean success = true;

    public static ErrorResult error() {
        ErrorResult result = new ErrorResult();
        result.setCode(ResultCodeEnum.INTERNAL_ERROR.code());
        return result;
    }

    public static ErrorResult error(String message) {
        ErrorResult result = new ErrorResult();
        result.setCode(ResultCodeEnum.INTERNAL_ERROR.code());
        result.setMessage(message);
        return result;
    }

    public static ErrorResult error(Integer code, String message) {
        ErrorResult result = new ErrorResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static ErrorResult error(ResultCodeEnum resultCode, String message) {
        ErrorResult result = new ErrorResult();
        result.setCode(resultCode.code());
        result.setMessage(message);
        return result;
    }

}
