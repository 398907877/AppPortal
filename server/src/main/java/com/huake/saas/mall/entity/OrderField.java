package com.huake.saas.mall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 订单拓展表
 * @author 
 *
 */
@Entity
@Table(name = "app_order_fields")
public class OrderField{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false)
	private Long id;
	/**
	 * 订单商品ID
	 */
	@Column(name = "o_p_id")
	private Long orderProductId;

	/**
	 * 字段名称
	 */
	private String name;
	/**
	 * 字段类型
	 */
	private String type;
	/**
	 * 字段描述
	 */
	private String detail;
	/**
	 * 字段值
	 */
	private String value;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderProductId() {
		return orderProductId;
	}
	public void setOrderProductId(Long orderProductId) {
		this.orderProductId = orderProductId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
