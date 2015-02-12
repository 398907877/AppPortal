/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.huake.saas.account.web.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.huake.saas.base.web.controller.BaseMobileController;
import com.huake.saas.tenancy.service.TenancyService;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

/**
 * 
 * 手机登陆
 * 
 */
@Controller
@RequestMapping(value = "/m/login")
public class LoginMobileController extends BaseMobileController{

	@Autowired	
	private TenancyUserService tenancyUserService;
	
	@Autowired	
	private TenancyService tenancyService;
	
	
	/**登陆首页
	 * @param uid
	 * @param url
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value="uid")  final String uid,
			@RequestParam(value="url" ,required = false,defaultValue="")  final String url) {
		ModelAndView mav = new ModelAndView();
		TenancyUser tenancyUser = new TenancyUser();
		mav.addObject("TenancyUser", tenancyUser);
		mav.addObject("uid", uid);
		mav.addObject("url", url);
		mav.setViewName("mobile/account/login");
		return mav;
	}

	/**如果有url参数，登陆后到指定url
	 * @param url
	 * @param request
	 * @param response
	 * @param tenancyUser
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String check(@RequestParam(value="url" ,required = false)  final String url,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute(value="TenancyUser")TenancyUser tenancyUser) throws Exception{
		String loginName = tenancyUser.getLoginname();
		long uid = tenancyUser.getUid();
		TenancyUser tenancyUsers = tenancyUserService.findByLoginnameAndUid(loginName, uid);
		String password = getPassWord(tenancyUser.getPassword());
		HttpSession session = request.getSession();
		if(tenancyUsers==null){
			session.setAttribute("login", "false");
			return "redirect:/m/login?uid="+uid;
		}else if(!tenancyUsers.getPassword().equals(password)){
			session.setAttribute("login", "false");
			return "redirect:/m/login?uid="+uid;
		}else{
			session.setAttribute("login", "true");
			session.setAttribute("uid", uid);
			session.setAttribute("memberId", tenancyUsers.getId());
			if(url!=null&!url.equals("")){
				System.out.println("1");
				return "redirect:"+url+""+tenancyUsers.getId();
			}else{
				return "redirect:/m/index?uid="+uid;
			}
			
			
		}
	}
	
	/**密码加密
	 * @param passWord
	 * @return
	 */
	public static String getPassWord(String passWord) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(passWord.getBytes());
			passWord = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return passWord;
	}


}
