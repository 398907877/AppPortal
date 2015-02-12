package com.huake.saas.auth.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 产品模块授权配置项
 * @author zjy
 *
 */
@Entity
@DiscriminatorValue(AuthCfg.AUTH_PRODUCT)
public class ProductAuthCfg extends AuthCfg{
	/**
	 * 每日发布条数限制
	 */
	private int dayLimit;

	public int getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(int dayLimit) {
		this.dayLimit = dayLimit;
	}
}
