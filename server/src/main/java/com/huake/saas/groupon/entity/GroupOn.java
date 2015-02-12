package com.huake.saas.groupon.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.huake.base.IdEntity;
import com.huake.saas.product.entity.Product;

/**
 * 微团购模型，信息部份与商品关联。
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_groupons", indexes = {@Index(name="IDX_GROUPON_UID", columnList="uid"), @Index(name="IDX_GROUPON_PID", columnList="productId")})
public class GroupOn extends IdEntity {

	/**
	 * 关联商品编号
	 */
	private Long productId;
	/**
	 * 租户编号
	 */
	private Long uid;
	/**
	 * 团购价
	 */
	private BigDecimal groupOnPrice;
	/**
	 * 原价
	 */
	private BigDecimal originPrice;
	/**
	 * 折扣
	 */
	private Integer discount;
	
	/**
	 * 参与人数
	 */
	private Integer peopleCount;
	/**
	 * 团购说明
	 */
	
	private String content;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新时间
	 */
	private Date updateAt;
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 开始时间 
	 */
	private Date startDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	/**
	 * 销量
	 */
	private Integer sales;
	/**
	 *简单说明
	 */
	private String intro;
	/**
	 * 所有库存
	 */
	private Integer stock;
	
	/**
	 * 扩展属性   以区分 该团购商品在不同特性下的不同库存 和 不同价格
	 */
	private List<GroupOnFields> groupOnFields;
	/**
	 * 对应产品 信息
	 */
	private Product product;
	
	@Transient
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Transient
	public List<GroupOnFields> getGroupOnFields() {
		return groupOnFields;
	}
	public void setGroupOnFields(List<GroupOnFields> groupOnFields) {
		this.groupOnFields = groupOnFields;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	@NotBlank
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	@NotBlank
	public BigDecimal getGroupOnPrice() {
		return groupOnPrice;
	}
	public void setGroupOnPrice(BigDecimal groupOnPrice) {
		this.groupOnPrice = groupOnPrice;
	}
	@NotBlank
	public BigDecimal getOriginPrice() {
		return originPrice;
	}
	public void setOriginPrice(BigDecimal originPrice) {
		this.originPrice = originPrice;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getPeopleCount() {
		return peopleCount;
	}
	public void setPeopleCount(Integer peopleCount) {
		this.peopleCount = peopleCount;
	}
	@Lob
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
