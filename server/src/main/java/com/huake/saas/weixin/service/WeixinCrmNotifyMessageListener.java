package com.huake.saas.weixin.service;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huake.saas.weixin.model.WeixinUser;

public class WeixinCrmNotifyMessageListener implements MessageListener {

	private static Logger logger = LoggerFactory.getLogger(WeixinCrmNotifyMessageListener.class);
	
	@Autowired
	private WeixinUserService userService;

	/**
	 * MessageListener回调函数.
	 */
	@Override
	public void onMessage(Message message) {
		try {
			MapMessage mapMessage = (MapMessage) message;
			logger.info("WeixinCrm is actived, OpenId:{}, Uid:{}", mapMessage.getString("openId"), mapMessage.getLong("uid"));
			WeixinUser user = new WeixinUser();
			user.setOpenid(mapMessage.getString("openId"));
			user.setUid(mapMessage.getLong("uid"));
			userService.subcribe(user);
		} catch (Exception e) {
			logger.error("处理消息时发生异常.", e);
		}
	}
}
