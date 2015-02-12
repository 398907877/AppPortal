package com.huake.saas.weixin.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;

/**
 * 通用拦截，用于验微信端安全请求及上下文装载
 * @author laidingqing
 *
 */
public class AccessApiRequestAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(AccessApiRequestAdvice.class);
	
	private TenancyService tenancyService ;
	
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable { 
		tenancyService = 
				  (TenancyService)WebApplicationContextUtils.
				    getRequiredWebApplicationContext(WebContext.getInstance().getServletContext()).
				    getBean("tenancyService");
		//验证请求的租户链接
		Tenancy tenancy = tenancyService.getTenancy(new Long(WebContext.getInstance().getUid()));
		
		if( null == tenancy){
			logger.error("", "No Found Enterprise ID.");
			throw new AppBizException("", "No Found Enterprise ID.");
		}
		return pjp.proceed();
	}

	public TenancyService getTenancyService() {
		return tenancyService;
	}

	public void setTenancyService(TenancyService tenancyService) {
		this.tenancyService = tenancyService;
	}
}
