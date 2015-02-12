package com.huake.saas.weixin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用于微信获取AccessToken的封装对象
 * @author laidingqing
 *
 */
public class AccessToken {
	
	@JsonProperty("access_token")
	private String token;
	
	@JsonProperty("expires_in")
	private int expire;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}
}
