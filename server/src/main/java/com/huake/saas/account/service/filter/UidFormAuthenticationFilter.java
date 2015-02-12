package com.huake.saas.account.service.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.huake.saas.account.service.token.UsernamePasswordUidToken;



public class UidFormAuthenticationFilter extends FormAuthenticationFilter{

	 public static final String DEFAULT_UID_PARAM = "uid"; 
	   
	   private String uidParam  = DEFAULT_UID_PARAM; 
	   
	   public String getUidParam () { 
	      return uidParam ; 
	   } 
	   
	   public void setUid(String uidParam ) { 
	      this.uidParam  = uidParam ; 
	   } 
	   
	   protected String getUid(ServletRequest request) { 
	      return WebUtils.getCleanParam(request, getUidParam()); 
	   } 
	   
	   // 创建 Token 
	   @Override
	protected UsernamePasswordUidToken createToken( 
	      ServletRequest request, ServletResponse response) { 
	      String username = getUsername(request); 
	      String password = getPassword(request); 
	      String uid = getUid(request); 
	      boolean rememberMe = isRememberMe(request); 
	      String host = getHost(request); 
	                   
	      return new UsernamePasswordUidToken(username, password.toCharArray(), rememberMe, host, uid.trim()); 
	   } 
	   
	  
	    
	   // 认证
	   @Override
	protected boolean executeLogin(ServletRequest request, 
	      ServletResponse response) throws Exception { 
		   UsernamePasswordUidToken token = createToken(request, response); 
	       
	      try { 	                 
	         Subject subject = getSubject(request, response); 
	         subject.login(token);   
	         return onLoginSuccess(token, subject, request, response); 
	      } catch (AuthenticationException e) { 
	         return onLoginFailure(token, e, request, response); 
	      } 
	   } 
	
	
	
}
