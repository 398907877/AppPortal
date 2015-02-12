package com.huake.saas.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huake.saas.weixin.aop.WebContext;

/**
 * 微信请求上下文过滤器
 * @author laidingqing
 *
 */
public class WebContextFilter implements Filter {

private static final Logger logger = LoggerFactory.getLogger(WebContextFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		logger.debug("web context filter...");
		
		HttpServletRequest request = (HttpServletRequest) req;  
        HttpServletResponse response = (HttpServletResponse) resp;  
        ServletContext servletContext = request.getSession().getServletContext(); 
        
        WebContext.create(request, response, servletContext);  
        
        String requestPrefixDomain = request.getRemoteAddr();
        String requestParamaterUID = request.getParameter("uid");
        
        WebContext.getInstance().setUid(requestParamaterUID);
        WebContext.getInstance().setDomain(requestPrefixDomain);
        
        chain.doFilter(request, response);  
        WebContext.clear(); 
	}

	@Override
	public void destroy() {

	}

}
