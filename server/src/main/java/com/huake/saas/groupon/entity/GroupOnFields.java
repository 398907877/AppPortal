package com.huake.saas.groupon.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 团购拓展特性 以及该特性下 商品的库存 和价格
 * @author zjy
 *
 */
@Entity
@Table(name = "app_groupon_fields",indexes = {@Index(name="IDX_GROUPONFIELDS_GROUPONID", columnList="groupOnId")})
public class GroupOnFields {
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "field_id")
	private Long fieldId;
	/**
	 * 团购id
	 */
	@JsonIgnore
	private Long groupOnId;
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
	/**
	 * 该属性对应的库存
	 */
	private Integer stock;
	
	/**
	 * 该属性下的 价格
	 */
	private BigDecimal price;
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
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
	public Long getGroupOnId() {
		return groupOnId;
	}
	public void setGroupOnId(Long groupOnId) {
		this.groupOnId = groupOnId;
	}
	
}
