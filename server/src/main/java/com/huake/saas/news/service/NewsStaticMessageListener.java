package com.huake.saas.news.service;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class NewsStaticMessageListener implements MessageListener{

	private static Logger logger = LoggerFactory.getLogger(NewsStaticMessageListener.class);
	
	@Autowired
	public NewsService newsService;
	
	@Override
	public void onMessage(Message message) {
		try {
			MapMessage mapMessage = (MapMessage) message;
			logger.debug("News Static is actived, nid:{}", mapMessage.getLong("nid"));
			
			String url = newsService.contentPublishingHtml(mapMessage.getLong("nid"));
			logger.debug(url);
		} catch (Exception e) {
			logger.error("处理消息时发生异常.", e);
		}
	}

}
