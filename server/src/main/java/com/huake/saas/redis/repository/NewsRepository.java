package com.huake.saas.redis.repository;

public interface NewsRepository {
	/**
	 * 存放新闻置顶最大值
	 * @param newsTop 置顶最大值
	 * @param uid 租户id
	 */
	public void putNewsTop(Long newsTop,String uid);
	
	/**
	 * 
	 * @param uid 租户id
	 * @return
	 */
	public long getNewsTop(String uid);
	
	public void putCurrentNewsTop();
}
