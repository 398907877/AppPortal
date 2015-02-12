package com.huake.saas.point.entity;

import java.util.Date;

import com.huake.base.IdEntity;

/**
 * 租户积分
 * @author laidingqing
 *
 */
public class Point extends IdEntity {

	/**
	 * 租户编号
	 */
	private Long uid;
	
	/**
	 * 积分值
	 */
	private Integer quantity;
	
	/**
	 * 最后修改日期
	 */
	private Date lastDate;
	
	/**
	 * 关联会员编号
	 */
	private Long memberId;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	
}
