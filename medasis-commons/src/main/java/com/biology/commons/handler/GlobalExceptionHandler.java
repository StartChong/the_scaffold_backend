package com.biology.commons.handler;

import com.biology.commons.base.ErrorResult;
import com.biology.commons.constant.Constants;
import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.exception.BaseException;
import com.biology.commons.exception.SysException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-24 16:59
 * @desc:
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 统一处理自定义基础异常
     */
    @ExceptionHandler(BaseException.class)
    public ErrorResult baseException(BaseException e) {
        if (Objects.isNull(e.getCode())) {
            return ErrorResult.error(e.getMessage());
        }
        e.printStackTrace();
        return ErrorResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 统一处理自定义业务异常
     */
    @ExceptionHandler(SysException.class)
    public ErrorResult SysException(SysException e) {
        if (Objects.isNull(e.getCode())) {
            return ErrorResult.error(e.getMessage());
        }
        e.printStackTrace();
        return ErrorResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return ErrorResult.error(ResultCodeEnum.ROLE_RESTRICTED, "没有权限，请联系管理员授权");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                           HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return ErrorResult.error(e.getMessage());
    }


    /**
     * 统一处理非自定义异常外的所有异常
     */
    @ExceptionHandler(Exception.class)
    public ErrorResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ErrorResult.error(e.getMessage());
    }

    /**
     * 兼容Validation校验框架：忽略参数异常处理器
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResult parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        return ErrorResult.error(ResultCodeEnum.PARAMETER_EXCEPTION, "请求参数 " + e.getParameterName() + " 不能为空!");
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public ErrorResult handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return ErrorResult.error(message);
    }

    /**
     * 兼容Validation校验框架：缺少请求体异常处理器
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResult parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        String[] messageParts = Objects.requireNonNull(e.getMessage()).split(Constants.SEMICOLON);
        if (messageParts.length != 0) {
            return ErrorResult.error(ResultCodeEnum.PARAMETER_EXCEPTION, "参数体转换异常!-->" + messageParts[0]);
        }
        return ErrorResult.error(ResultCodeEnum.PARAMETER_EXCEPTION, "参数体不能为空,请检查参数体是否正确！");
    }

    /**
     * 兼容Validation校验框架：参数效验异常处理器
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult parameterExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return ErrorResult.error(ResultCodeEnum.PARAMETER_EXCEPTION, fieldError.getDefaultMessage());
            }
        }
        return ErrorResult.error(ResultCodeEnum.PARAMETER_EXCEPTION, "请求参数校验异常!");
    }
}