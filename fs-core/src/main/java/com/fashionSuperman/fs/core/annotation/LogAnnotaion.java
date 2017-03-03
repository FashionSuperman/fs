package com.fashionSuperman.fs.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * @description 
 * @author FashionSuperman
 * @date 2017年3月1日 下午1:54:11
 * @version 1.0
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotaion {
	String module() default "";  //功能模块
	String remark() default ""; // 操作备注
	String operateType() default ""; // 操作类型：Add/Update/Delete/Search
}
