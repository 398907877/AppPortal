package com.huake.saas.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;

@MappedSuperclass
public class BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 定义接口分页  first默认为first=0
	 */
	public static final Integer DATE_FIRST= 0;
	
	/**
	 * 定义接口分页  max默认为max=10
	 */
	public static final Integer DATE_MAX= 10;
	
	/**
	 *接口取出全部  定义出每页取99999999条  约等于无限大
	 */
	public static final Integer PAGE_SIZE = 999999999;
	
	/**
	 *数据根据创建时间降序输出（默认方式）
	 */
	public static final String PAGE_CRTDATE_DESC = "auto";
	
	/**
	 *数据根据创建时间升序输出
	 */
	public static final String PAGE_CRTDATE_ASC="crtDate";
	
	
	/**
	 *数据根据更新时间降序输出
	 */
	public static final String PAGE_UPDATE_DESC="upTime";
	
	/**
	 * 默认为第一页
	 */
	public static final Integer PAGE_NUMBER = 1;
	
	
	/**
	 * 有效
	 */
	public static final String STATUS_VALIDE="1";
	/**
	 * 无效
	 */
	public static final String STATUS_INVALIDE="0";
	
	/**
	 * 
	 */
	public static final String STATUS_SUCCESS= "{status:success}";
	
	
	protected Long id;
	
	private String uid;
	
	protected String status;
	
	private Date crtDate;
	
	private Date upDate;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getUpDate() {
		return upDate;
	}

	public void setUpDate(Date upDate) {
		this.upDate = upDate;
	}
	
	
}
