package com.huake.saas.mall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 第三方支付表
 * @author zhenghongwei
 *
 */
@Entity
@Table(name = "app_order_payway")
public class OrderPayWay{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false)
	private Long id;
	/**
	 * 支付ID()
	 */
	private String payId;
	/**
	 * 订单号
	 */
	private Long orderId;
	/**
	 * 支付类型
	 */
	private Integer type;
	/**
	 * 支付金额
	 */
	private String payMoney;
	/**
	 * 支付时间
	 */
	private Date payDate;
	
	private String returnCode;
	
	
	private String status;
	
	public String getPayId() {
		return payId;
	}
	public void setPayId(String string) {
		this.payId = string;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String string) {
		this.payMoney = string;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}