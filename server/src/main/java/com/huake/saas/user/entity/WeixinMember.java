package com.huake.saas.user.entity;

import com.huake.base.IdEntity;

/**
 * 微信会员绑定系统会员登记表
 * @author laidingqing
 *
 */
public class WeixinMember extends IdEntity {

	/**
	 * 注册会员编号
	 */
	private Long userId;
	
	/**
	 * 租户编号
	 */
	private Long uid;
	
	/**
	 * 微信用户表，对应WeixinUser.java
	 */
	private Long wpId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getWpId() {
		return wpId;
	}

	public void setWpId(Long wpId) {
		this.wpId = wpId;
	}
	
	
}
