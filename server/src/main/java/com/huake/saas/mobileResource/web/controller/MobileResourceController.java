package com.huake.saas.mobileResource.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.huake.dict.service.DictViewService;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.mobileResource.entity.MobileResource;
import com.huake.saas.mobileResource.service.MobileResourceService;

@Controller
@RequestMapping(value = "/mgr/mobileResouce")
public class MobileResourceController extends BaseController{
	
	@Autowired
	private DictViewService dictViewService;
	
	@Autowired
	private MobileResourceService resourceService;
	
	@RequestMapping(value = "showInfo")
	public String showInfo() {
		return "mobile/resource/info";
	}
	/**
	 * 进入 首页设置页面
	 * @return
	 */
	@RequestMapping(value="setIndex",method = RequestMethod.GET)
	public ModelAndView indexForm(){
		Long uid = getCurrentUID();
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("resource", new MobileResource());
		mav.addObject("bizcode", dictViewService.getDictViewList("BIZ_CODE"));//加载业务模块列表
		mav.addObject("templates", dictViewService.getDictViewList("TEMPLATE"));//加载模板列表
		mav.setViewName("mobile/resource/form");
		return mav;
	}
}
