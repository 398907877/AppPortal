package com.huake.saas.base.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.huake.saas.base.entity.AppBizException;


/**
 * curl -i -X POST -H "Accept: application/json" -H "Content-Type: application/json;charset=UTF-8" -d '{"email":"test1@test.com"}' http://localhost:8080/film/api/v3/users/sessions
 * @author laidingqing
 *
 */
public class BaseApiController {

	final static Logger logger = LoggerFactory.getLogger(BaseApiController.class);

	public final static Map<String, String> SUCCESS_RESULT = new HashMap<String, String>();
	
	public final static Map<String, String> FAILD_RESULT = new HashMap<String, String>();
	
	static{
		SUCCESS_RESULT.put("success", "true");
		FAILD_RESULT.put("faild", "true");
	}
	@ExceptionHandler(value = {AppBizException.class, Exception.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Map<String, String> badRequest(Exception ex, HttpServletResponse response) {
		FAILD_RESULT.put("message", ex.getMessage());
		logger.error("api error:", ex);
		return FAILD_RESULT;
	}
	
}
