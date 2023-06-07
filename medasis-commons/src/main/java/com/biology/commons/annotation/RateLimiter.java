package com.biology.commons.annotation;

import com.biology.commons.constant.Constants;
import com.biology.commons.enums.LimitTypeEnum;

import java.lang.annotation.*;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-06 23:47
 * @desc: 限流注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流key
     */
    public String key() default Constants.RATE_LIMIT_KEY;

    /**
     * 限流时间,单位秒
     */
    public int time() default 60;

    /**
     * 限流次数
     */
    public int count() default 100;

    /**
     * 限流类型
     */
    public LimitTypeEnum limitType() default LimitTypeEnum.DEFAULT;

}
