package com.huake.saas.product.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.huake.saas.base.entity.BaseEntity;

/**
 * 产品展示持久类
 * @author 
 *
 */
@Entity
@Table(name = "app_product")
public class Product extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String PARMS_PRODUCT= "product";
	
	public static final String PARMS_PRODUCTS= "products";
	/**
	 * 库存不足
	 */
	public static final String NO_STOCK = "nostock";

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 介绍
	 */
	private String intro;

	/**
	 * 分类  关联category
	 */
	private Integer categoryId;
	
	/**
	 * 详细内容
	 */
	private String detail;
	
	/**
	 * 缩略图
	 */
	private String thumb;
	
	/**
	 * 销量
	 */
	private Integer sales;
	
	/**
	 * 价格
	 */
	private Double price;
	
	/**
	 *库存 
	 */
	private Integer total;
	
	/**
	 * 运费
	 */
	private Double freight;
	
	/**
	 * 限购数
	 */
	private Integer buyLimit;
	
	/**
	 * 二维码
	 */
	private String pictureCode;
	
	/**
	 * 图片集
	 */
	private List<ProductPictures> pictures;
	
	/**
	 * 扩展字段
	 */
	private List<ProductFields> productFields;
	
	private String category;
	
	
	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public Integer getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(Integer buyLimit) {
		this.buyLimit = buyLimit;
	}

	public String getPictureCode() {
		return pictureCode;
	}

	public void setPictureCode(String pictureCode) {
		this.pictureCode = pictureCode;
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
//	@NotBlank
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}


	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Lob
//	@NotBlank
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Transient
	public List<ProductPictures> getPictures() {
		return pictures;
	}

	public void setPictures(List<ProductPictures> pictures) {
		this.pictures = pictures;
	}
	
	@Transient
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@OneToMany(cascade = CascadeType.ALL,  
            fetch = FetchType.LAZY)  
	@JoinColumn(name="productId")
	public List<ProductFields> getProductFields() {
		return productFields;
	}

	public void setProductFields(List<ProductFields> productFields) {
		this.productFields = productFields;
	}
	
}
