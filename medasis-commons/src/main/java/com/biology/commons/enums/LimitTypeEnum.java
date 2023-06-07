package com.biology.commons.enums;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-06 23:47
 * @desc: 限流类型
 */
public enum LimitTypeEnum {

    /**
     * 默认策略全局限流
     */
    DEFAULT,

    /**
     * 根据请求者IP进行限流
     */
    IP

}
