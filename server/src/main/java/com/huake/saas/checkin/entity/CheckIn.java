package com.huake.saas.checkin.entity;

import java.util.Date;

import com.huake.base.IdEntity;

/**
 * 签到模块实体
 * @author laidingqing
 *
 */
public class CheckIn extends IdEntity {

	private Long uid;
	
	/**
	 * 注册会员编号，对应TenancyUser.java
	 */
	private Long memberId;
	
	private Date createdAt;
	
	private Long latitude;
	
	private Long longitude;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}
	
	
	
}
