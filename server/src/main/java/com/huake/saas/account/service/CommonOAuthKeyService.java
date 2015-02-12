package com.huake.saas.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huake.base.CodedValidationException;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;
import com.huake.security.AuthParams;
import com.huake.security.OAuthKeyService;
import com.huake.web.filter.OAuthFilter;

/**
 * 获取公钥、私钥实现类，适用于安全认证的过滤器.
 * @author laidingqing
 *
 */
@Component("oAuthKeyService")
public class CommonOAuthKeyService implements OAuthKeyService {

	@Autowired
	private TenancyService service;
	
	
	public AuthParams getAuthParams(String uid) {
		 Tenancy tenancy = service.getTenancy(Long.valueOf(uid));
		 AuthParams authParam = new AuthParams();
		 authParam.setApiKey(tenancy.getAppId());
		 authParam.setSecretKey(tenancy.getAppSecret());
		 return authParam;
	
	}


	public AuthParams getAuthParams(String uid, String apiKey) {
		 Tenancy tenancy = service.getTenancy(Long.valueOf(uid));
		 if(tenancy.getAppId() .equals(apiKey))
		 {
			 AuthParams authParam = new AuthParams();
			 authParam.setApiKey(tenancy.getAppId());
			 authParam.setSecretKey(tenancy.getAppSecret());
			 return authParam;
		 }
		 else
		 {
			 throw new CodedValidationException(OAuthFilter.ERROR_CODE_INVALID_API_KEY, "apiKey can not be access");
		 }	 
	}
	
	



}
