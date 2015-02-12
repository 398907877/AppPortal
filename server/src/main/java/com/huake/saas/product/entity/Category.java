package com.huake.saas.product.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.huake.saas.base.entity.BaseEntity;
/**
 * 产品类别管理
 * @author wuhui
 *
 */
@Entity
@Table(name = "app_product_category")
public class Category extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PARMS_CATEGORYS = "categorys";
	public static final String PARMS_CATEGORY = "category";
		
	/**
	 * 名称
	 */
	private String name;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
}
