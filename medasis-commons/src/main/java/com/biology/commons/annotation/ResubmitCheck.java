package com.biology.commons.annotation;

import com.biology.commons.constant.Constants;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-24 18:18
 * @desc: 防重复提交注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ResubmitCheck {

    /**
     * 参数Spring EL表达式例如 #{param.name},表达式的值作为防重复校验key的一部分
     */
    String[] argExpressions();

    /**
     * 重复提交报错信息
     */
    String message() default Constants.RESUBMIT_MSG;

    /**
     * Spring EL表达式,决定是否进行重复提交校验,多个条件之间为且的关系,默认是进行校验
     */
    String[] conditionExpressions() default {"true"};

    /**
     * 是否选用当前操作用户的信息作为防重复提交校验key的一部分
     */
    boolean withUserInfoInKey() default true;

    /**
     * 是否仅在当前session内进行防重复提交校验
     */
    boolean onlyInCurrentSession() default false;

    /**
     * 防重复提交校验的时间间隔
     */
    long interval() default 1;

    /**
     * 防重复提交校验的时间间隔的单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
