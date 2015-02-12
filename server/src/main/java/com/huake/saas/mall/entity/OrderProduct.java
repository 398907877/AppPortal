package com.huake.saas.mall.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "app_order_product")
public class OrderProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false)
	private Long id;
	/**
	 * 订单号
	 */
	private Long orderId;
	
	/**
	 * 商品Id
	 */
	private Long productId;
	
	
	/**
	 * 订单扩展字段
	 */
	@Transient
	private List<OrderField> orderFields;
	
	/**
	 * 数量
	 */
	private Long num;
	/**
	 * 单价
	 */
	private double price;
	
	private String description;
	
	private String name;
	
	private Date crtDate;
	
	private String thumb;
	
	private double freight;
	@Transient
	private Long total;
	
	
	private long buyLimit;
	
	@Transient
	private String freightRule;
	
	
	
	public Long getTotal() {
		return total;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public List<OrderField> getOrderFields() {
		return orderFields;
	}
	public void setOrderFields(List<OrderField> orderFields) {
		this.orderFields = orderFields;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public double getFreight() {
		return freight;
	}

	public void setFreight(double freight) {
		this.freight = freight;
	}

	public long getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(long buyLimit) {
		this.buyLimit = buyLimit;
	}

	public String getFreightRule() {
		return freightRule;
	}

	public void setFreightRule(String freightRule) {
		this.freightRule = freightRule;
	}


	
	
}