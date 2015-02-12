package com.huake.saas.coupon.entity;

import java.util.Date;

import com.huake.base.IdEntity;

/**
 * 用户的券
 * @author laidingqing
 *
 */
public class UserCoupon extends IdEntity {

	private Long uid;
	
	/**
	 * 与TenancyUser关联
	 */
	private Long memberId;
	
	/**
	 * 获取来源(管理员发放，主动领取)
	 */
	private String from;
	
	/**
	 * 券编号
	 */
	private String numCode;
	/**
	 * 过期时间
	 */
	private Date expireDate;
	/**
	 * 状态(过期，已使用)
	 */
	private String status;
	
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
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getNumCode() {
		return numCode;
	}
	public void setNumCode(String numCode) {
		this.numCode = numCode;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
