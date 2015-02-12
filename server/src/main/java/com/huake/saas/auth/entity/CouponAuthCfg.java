package com.huake.saas.auth.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 电子优惠劵模块授权配置项
 * @author zjy
 *
 */
@Entity
@DiscriminatorValue(AuthCfg.AUTH_COUPON)
public class CouponAuthCfg extends AuthCfg{
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
