package com.huake.saas.weixin.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @author wujiajun
 * @desc 方法参数
 */

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface WeixinTargetParameter {
	public String enname() default "";  //参数的英文名称
	public String  description() default ""; //用于提示用户输入的值
	public String  zhname() default ""; //用于获取参数中文名称
	public String  type() default "text";//参数类型，是隐藏还是 弹窗(默认是text  暂定3种类型！！！！text，hidden，alert)
	public String  handle() default "";//特殊处理时候 传递的 信息，用于做业务处理
	
}
