package com.huake.saas.news;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huake.saas.BaseTransactionalTestCase;
import com.huake.saas.news.service.NewsStaticMessageProducer;


public class NewHtmlTest extends BaseTransactionalTestCase{

	@Autowired
	private  NewsStaticMessageProducer produce;
	
	@Test
	public void testAddTenancy(){
		produce.sendQueue(15l);
	}
}
