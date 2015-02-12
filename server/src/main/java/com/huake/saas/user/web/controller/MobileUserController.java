package com.huake.saas.user.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.huake.saas.base.web.controller.BaseMobileController;

@RestController("mobileUserController")
@RequestMapping("/m/user/**")
public class MobileUserController extends BaseMobileController{
	
	@RequestMapping(value="bind",method = { RequestMethod.GET })
	public ModelAndView bindUser(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("mobile/user/bind");
		return mav;
	}
}
