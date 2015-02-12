package com.huake.saas.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 对所有接口访问（/api/**）开始的请求进行拦截，完成API接口的请求记录
 * @author laidingqing
 *
 */
public class AccessApiLogInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(AccessApiLogInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();
		if( requestURI.startsWith("/api/")){
			//是以api开头，则进行Access日志存储，便于分析
			logger.debug("api access is logged...");
			
		}
		
		return true;
	}

}
