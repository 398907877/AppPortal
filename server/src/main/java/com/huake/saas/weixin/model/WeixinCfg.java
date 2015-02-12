package com.huake.saas.weixin.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 微信渠道接入配置
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_weixin_cfg")
public class WeixinCfg extends IdEntity{

	/**
	 * 租户编号
	 */
	private long uid;
	
	private String appid;
	
	private String secret;
	
	private String accessToken;
	
	/**
	 * 注意这里的过期时间，如果过期需要重新获取
	 */
	private int expire;
	/**
	 * 登记于微信平台的token字符串
	 */
	private String token;
	
	
	

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
