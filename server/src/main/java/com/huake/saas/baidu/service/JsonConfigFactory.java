package com.huake.saas.baidu.service;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


/**
 * 
 * @author wujiajun
 * @time20140604
 * 用于json为空时候的默认值配置
 *
 */
public class JsonConfigFactory {
	
	private static JsonConfig instance = null;

	public static synchronized JsonConfig getInstance() {
	if (instance == null) {
	System.out.println("初始化");
	instance = new JsonConfig();
	register(instance);
	}
	return instance;
	}

	private static void register(JsonConfig jsonConfig) {
	                //如果double类型为null，想输出null，那就注册double.class
	jsonConfig.registerJsonValueProcessor(Long.class,
	new JsonValueProcessor() {
	                                        //这个方法不用管
	public Object processArrayValue(Object value,
	JsonConfig arg1) {
	return value;
	}
	//修改此方法就可以
	public Object processObjectValue(String key, Object value,
	JsonConfig arg2) {
	//如果vlaue为null，就返回"",	为空就返回他的值，
	if (value == null) {
	return  "";
	}
	return value;
	}
	});
	
	
	
	
	
	//int
	jsonConfig.registerJsonValueProcessor(Integer.class,
	new JsonValueProcessor() {
	                                        //这个方法不用管
	public Object processArrayValue(Object value,
	JsonConfig arg1) {
	return value;
	}
	//修改此方法就可以
	public Object processObjectValue(String key, Object value,
	JsonConfig arg2) {
	//如果vlaue为null，就返回"",	为空就返回他的值，
	if (value == null) {
	return  "";
	}
	return value;
	}
	});
	

	}
	}
