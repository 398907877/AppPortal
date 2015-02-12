package com.huake.saas.company.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.huake.saas.base.entity.BaseEntity;
/**
 * 企业介绍类别管理
 * 
 * @author zhongshuhui
 * 
 */
@Entity
@Table(name = "app_company_category")
public class CompanyCategory extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PARMS_COMPANY_CATEGORY= "companyCategory";
	public static final String PARMS_COMPANY_CATEGORYS= "companyCategorys";
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
