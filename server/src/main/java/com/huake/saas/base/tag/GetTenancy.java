package com.huake.saas.base.tag;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;


import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;

public class GetTenancy extends TagSupport {

	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;

	private String id;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int doStartTag() throws JspTagException {
		return 1;
	}

	@Override
	public int doEndTag() throws JspTagException {
		try {
			WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());//获取SPRING的上下文
			TenancyService TService = (TenancyService) ctx.getBean("tenancyService");
			Tenancy tenancy =  TService.findById(Long.parseLong(id));
			if(tenancy!=null){
			pageContext.getOut().write(tenancy.getName());
			}else{
				pageContext.getOut().write("<b>租户已不存在</b>");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 6;
	}

}
