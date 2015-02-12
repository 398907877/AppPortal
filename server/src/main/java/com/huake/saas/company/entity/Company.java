package com.huake.saas.company.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huake.saas.base.entity.BaseEntity;
/**
 * 企业介绍管理
 * @author zhongshuhui
 */
@Entity
@Table(name = "app_company")
public class Company extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PARMS_COMPANYS = "companys";
	public static final String PARMS_COMPANY = "company";
	
	/**
	 * 分类  关联category
	 */
	private Integer categoryId;
	
	/**
	 * 详细内容
	 */
	private String detail;
	
	/**
	 * 介绍
	 */
	private String intro;
	

	/**
	 * 标题
	 */
	private String title;
	
	private String category;
	
    private String pic;
    
	private List<CompanyPictures> pictures;

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	@Transient
	public List<CompanyPictures> getPictures() {
		return pictures;
	}

	public void setPictures(List<CompanyPictures> pictures) {
		this.pictures = pictures;
	}

	@Transient
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getCategoryId() {
		return categoryId;
	}



	public static String getParmsCompanys() {
		return PARMS_COMPANYS;
	}

	public static String getParmsCompany() {
		return PARMS_COMPANY;
	}

	@Lob
//	@NotBlank
	public String getDetail() {
		return detail;
	}

	@Lob
//	@NotBlank
	public String getIntro() {
		return intro;
	}

	
//	@NotBlank
	public String getTitle() {
		return title;
	}


	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

}
