package com.huake.saas.base.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huake.saas.tenancy.service.TenancyService;


/**
 * 
 * @author hyl
 * 登入后首页跳转控制
 *
 */
@Controller("indexController")
@RequestMapping("/mgr/")
public class IndexController extends BaseController{
	
	@Autowired
	private TenancyService tenancyService;
	/**
      * 首页跳转
      * @return
      */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(Model model)
	{	
		int expiring = tenancyService.findByDeadline(getCurrentUserId(), "1").size();//查询快过期租户数
		int expired = tenancyService.findByDeadline(getCurrentUserId(), "2").size(); //查询已过期租户数
		model.addAttribute("expiring", expiring);
		model.addAttribute("expired", expired);
		return "index";
	}

}
