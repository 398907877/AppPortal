package com.huake.saas.news;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huake.saas.BaseTransactionalTestCase;
import com.huake.saas.news.service.NewsWeixinTarget;

public class TestToWeixinNewsMessage extends BaseTransactionalTestCase{

	@Autowired 
	private NewsWeixinTarget newsWeixinTarget;
	
	@Test
	public void testGetNewsByCategory(){
		newsWeixinTarget.getNewsByCategory("20140417122551", "2", "5");
	}
	
	@Test
	public void testGetNews(){
		
		newsWeixinTarget.getNews("20140417122551", "5");
	}
}
