package com.huake.saas.coupon.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 优惠券
 * @author laidingqing
 *
 */
@Entity
@Table(name="app_coupons",  indexes = {@Index(name="IDX_COUPON_UID", columnList="uid"), 
		@Index(name="IDX_COUPON_CAT", columnList="category"),
		@Index(name="IDX_COUPON_STATUS", columnList="status")})
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="category", discriminatorType=DiscriminatorType.STRING)
public abstract class Coupon extends IdEntity{
	
	/**
	 * 折扣券
	 */
	public final static String COUPON_CATEGORY_DISCOUNT = "discount";
	
	/**
	 * 现金券
	 */
	public final static String COUPON_CATEGORY_CASH = "cash";
	
	/**
	 * 免费体验券
	 */
	public final static String COUPON_CATEGORY_TIME =  "free";
	
	/**
	 * 租户编号
	 */
	private String uid;
	/**
	 * 优惠券名称
	 */
	private String name;
	/**
	 * 拆扣或是代金数
	 */
	private BigDecimal money;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 累计发放张数, 0为无限
	 */
	private Integer total;
	/**
	 * 剩余
	 */
	private Integer overage;
	/**
	 * 使用数
	 */
	private Integer used;
	
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新日期
	 */
	private Date updatedAt;
	/**
	 * 状态
	 */
	private String status;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getOverage() {
		return overage;
	}
	public void setOverage(Integer overage) {
		this.overage = overage;
	}
	public Integer getUsed() {
		return used;
	}
	public void setUsed(Integer used) {
		this.used = used;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
