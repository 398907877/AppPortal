package com.huake.saas.mobileResource.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.huake.base.IdEntity;

@Entity
@Table(name = "app_mobile_resource")
public class MobileResource extends IdEntity {
	
	public static String ROLE_NONE = "none";
	
	public static String ROLE_ADMIN = "admin";
	
	public static String BIZ_activity = "activity";
	
	public static String BIZ_news = "news";

	public static String BIZ_tcustomer = "tcustomer";

	public static String BIZ_supply = "supply";

	public static String BIZ_invitation = "invitation";

	public static String BIZ_product = "product";
	
	public static String BIZ_ernie = "ernie";
	
	

	
	
	/**
	 * 名称
	 */
	private String title;
	
	
	/**
	 * 业务模块
	 */
	private String bizCode;
	
	/**
	 * 目标地址
	 */
	private String target;
	
	/**
	 * 权限
	 */
	private String role;
	
	/**
	 * 模板名称
	 */
	private String template;
	/**
	 * 页面显示内容  比如首页 需要定义显示哪几个模块
	 */


	/**
	 * 所属租户id
	 */
	private Long uid;
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	
	
	
}
