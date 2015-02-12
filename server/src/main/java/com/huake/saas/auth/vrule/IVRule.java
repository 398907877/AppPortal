package com.huake.saas.auth.vrule;

import com.huake.saas.auth.entity.AuthCfg;
import com.huake.saas.auth.exception.AuthValidationException;

/**
 * 业务模块授权细则校验接口  (如：图文资讯模块的每日条数限制等)
 * @author zjy
 *
 */
public interface IVRule <T,A extends AuthCfg>{
	
	/**
	 *  校验规则的主体方法  抛出异常则校验不通过
	 *  
	 * @param uid 要校验的租户id
	 * @param value 要校验的数据 
	 * @param useCfg 配置的已使用情况  (校验通过修改相应的配置项使用情况，并在controller中更新到redis中)
	 * @return
	 */
	public void check(Long uid,T value,A useCfg)throws AuthValidationException;
}
