package com.huake.saas.version.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 客户端软件片本更新信息.
 * @author skylai
 *
 */
@Entity
@Table(name="app_appinfo")
public class AppVersionInfo implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;
	/**
	 * 手机类型，1: Android/2: iPhone/...
	 */
	@Column(name = "category")
	public String appCategory;
	/**
	 * 应用软件名称
	 */
	@Column
	public String appName;
	/**
	 * 版本代码，如1, android 对应为 android:versionCode="1" android:versionName="1.0.0"
	 */
	@Column
	public Integer verCode;
	/**
	 * 版本名称，用于显示.
	 */
	@Column
	public String verName;
	/**
	 * 应用下载地址
	 */
	@Column
	public String url;
	
	/**
	 * 创建日期
	 */
	@Column
	public Date crtDate;
	
	@Column
	private Boolean isNew;
	/**
	 * 更新说明
	 */
	@Column
	private String intro;
	
	private String tenancyId;
	/**
	 * @return the isNew
	 */
	public Boolean getIsNew() {
		return isNew;
	}
	/**
	 * @param isNew the isNew to set
	 */
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getAppCategory() {
		return appCategory;
	}
	public void setAppCategory(String appCategory) {
		this.appCategory = appCategory;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Integer getVerCode() {
		return verCode;
	}
	public void setVerCode(Integer verCode) {
		this.verCode = verCode;
	}
	public String getVerName() {
		return verName;
	}
	public void setVerName(String verName) {
		this.verName = verName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getTenancyId() {
		return tenancyId;
	}
	public void setTenancyId(String tenancyId) {
		this.tenancyId = tenancyId;
	}
	
	
}
