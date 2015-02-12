package com.huake.saas.access.service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import net.sf.json.JSONObject;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.huake.saas.access.entity.AccessLog;

/**
 * JMS请求日志消息生产
 * @author laidingqing jiajun-modify
 *
 */
public class AccessLogNotifyMessageProducer {
	private JmsTemplate jmsTemplate;
	private Destination notifyQueue;
	
	public void sendQueue( AccessLog log) {
		sendMessage(log, notifyQueue);
	}
	/**
	 * 使用jmsTemplate的send/MessageCreator()发送Map类型的消息.
	 */
	private void sendMessage( final AccessLog user, Destination notifyQueue) {
		System.out.println("SEND-------");
		
		//user 转换json
		final JSONObject json= JSONObject.fromObject(user);
		System.out.println("获取到的xml"+json.toString());
		
		jmsTemplate.send(notifyQueue, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				MapMessage message =  session.createMapMessage();

				message.setString("message", json.toString());
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
