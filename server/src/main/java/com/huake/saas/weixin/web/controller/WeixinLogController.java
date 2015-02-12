package com.huake.saas.weixin.web.controller;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.huake.saas.access.entity.AccessLog;
import com.huake.saas.access.service.AccessLogService;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;

/**
 * @author jiajun 微信消息管理
 */
@RestController("weixinLogController")
@RequestMapping(value = "/mgr/weixin/**")
public class WeixinLogController extends BaseController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();

	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("reqAt", "请求时间");
	}

	@Autowired
	private AccessLogService accesslogservice;

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinMenuController.class);

	@RequestMapping(value = "logindex", method = RequestMethod.GET)
	public ModelAndView weixinLogIndex(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			ServletRequest request) {
		// 微信日志的主页
		System.out.println("进来了");
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		Page<AccessLog> accesslog = accesslogservice.getWeixinLogBySpcPageble(
				getCurrentUserId(), searchParams, pageNumber, pageSize,
				sortType);
		ModelAndView mav = new ModelAndView();

		mav.addObject("accesslog", accesslog);
		mav.addObject("sortTypes", sortTypes);

		mav.setViewName("/weixin/weixinlogIndex");

		return mav;

	}

}
