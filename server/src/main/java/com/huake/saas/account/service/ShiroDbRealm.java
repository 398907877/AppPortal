/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.huake.saas.account.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.token.CaptchaAndUidToken;

import org.springside.modules.utils.Encodes;

import com.google.common.base.Objects;

public class ShiroDbRealm extends AuthorizingRealm {

	private AccountService accountService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
	
		CaptchaAndUidToken token = (CaptchaAndUidToken) authcToken;
		User user = accountService.findUserByLoginNameAndUid(token.getUsername(),Long.valueOf(token.getUid()));
		
		if (user != null) {
			if(isNomal(user))
			{
				byte[] salt = Encodes.decodeHex(user.getSalt());
				return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getLoginName(), user.getName(),user.getUid()),
						user.getPassword(), ByteSource.Util.bytes(salt), getName());
			}
			return null;	
		} else {
			return null;
		}
	}

	
	private boolean isNomal(User user)
	{
		if(user.getStatus().equals(User.STATUS_VALIDE))
		{
		    return true;
		}
		    return false;
	}
	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = accountService.findUserByLoginNameAndUid(shiroUser.loginName,shiroUser.uid);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(user.getRoleList());
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(AccountService.HASH_ALGORITHM);
		matcher.setHashIterations(AccountService.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Long id;
		public String loginName;
		public String name;
		//新加
		public Long uid;

		public ShiroUser(Long id, String loginName, String name,Long uid) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
			this.uid = uid;
			
		}

		public Long getUid() {
			return uid;
		}

		public String getName() {
			return name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 * 修改通过用户名写租户id
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			
			if(loginName == null || uid == null || other.loginName == null || other.uid == null)
			{
					return false;	
			}
			else if(loginName.equals(other.loginName) && uid.equals(other.uid) ){
			     return true;	
			}
			return false;
			

		}
	}
}
