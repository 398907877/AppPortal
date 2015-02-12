package com.huake.saas.point.entity;

import java.util.Date;

import com.huake.base.IdEntity;

/**
 * 积分规则配置表
 * @author laidingqing
 *
 */
public class PointRule extends IdEntity {

	/**
	 * 租户编号
	 */
	private Long uid;
	
	/**
	 * 业务代码
	 */
	private String bizCode;
	
	/**
	 * 可产生积分的业务定义名称
	 */
	private String from;
	
	/**
	 * 可产生积分的业务定义名称说明
	 */
	private String name;
	
	/**
	 * 可产生积分值
	 */
	private Integer quantity;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	
	/**
	 * 修改时间
	 */
	private Date updatedAt;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
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

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
