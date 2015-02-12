package com.huake.saas.auth.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 图文资讯模块授权配置项
 * @author laidingqing
 *
 */
@Entity
@DiscriminatorValue(AuthCfg.AUTH_NEWS)
public class NewsAuthCfg extends AuthCfg {

	/**
	 * 每日条数限额
	 */
	private int dayLimit;
	
	/**
	 * 月度视频限额
	 */
	private int monthVideoLimit;

	public int getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(int dayLimit) {
		this.dayLimit = dayLimit;
	}

	public int getMonthVideoLimit() {
		return monthVideoLimit;
	}

	public void setMonthVideoLimit(int monthVideoLimit) {
		this.monthVideoLimit = monthVideoLimit;
	}
	
	
}
