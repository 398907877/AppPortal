package com.huake.saas.auth.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 活动模块授权配置项
 * @author zjy
 *
 */
@Entity
@DiscriminatorValue(AuthCfg.AUTH_ACTIVITY)
public class ActivityAuthCfg extends AuthCfg{
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
