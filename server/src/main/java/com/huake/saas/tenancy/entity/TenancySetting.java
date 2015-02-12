package com.huake.saas.tenancy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 租户必要设置项，包括同步设置，通知设置，认证平台设置
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_tenancy_setting")
public class TenancySetting extends IdEntity {

	/**
	 * 租户编号
	 */
	@Column(unique=true)
	private String uid;
	/**
	 * 新浪
	 */
	private String tSinaKey;
	
	private String tSinaSecret;
	
	/**
	 * 腾迅
	 */
	private String tqqKey;
	
	private String tqqSecret;
	
	/**
	 * 百度运推送
	 */
	private String bdPushKey;
	
	private String bdPushSecret;
	
	/**
	 * 百度云统计
	 */
	private String bdAnalyzeKey;
	
	private String bdAnalyzeSecure;
	
	/**
	 * 微信
	 */
	private String weixinKey;
	
	private String weixinSecure;
	
	/**
	 * 微信同步开关
	 */
	private Boolean weixinSyncable;
	/**
	 * 新浪微博同步开关
	 */
	private Boolean tSinaSyncable;
	
	/**
	 * 腾迅微博同步开关设置
	 */
	private Boolean tQQSyncable;
	
	private Date crtDate;
	
	private Date uptDate;

	public String gettSinaKey() {
		return tSinaKey;
	}

	public void settSinaKey(String tSinaKey) {
		this.tSinaKey = tSinaKey;
	}

	public String gettSinaSecret() {
		return tSinaSecret;
	}

	public void settSinaSecret(String tSinaSecret) {
		this.tSinaSecret = tSinaSecret;
	}

	public String getTqqKey() {
		return tqqKey;
	}

	public void setTqqKey(String tqqKey) {
		this.tqqKey = tqqKey;
	}

	public String getTqqSecret() {
		return tqqSecret;
	}

	public void setTqqSecret(String tqqSecret) {
		this.tqqSecret = tqqSecret;
	}

	public String getBdPushKey() {
		return bdPushKey;
	}

	public void setBdPushKey(String bdPushKey) {
		this.bdPushKey = bdPushKey;
	}

	public String getBdPushSecret() {
		return bdPushSecret;
	}

	public void setBdPushSecret(String bdPushSecret) {
		this.bdPushSecret = bdPushSecret;
	}

	public String getBdAnalyzeKey() {
		return bdAnalyzeKey;
	}

	public void setBdAnalyzeKey(String bdAnalyzeKey) {
		this.bdAnalyzeKey = bdAnalyzeKey;
	}

	public String getBdAnalyzeSecure() {
		return bdAnalyzeSecure;
	}

	public void setBdAnalyzeSecure(String bdAnalyzeSecure) {
		this.bdAnalyzeSecure = bdAnalyzeSecure;
	}

	public String getWeixinKey() {
		return weixinKey;
	}

	public void setWeixinKey(String weixinKey) {
		this.weixinKey = weixinKey;
	}

	public String getWeixinSecure() {
		return weixinSecure;
	}

	public void setWeixinSecure(String weixinSecure) {
		this.weixinSecure = weixinSecure;
	}

	public Boolean getWeixinSyncable() {
		return weixinSyncable;
	}

	public void setWeixinSyncable(Boolean weixinSyncable) {
		this.weixinSyncable = weixinSyncable;
	}

	public Boolean gettSinaSyncable() {
		return tSinaSyncable;
	}

	public void settSinaSyncable(Boolean tSinaSyncable) {
		this.tSinaSyncable = tSinaSyncable;
	}

	public Boolean gettQQSyncable() {
		return tQQSyncable;
	}

	public void settQQSyncable(Boolean tQQSyncable) {
		this.tQQSyncable = tQQSyncable;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public Date getUptDate() {
		return uptDate;
	}

	public void setUptDate(Date uptDate) {
		this.uptDate = uptDate;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
