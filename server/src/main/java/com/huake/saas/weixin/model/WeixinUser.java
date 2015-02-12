package com.huake.saas.weixin.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huake.base.IdEntity;

/**
 * 微信用户资料，参考：http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_weixin_users", indexes = {@Index(name="IDX_WP_USER_UID", columnList="uid")})
public class WeixinUser extends IdEntity{
	
	/**
	 * 正常
	 */
	public static final String WEIXIN_USER_STATUS_NORMAL = "1";
	/**
	 * 取消订阅
	 */
	public static final String WEIXIN_USER_STATUS_UNSCRIBED = "0";

	private String openid;
	
	private String nickname;
	
	private String sex;
	
	private String language;
	
	private String city;
	
	private String province;
	
	private String country;
	
	private String headimgurl;
	
	private Date createdAt;
	
	private Long latitude;
	
	private Long longitude;
	
	private Long precisions;
	
	private List<String> privilege;
	/**
	 * 正常、或已取消订阅
	 */
	private String status;
	
	private Date updatedAt;
	
	private long uid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}

	public Long getPrecisions() {
		return precisions;
	}

	public void setPrecisions(Long precisions) {
		this.precisions = precisions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	@Transient
	public List<String> getPrivilege() {
		return privilege;
	}

	public void setPrivilege(List<String> privilege) {
		this.privilege = privilege;
	}
	
	
}
