package com.huake.saas.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 商品拓展表
 * @author zjy
 *
 */
@Entity
@Table(name = "app_product_fields")
public class ProductFields {
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "field_id")
	private Long fieldId;
	/**
	 * 商品ID
	 */
	@JsonIgnore
	private Long productId;
	/**
	 * 字段名称
	 */
	private String name;
	/**
	 * 字段类型
	 */
	@JsonIgnore
	private String type;
	/**
	 * 字段描述
	 */
	@JsonIgnore
	private String detail;
	/**
	 * 字段值 （多个值以_分隔）
	 */
	private String value;
	
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
