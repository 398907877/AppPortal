package com.huake.saas.account.service.token;

import org.apache.shiro.authc.UsernamePasswordToken;


/**
 * 自定义的 授权 用于进行 用户租户号的校验
 * @author Administrator
 *
 */
public class UsernamePasswordUidToken extends UsernamePasswordToken{


	private static final long serialVersionUID = 1L;
	
	private String uid;
	
	
	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public UsernamePasswordUidToken(String username, char[] password, 
			 boolean rememberMe, String host,String uid)
	{
		super(username, password, rememberMe, host);
		this.setUid(uid);
	}


	
}
