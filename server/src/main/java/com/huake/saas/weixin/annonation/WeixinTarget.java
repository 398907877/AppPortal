package com.huake.saas.weixin.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于页面提取接口方法，在微信配置服务中使用。
 * @author laidingqing
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WeixinTarget {
	String value() default "";
}
