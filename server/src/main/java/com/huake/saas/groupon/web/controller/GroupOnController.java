package com.huake.saas.groupon.web.controller;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.groupon.entity.GroupOn;
import com.huake.saas.groupon.service.GroupOnService;

@Controller
@RequestMapping(value = "/mgr/groupon")
public class GroupOnController extends BaseController{
	
	@Autowired
	private GroupOnService groupOnService; 
	/**
	 * 进入功能介绍页面
	 * @return
	 */
	@RequestMapping(value = "showInfo")
	public String showInfo() {
		return "groupon/info";
	}
	/**
	 * 进入团购商品管理 界面
	 * @return
	 */
	@RequestMapping(value = "productMgr", method = RequestMethod.GET)
	public String toProductMgr(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC)String sortType
			,Model model,ServletRequest request){
		Long uid = getCurrentUID();
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, Message.SEARCH_CONDITIONS);
		Page<GroupOn> groupOns = groupOnService.findByCondition(uid,
				searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("content", groupOns);
		
		return "groupon/productMgr";
	}
}
