package com.huake.saas.location.service;

import com.huake.saas.location.model.Location;
import com.huake.saas.weixin.aop.WebContext;

/**
 * 位置服务抽象类，其它对应服务包继承此类
 * @author laidingqing
 *
 * @param <T>
 */
public abstract class AbstractLocationHandleService<T> implements LocationRuleHandleService<T> {

	/**
	 * 保护方法
	 */
	protected Location getLocation(WebContext context) {
		return null;
	}
}
