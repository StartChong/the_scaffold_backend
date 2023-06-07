package com.biology.api.annotation;

import java.lang.annotation.*;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-31 10:38
 * @desc: 自定义操作日志注解
 */
@Target(ElementType.METHOD)//注解放置的目标位置即方法级别
@Retention(RetentionPolicy.RUNTIME)//注解在哪个阶段执行
@Documented
public @interface SysLogAnnotation {

    String module() default ""; // 操作模块

    String type() default "";  // 操作类型

    String desc() default "";  // 操作说明

}
