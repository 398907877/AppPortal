package com.huake.saas.auth.vrule.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huake.saas.auth.entity.Auth;
import com.huake.saas.auth.entity.NewsAuthCfg;
import com.huake.saas.auth.exception.AuthValidationException;
import com.huake.saas.auth.service.AuthService;
import com.huake.saas.auth.vrule.IVRule;
import com.huake.saas.news.entity.News;
import com.huake.saas.news.service.NewsService;


/**
 * 校验图文资讯每日发布的条数限制(如：每日不超过10条)
 * @author zjy
 */
@Component("newsDaylimit")
public class NewsDaylimitVRule implements IVRule<News,NewsAuthCfg>{

	@Autowired
	private NewsService newsService;
	
	@Autowired	
	private AuthService authService;
	
	@Override
	public void check(Long uid,News news,NewsAuthCfg useCfg) throws AuthValidationException{
		Auth auth = authService.findByUid(uid.toString());//查找该租户的授权
		//获取新闻资讯业务模块授权细则
		NewsAuthCfg authCfg = authService.findAuthCfgByAuthId(NewsAuthCfg.class,auth.getId(),NewsAuthCfg.AUTH_NEWS);
		if(authCfg == null){ //没有该授权 校验不通过
			throw new AuthValidationException("您没有新闻资讯业务服务包");
		}
		
		if(authCfg.getDayLimit() < useCfg.getDayLimit()+1){
			throw new AuthValidationException("您今天能添加的新闻资讯限额已达上限！");
		}
		
		useCfg.setDayLimit(useCfg.getDayLimit()+1);//校验通过 修改配置使用情况  在controler中更新到redis中
	}
}
