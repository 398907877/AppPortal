package com.huake.saas.auth.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 合同授权配置
 * @author laidingqing
 *
 */
@Entity
@Table(name="app_auth_cfg",indexes = {@Index(name="IDX_AUTHCFG_UID",columnList="uid")})
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="bizcode", discriminatorType=DiscriminatorType.STRING)
public abstract class AuthCfg extends IdEntity{

	public final static String AUTH_NEWS   = "news";//新闻资讯
	public final static String AUTH_PRODUCT = "product";//产品展示
	public final static String AUTH_VIP = "vip";//会员卡
	public final static String AUTH_COUPON = "coupon";//优惠券
	public final static String AUTH_PAYMENT = "payment";//支付服务
	public final static String AUTH_BOOK = "book";//预订
	public final static String AUTH_SUPPLY = "supply";//供求信息
	public final static String AUTH_ACTIVITY = "activity";//活动
	public final static String AUTH_INVITATION = "invitation";//论坛
	
	/**
	 * 企业编号
	 */
	@Column(name = "uid", length = 8)
	private String uid;
	
	/**
	 * 主授权编号
	 */
	private Long authId;
	
	@Column(name = "created_at", updatable=false)
	private Date createdDate;

	/**
	 *  合同更新时间
	 */
	@Column(name = "updated_at")
	private Date updatedDate;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
