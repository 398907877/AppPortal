package com.huake.saas.weixin.web.controller;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.location.entity.LocationRule;
import com.huake.saas.location.service.LocationRuleService;

/**
 * 位置服务管理
 * @author laidingqing
 *
 */
@Controller
@RequestMapping("/mgr/weixin/")
public class LocationAdminController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(LocationAdminController.class);
	
	@Autowired
	private LocationRuleService ruleService;
	
	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public ModelAndView list(){
		LocationRule rule = ruleService.findLocationRuleByUid(getCurrentUID());
		if( rule == null){
			rule = new LocationRule();
			log.debug("no found rule...");
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("rule", rule);
		mav.setViewName("weixin/locationList");
		return mav;
	}
	
	@RequestMapping(value = "/location/saveCfg", method = { RequestMethod.POST })
	public String saveLocationRule(ServletRequest request, LocationRule rule, RedirectAttributes redirectAttributes) {
		ruleService.saveLocationRule(rule);
		redirectAttributes.addAttribute("message", "位置服务更新成功");
		return "redirect:/mgr/weixin/location";
	}
	
}
