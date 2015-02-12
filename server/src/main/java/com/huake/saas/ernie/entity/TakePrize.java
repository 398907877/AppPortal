package com.huake.saas.ernie.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 兑奖信息实体
 *
 */
@Entity
@Table(name = "app_take_prize", indexes = {@Index(name="IDX_TAKEPRIZE_UID", columnList="uid"), 
		@Index(name="IDX_TAKEPRIZE_MEMID", columnList="memberId"),
		@Index(name="IDX_TAKEPRIZE_ERNIEID", columnList="ernieId")})
public class TakePrize extends IdEntity{
	/**
	 * 会员id
	 */
	private Long memberId;
	/**
	 * 租户编号
	 */
	private Long uid;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 收奖人姓名
	 */
	private String name;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 收奖地址
	 */
	private String address;
	/**
	 * 活动id
	 */
	private Long ernieId;
	/**
	 * 兑奖码
	 */
	private String sn;
	/**
	 * 状态
	 */
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getErnieId() {
		return ernieId;
	}
	public void setErnieId(Long ernieId) {
		this.ernieId = ernieId;
	}
	
	
}
