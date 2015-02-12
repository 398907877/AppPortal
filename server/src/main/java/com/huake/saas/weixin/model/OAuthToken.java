package com.huake.saas.weixin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthToken {

	@JsonProperty("access_token")
	private String token;
	@JsonProperty("expires_in")
	private Integer expires;
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	private String openid;
	
	private String scope;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getExpires() {
		return expires;
	}

	public void setExpires(Integer expires) {
		this.expires = expires;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
