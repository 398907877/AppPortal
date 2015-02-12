package com.huake.saas.redis.repository.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.huake.saas.auth.entity.NewsAuthCfg;
import com.huake.saas.news.entity.News;
import com.huake.saas.news.entity.NewsContent;
import com.huake.saas.news.service.NewsService;
import com.huake.saas.redis.RedisKeyUtil;
import com.huake.saas.redis.repository.AuthCfgRepository;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;

@Service("newsAuthCfgRepository")
public class NewsAuthCfgRepositoryImpl implements AuthCfgRepository<NewsAuthCfg> {

	@Autowired 
	private NewsService newsService;
	
	@Autowired
	private TenancyService tenancyService;
	
	@Autowired
	private RedisTemplate<String, Map<String,String>> redisTemplate;
	
	@Override
	public void putAuthCfg(String uid) {
		String key = getKey(uid);//对应租户 新闻权限配置节点
		
		Map<String,String> map=new HashMap<String,String>();
		map.put(RedisKeyUtil.NEWS_AUTH_CFG_DAYLIMIT,"0");//添加时，存放每日条数限额
		map.put(RedisKeyUtil.NEWS_AUTH_CFG_MONTHVIDEOLIMIT,"0");//存放 每月视频限额
		
		redisTemplate.opsForHash().putAll(key, map);
	}

	@Override
	public void removeAuthCfg(String uid) {
		String key = getKey(uid);
		
		redisTemplate.delete(key);
	}

	@Override
	public void updateAuthCfg(NewsAuthCfg authCfg) {
		String key = getKey(authCfg.getUid());
		
		Map<String,String> map=new HashMap<String,String>();
		map.put(RedisKeyUtil.NEWS_AUTH_CFG_DAYLIMIT,String.valueOf(authCfg.getDayLimit()));
		map.put(RedisKeyUtil.NEWS_AUTH_CFG_MONTHVIDEOLIMIT,String.valueOf(authCfg.getMonthVideoLimit()));
		
		redisTemplate.opsForHash().putAll(key, map);
		
	}

	@Override
	public NewsAuthCfg getAuthCfg(String uid) {
		String key = getKey(uid);
		
		Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
		NewsAuthCfg authCfg = new NewsAuthCfg();
		authCfg.setUid(uid.toString());
		if(map.isEmpty()){
			authCfg.setDayLimit(0);
			authCfg.setMonthVideoLimit(0);
			return authCfg;
		}
		
		authCfg.setDayLimit(Integer.parseInt((String)map.get(RedisKeyUtil.NEWS_AUTH_CFG_DAYLIMIT)));
		authCfg.setMonthVideoLimit(Integer.parseInt((String)map.get(RedisKeyUtil.NEWS_AUTH_CFG_MONTHVIDEOLIMIT)));
		
		return authCfg;
	}

	private String getKey(String uid) {
		StringBuilder sb = new StringBuilder();
		sb.append(RedisKeyUtil.CACHE_ROOT_NAME).append(RedisKeyUtil.CACHE_KEY_SEPARATOR);
		sb.append(RedisKeyUtil.TENANCY_ROOT).append(RedisKeyUtil.CACHE_KEY_SEPARATOR);
		sb.append(uid).append(RedisKeyUtil.CACHE_KEY_SEPARATOR).append(RedisKeyUtil.NEWS_AUTH_CFG);//对应租户 新闻权限配置节点
		return sb.toString();
	}

	@Override
	public void putCurrentAuthCfg() {
		Date now = DateUtils.truncate(new Date(), Calendar.DATE);
		for(Tenancy t : tenancyService.getAllTenancies()){//遍历所有租户
			NewsAuthCfg authCfg = new NewsAuthCfg();//当前新闻配置项 
			authCfg.setUid(String.valueOf(t.getUid()));
			List<News> newss = newsService.findNews(
					t.getUid(),DateUtils.round(now, Calendar.MONTH),DateUtils.ceiling(now, Calendar.MONTH));//查找该租户当月添加的所以新闻资讯
			//当前租户当天已添加的新闻资讯数目
			int dayLimit = newsService.findNews(t.getUid(),now,DateUtils.addDays(now, 1)).size();
			int total = 0;//统计当前租户当月已添加的视屏总数
			for(News ne : newss){
				List<NewsContent> contents = ne.getContents();
				for(NewsContent content : contents){
					if(content.getVideo() != null && !content.getVideo().trim().equals("")){
						total += 1;
					}
				}
			}
			authCfg.setDayLimit(dayLimit);
			authCfg.setMonthVideoLimit(total);
			updateAuthCfg(authCfg);//保存到redis中
		}
	}

	@Override
	public void cleanAuthCfg(String uid) {
		Date now = new Date();
		boolean isMonthBegin = DateUtils.isSameDay(now, DateUtils.round(now, Calendar.MONTH));
		NewsAuthCfg oldCfg = getAuthCfg(uid);
		oldCfg.setDayLimit(0);
		if(isMonthBegin){
			oldCfg.setMonthVideoLimit(0);
		}
		updateAuthCfg(oldCfg);
	}
}
