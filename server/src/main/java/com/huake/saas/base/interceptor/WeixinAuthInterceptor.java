package com.huake.saas.base.interceptor;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;
import com.huake.saas.weixin.model.OAuthToken;
import com.huake.saas.weixin.model.WeixinCfg;
import com.huake.saas.weixin.model.WeixinUser;
import com.huake.saas.weixin.service.WeixinService;
import com.huake.saas.weixin.service.WeixinUserService;

public class WeixinAuthInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(WeixinAuthInterceptor.class);
	/**
	 * 引导用户授权 url  
	 */
	public static final String FETCH_AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=code&scope={2}&state=wx#wechat_redirect";
	/**
	 * 获取access_token openid url
	 */
	public static final String FETCH_TOKEN_OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
	/**
	 * 获取用户信息 url
	 */
	public static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";
	/**
	 * 应用授权作用域，snsapi_base 
	 * （不弹出授权页面，直接跳转，只能获取用户openid），
	 * snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地
	 * 。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 */
	public static final String SCOPE_USERINFO = "snsapi_userinfo";
	public static final String SCOPE_BASE = "snsapi_base";
	
	@Autowired
	private WeixinService weixinService;
	
	@Autowired
	private WeixinUserService userService;
	
	@Autowired
	private TenancyUserService tuserService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.debug("进入微信授权拦截。。。");
		String userAgent = request.getHeader("User-Agent");
		logger.debug(userAgent);
		String uid = request.getParameter("uid");
		WeixinCfg weixinCfg = weixinService.getWeixinCfg(Long.valueOf(uid));//当前租户微信配置
		
		if(userAgent.contains("MicroMessenger")){//判断是否为微信发起请求
			/**
			 * 获取session中的openid 不为null则放行
			 */
			if(request.getSession().getAttribute("openid") != null){
				return true;
			}
			
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			logger.debug("code:"+code);
			/**
			 * 判断返回code  用户是否拒绝授权
			 */
			if(code != null && !code.trim().equals("")&&!code.equals("authdeny")){
				/**
				 * 用户同意授权  根据code获取openid 和acces_token
				 */
				ResponseEntity<String> resEntity = restTemplate.getForEntity(FETCH_TOKEN_OPENID_URL, String.class, weixinCfg.getAppid(), weixinCfg.getSecret(), code);
				OAuthToken oauthToken = null;
				ObjectMapper  objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
				try {
					oauthToken = objectMapper.readValue(resEntity.getBody(), OAuthToken.class);
					logger.debug("access_token:"+oauthToken.getToken());
					logger.debug("openid:"+oauthToken.getOpenid());
					/**
					 * 根据openid在数据库查找 微信用户信息  有则通过 跳转到相应页面 
					 */
					WeixinUser userinfo = userService.getByOpenid(oauthToken.getOpenid());
					String openid = oauthToken.getOpenid();
					if(userinfo != null){
						TenancyUser tuser = tuserService.findByOpenid(openid);
						if(tuser == null){
							userService.saveWeixinUser(userinfo);//同步
						}
						/**
						 * 做登录操作 该操作 因MobileLoginController 里的登录逻辑 不同而不同
						 */
						doLogin(request, uid, openid,tuser);
						String url = request.getParameter("url");
						if(url == null){
							response.sendRedirect(url);
						}
						
						return true;
					}
					try {
						/**
						 * 数据库张查询不到微信用户信息   拉取用户信息
						 */
						ResponseEntity<String> resUser =  restTemplate.getForEntity(USER_INFO_URL, String.class,oauthToken.getToken(),oauthToken.getOpenid());
						WeixinUser user = objectMapper.readValue(resUser.getBody(), WeixinUser.class);
						user.setUid(Long.valueOf(uid));
						user.setCreatedAt(new Date());
						userService.saveWeixinUser(user);
						TenancyUser tuser = tuserService.findByOpenid(openid);
						/**
						 * 做登录操作 该操作 因MobileLoginController 里的登录逻辑 不同而不同
						 */
						doLogin(request, uid, openid,tuser);
						String url = request.getParameter("url");
						if(url == null){
							response.sendRedirect(url);
						}
						return true;
					} catch (Exception e) {
						logger.debug("获取用户失败",e.getMessage());
						/**
						 * 获取用户信息失败   第一次进入 引导用户进入授权页面
						 */
						String url = MessageFormat.format(FETCH_AUTH_URL, weixinCfg.getAppid(), URLEncoder.encode(request.getRequestURL()+"?"+request.getQueryString(), "UTF-8"), SCOPE_USERINFO);
						response.sendRedirect(url);
						return false;
					}
				} catch (Exception e) {
					logger.debug("获取access_token and openid 失败",e.getMessage());
				}
			}else{
				if(state == null){
					//TODO 获取所有请求参数  对请求参数编码  
					String url = MessageFormat.format(FETCH_AUTH_URL, weixinCfg.getAppid(), URLEncoder.encode(request.getRequestURL()+"?"+request.getQueryString(), "UTF-8"), SCOPE_BASE);
					response.sendRedirect(url);
					return false;
					
				}else{
					//TODO 不授权是否一定跳转到首页 或者直接return false
					//request.getRequestDispatcher("/m/index").forward(request, response);
					return false;
				}
			}
			
		}else{
			// TODO 非微信内置浏览器 做登录拦截。。。
			//request.getRequestDispatcher("/m/index").forward(request, response);
			return true;
		}
		return true;
	}

	/**
	 *
	 * 做登录操作 该操作 因MobileLoginController 里的登录逻辑 不同而不同
	 * @param request
	 * @param uid
	 * @param openid
	 */
	private void doLogin(HttpServletRequest request, String uid, String openid,TenancyUser tuser) {
		request.getSession().setAttribute("openid", openid);//保存openid进session 为公用部分
		/**
		 * 登录操作
		 */
		request.getSession().setAttribute("login", "true");
		request.getSession().setAttribute("uid", uid);
		request.getSession().setAttribute("memberId", tuser.getId());
	}
}
