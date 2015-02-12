package com.huake.saas.weixin.aop;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebContext {
	private static ThreadLocal<WebContext> tlv = new ThreadLocal<WebContext>();  
	
    private HttpServletRequest request;  
    private HttpServletResponse response;  
    private ServletContext servletContext;
    private String uid;
    private String domain;
    private String fromUserName;
    private String toUserName;
  
    protected WebContext() {  
    }  
  
    public HttpServletRequest getRequest() {  
        return request;  
    }  
  
    public void setRequest(HttpServletRequest request) {  
        this.request = request;  
    }  
  
    public HttpServletResponse getResponse() {  
        return response;  
    }  
  
    public void setResponse(HttpServletResponse response) {  
        this.response = response;  
    }  
  
    public ServletContext getServletContext() {  
        return servletContext;  
    }  
  
    public void setServletContext(ServletContext servletContext) {  
        this.servletContext = servletContext;  
    }  
  
    private WebContext(HttpServletRequest request,  
            HttpServletResponse response, ServletContext servletContext) {  
        this.request = request;  
        this.response = response;  
        this.servletContext = servletContext;  
    }  
  
    public static WebContext getInstance() {  
        return tlv.get();  
    }  
  
    public static void create(HttpServletRequest request,  
            HttpServletResponse response, ServletContext servletContext) {  
        WebContext wc = new WebContext(request, response, servletContext);  
        tlv.set(wc);  
    }  
  
    public static void clear() {  
        tlv.set(null);  
    }

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	} 
}
