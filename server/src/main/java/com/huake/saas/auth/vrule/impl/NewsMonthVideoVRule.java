package com.huake.saas.auth.vrule.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huake.saas.auth.entity.Auth;
import com.huake.saas.auth.entity.NewsAuthCfg;
import com.huake.saas.auth.exception.AuthValidationException;
import com.huake.saas.auth.service.AuthService;
import com.huake.saas.auth.vrule.IVRule;
import com.huake.saas.news.entity.News;
import com.huake.saas.news.entity.NewsContent;
import com.huake.saas.news.service.NewsService;

@Component("newsMonthVideo")
public class NewsMonthVideoVRule implements IVRule<News,NewsAuthCfg>{

	@Autowired
	private NewsService newsService;
	
	@Autowired	
	private AuthService authService;
	
	@Override
	public void check(Long uid, News news,NewsAuthCfg useCfg) throws AuthValidationException{
		Auth auth = authService.findByUid(uid.toString());//查找该租户的授权
		//获取新闻资讯业务模块授权细则
		NewsAuthCfg authCfg = authService.findAuthCfgByAuthId(NewsAuthCfg.class,auth.getId(),NewsAuthCfg.AUTH_NEWS);
		if(authCfg == null){ //没有该授权 校验不通过
			throw new AuthValidationException("您没有新闻资讯业务服务包");
		}
		int current = 0;//当前新闻资讯的视频数
		for(NewsContent content : news.getContents()){
			if(content.getVideo() != null && !content.getVideo().trim().equals("")){
				current += 1;
			}
		}
		
		if(news.getId() == null){//当前 为添加操作时
			if(authCfg.getMonthVideoLimit() < useCfg.getMonthVideoLimit()+current){
				throw new AuthValidationException("您本月的可添加视频限额已达上限！");
			}
		}else{//修改操作
			News oldNews = newsService.getNews(news.getId());
			oldNews.setContents(newsService.findNewsContentByNewsId(oldNews.getId()));
			int oldCount = 0;//更新之前 该新闻视频的条数
			for(NewsContent content : oldNews.getContents()){
				if(content.getVideo() != null && !content.getVideo().trim().equals("")){
					oldCount += 1;
				}
			}
			if(oldCount > current){
				return;
			}
			if(authCfg.getMonthVideoLimit() < useCfg.getMonthVideoLimit()+current-oldCount){
				throw new AuthValidationException("您本月的可添加视频限额已达上限！");
			}
			//校验通过 修改配置实用情况 在controler中更新到redis中
			useCfg.setMonthVideoLimit(useCfg.getMonthVideoLimit()+current-oldCount);
		}
	}
}
