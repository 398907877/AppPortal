package com.huake.saas.tenancy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huake.base.IdEntity;

/**
 * 租户基本资料
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_tenancy")
public class Tenancy extends IdEntity{

	public static final String PARMS_TENANCY= "tenancy";
	
	public final static String DEL = "2";
	
	public final static String NODEL = "1";
	
	/**
	 * 内部编号,注意，使用UtilData生成随机号。
	 */
    @Column(name = "uid", length = 8, unique = true)
	private long uid;
	/**
	 * 公钥
	 */
	private String appId;
	/**
	 * 私钥
	 */
	private String appSecret;
	
	/**
	 * 租户名称,企业的全称
	 */
	private String name;
	
	/**
	 * 电话
	 */
	private String tel;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 企业logo图片
	 */
	private String logo;
	
	/**
	 * 生成日期
	 */
	private Date crtDate;
	/**
	 * 更新日期
	 */
	private Date uptDate;

	/**
	 * 状态，是否可用
	 */
	private String status;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 合同开始日期
	 */
	@Column(name="start_date")
	private Date startDate;
	
	/**
	 * 合同结束日期
	 */
	@Column(name="end_date")
	private Date endDate;
	
	/**
	 * 联系人姓名
	 */
	@Column(name="link_name")
	private String linkName;
	
	/**
	 * 联系人电话（手机）
	 */
	@Column(name="link_tel")
	private String linkTel;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getUptDate() {
		return uptDate;
	}
	public void setUptDate(Date uptDate) {
		this.uptDate = uptDate;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getLinkName() {
		return linkName;
	}
	
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	
	public String getLinkTel() {
		return linkTel;
	}
	
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
