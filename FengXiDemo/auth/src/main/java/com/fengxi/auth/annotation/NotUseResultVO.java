package com.fengxi.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于不使用统一返回格式
 *
 * @author wujiuhe
 * @description: TODO
 * @title: NotUseResultVO
 * @projectName FengXiDemo
 * @date 2023/1/31 14:47:39
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotUseResultVO {
    boolean isUse() default true;
}
