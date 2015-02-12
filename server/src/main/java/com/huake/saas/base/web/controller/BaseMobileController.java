package com.huake.saas.base.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.news.web.controller.NewsMobileController;
import com.huake.saas.weixin.aop.WebContext;


public class BaseMobileController {

	private final Logger log = LoggerFactory.getLogger(BaseMobileController.class);
	
	@ExceptionHandler(value = {AppBizException.class, Exception.class})
	public ModelAndView badRequest(Exception ex, HttpServletResponse response) {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("message", ex.getMessage());
		log.error("", ex);
		return new ModelAndView("mobile/error",map);
	}
	
	@ModelAttribute("currentUID")
	public String getCurrentUID() {
		return WebContext.getInstance().getUid();
	}
}
