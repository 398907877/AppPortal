package com.huake.saas.news.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.huake.saas.base.entity.BaseEntity;

/**
 * 新闻资讯类别管理
 * 
 * @author zhongshuhui
 * 
 */
@Entity
@Table(name = "app_news_category", indexes = {@Index(name="IDX_NEWS_CAT_UID", columnList="uid")})
public class NewsCategory extends BaseEntity {
	
	/**
	 * 分类状态，TODO 待与原始定义比较，是否使用字典，当分类设为无效时，下属的新闻资讯条目如何处理？？？？@家泳
	 */
	public final static String CATEGORY_STATUS_VALID = "1";
	public final static String CATEGORY_STATUS_INVALID = "0";
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PARMS_NEWS_CATEGORY= "newsCategory";
	public static final String PARMS_NEWS_CATEGORYS= "newsCategorys";
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
