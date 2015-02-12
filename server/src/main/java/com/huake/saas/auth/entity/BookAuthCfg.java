package com.huake.saas.auth.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 产品预订模块授权
 * @author zjy
 *
 */
@Entity
@DiscriminatorValue(AuthCfg.AUTH_BOOK)
public class BookAuthCfg extends AuthCfg{
	/**
	 * 每日条数限制
	 */
	private int dayLimit;

	public int getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(int dayLimit) {
		this.dayLimit = dayLimit;
	}
}
