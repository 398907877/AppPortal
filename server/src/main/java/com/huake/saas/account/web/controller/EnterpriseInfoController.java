package com.huake.saas.account.web.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huake.saas.account.service.AccountService;
import com.huake.saas.account.service.ShiroDbRealm.ShiroUser;
import com.huake.saas.tenancy.service.TenancyService;

/**
 * 用户修改企业资料的controller
 * @author chen weirong
 *
 */
@Controller
@RequestMapping(value = "/mgr/enterprise")
public class EnterpriseInfoController {

	@Autowired
	private TenancyService tenancyService;
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String updateForm(Model model) {
		Long uid = getCurrentUserUid();
		model.addAttribute("tenancy", tenancyService.getTenancy(uid));
		return "crm/tenancy/edit";
	}
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserUid() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.uid;
	}	
	
}
