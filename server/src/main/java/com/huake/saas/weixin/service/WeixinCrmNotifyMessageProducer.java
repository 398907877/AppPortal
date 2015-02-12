package com.huake.saas.weixin.service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.huake.saas.weixin.model.WeixinUser;

public class WeixinCrmNotifyMessageProducer {
	
	private JmsTemplate jmsTemplate;
	private Destination notifyQueue;

	public void sendQueue(final WeixinUser user) {
		sendMessage(user, notifyQueue);
	}

	/**
	 * 使用jmsTemplate的send/MessageCreator()发送Map类型的消息并在Message中附加属性用于消息过滤.
	 */
	private void sendMessage(final WeixinUser user, Destination destination) {
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {

				MapMessage message = session.createMapMessage();
				message.setString("openId", user.getOpenid());
				message.setLong("uid", user.getUid());
				return message;
			}
		});
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setNotifyQueue(Destination notifyQueue) {
		this.notifyQueue = notifyQueue;
	}
}
