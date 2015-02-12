package com.huake.saas.base.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.huake.dict.entity.Dictionary;
import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.account.service.ShiroDbRealm.ShiroUser;
import com.huake.saas.auth.entity.Auth;
import com.huake.saas.auth.entity.AuthCfg;
import com.huake.saas.auth.service.AuthService;
import com.huake.saas.dictionary.service.DictionaryService;

/**
 * 用于服务包下业务控制授权检查，需要定制Spring MVC mapping URL.
 * @author laidingqing
 *
 */
public class BizAuthHandlerInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(BizAuthHandlerInterceptor.class);
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired 
	private AccountService accountService;
	
	@Autowired	
	private AuthService authService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();//获取当前用户
		User current = accountService.getUser(user.id);
		if(User.USER_ROLE_SYS_ADMIN.equals(current.getRoles())){//如果是平台管理员 所有功能放行
			return true;
		}
		Long uid = user.getUid();//获取租户id
		Auth auth = authService.findByUid(uid.toString());//查找该租户的授权
		String requestURI = request.getRequestURI();//获取当前的uri
		List<Dictionary> dicts = dictionaryService.findByClassName("BIZ_CODE");//获取业务模块列表
		for(Dictionary dict : dicts){
			if(requestURI.contains(dict.getKey())){
				AuthCfg authCfg = authService.findAuthCfgByAuthId(auth.getId(), dict.getKey());//根据授权id查询授权配置
				if(authCfg == null){//找不到改授权配置则不能访问该业务模块
					logger.debug("用户"+user.id+"试图访问"+dict.getKey()+"业务模块");
					request.setAttribute("message", "您没有权限访问"+dict.getValue()+",请开通.");
					request.getRequestDispatcher("/mgr/index").forward(request, response);;
					return false;
				}
				return true;
			}
		}
		return true;
	}
}
