package com.biology.commons.annotation;

import java.lang.annotation.*;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-24 16:37
 * @desc: 作用于controller类,或里面的方法（作用于类上，对所有接口有效）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD}) //作用于方法和类（接口）上
@Documented
public @interface ResponseResult {
}
