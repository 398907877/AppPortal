package com.huake.saas.point.entity;

import java.util.Date;

import com.huake.base.IdEntity;

/**
 * 会员积分记录
 * @author laidingqing
 *
 */
public class PointHistory extends IdEntity {

	/**
	 * 租户编号
	 */
	private Long uid;
	/**
	 * 会员编号
	 */
	private Long memberId;
	/**
	 * 积分来源：（根据管理员配置积分规则而产生，如注册会员初始积分）
	 */
	private String from;
	
	/**
	 * 积分变动值，可以为负数哦。 
	 */
	private Integer quantity;
	
	/**
	 * 创建时间
	 */
	private Date createdAt;

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
