package com.huake.saas.redis.repository.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.huake.saas.news.service.NewsService;
import com.huake.saas.redis.RedisKeyUtil;
import com.huake.saas.redis.repository.NewsRepository;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;

@Service
public class NewsRepositoryImpl implements NewsRepository{

	@Autowired 
	private NewsService newsService;
	
	@Autowired
	private TenancyService tenancyService;
	
	@Autowired
	private RedisTemplate<String, Map<String,String>> redisTemplate;
	
	@Override
	public void putNewsTop(Long newsTop, String uid) {
		String key = getKey(uid);
		Map<String,String> map=new HashMap<String,String>();
		map.put(RedisKeyUtil.NEWS_TOP, newsTop.toString());
		redisTemplate.opsForHash().putAll(key, map);
	}

	@Override
	public long getNewsTop(String uid) {
		String key = getKey(uid);
		Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
		
		return Long.valueOf(map.get(RedisKeyUtil.NEWS_TOP).toString());
	}

	private String getKey(String uid) {
		StringBuilder sb = new StringBuilder();
		sb.append(RedisKeyUtil.CACHE_ROOT_NAME).append(RedisKeyUtil.CACHE_KEY_SEPARATOR);
		sb.append(RedisKeyUtil.TENANCY_ROOT).append(RedisKeyUtil.CACHE_KEY_SEPARATOR);
		sb.append(uid).append(RedisKeyUtil.CACHE_KEY_SEPARATOR).append(RedisKeyUtil.NEWS);
		return sb.toString();
	}

	@Override
	public void putCurrentNewsTop() {
		for(Tenancy t : tenancyService.getAllTenancies()){
			String uid = String.valueOf(t.getUid());
			Long topNum = newsService.findTopnum(uid);
			putNewsTop(topNum, uid);
		}
	}
}
