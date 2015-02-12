package com.huake.saas.base.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.huake.saas.account.service.ShiroDbRealm.ShiroUser;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;


public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
   
	public static final int File_MaxSize = 10 * 1024 * 1024;
	
	public final static Map<String, String> FAILD_RESULT = new HashMap<String, String>();
	
	static{
		FAILD_RESULT.put("faild", "true");
	}
	
	@Autowired
	public TenancyService tenancyService;

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	/**
	 * 取出Shiro中的当前用户UId.
	 */
	@ModelAttribute("currentTenancyID")
	public Long getCurrentUID() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.getUid();
	}
	
	/**
	 * 获取当前租户名称
	 * @param uid
	 * @return
	 */
	@ModelAttribute("currentTenancyName")
	public String getCurrentTenancyName(){
		
		Tenancy tenancy = tenancyService.getTenancy(getCurrentUID());
		if( tenancy != null){
			logger.debug("current tenancy name is :" + tenancy.getName());
			return tenancy.getName();
		}
		return "";
	}
	/**
	 * 静态资源的代理路径
	 */
	@Value("#{envProps.app_files_url_prefix}")
	private String proxyPath;
	
	@ModelAttribute("proxyPath")
	public String getProxyPath(){
		return proxyPath;
	}
	
	
}
