package com.huake.saas.mobile.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.huake.saas.base.web.controller.BaseMobileController;
import com.huake.saas.company.entity.Company;
import com.huake.saas.company.service.CompanyService;

/**
 * 手机网站首页导航
 */
@RestController("mobileIndexController")
@RequestMapping(value = "/m/**")
public class IndexController extends BaseMobileController{

	@Autowired
	private CompanyService companyService;
	
	
	@RequestMapping(value = "index", method = { RequestMethod.GET })
	public ModelAndView index(@RequestParam(value="uid",required=true)String uid) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("uid", uid);
		mav.setViewName("mobile/index");
		return mav;
	}
	@RequestMapping(value="about",method={RequestMethod.GET})
	public ModelAndView about(@RequestParam(value="uid",required=true)String uid){
		ModelAndView mav = new ModelAndView();
		Company company = companyService.findByUid(uid);
		mav.addObject("company", company);
		mav.addObject("pictures", companyService.getPictures(company.getId().intValue()));
		mav.setViewName("mobile/about");
		return mav;
	}
}
