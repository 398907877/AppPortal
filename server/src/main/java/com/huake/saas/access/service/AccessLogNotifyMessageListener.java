package com.huake.saas.access.service;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huake.saas.access.entity.AccessLog;

/**
 * 请求日志处理异步监听
 * @author laidingqing   jiajun-modify
 *
 */
public class AccessLogNotifyMessageListener implements MessageListener {

	private static Logger logger = LoggerFactory.getLogger(AccessLogNotifyMessageListener.class);
	
	@Autowired
	private AccessLogService accessLogService;
	
	@Override
	public void onMessage(Message message) {
		
		System.out.println("LOG---------------------::::");
		try {
		 	  MapMessage mapmes = (MapMessage)message;
		 	  String mes=(String) mapmes.getObject("message");
		 	  
		 	 System.out.println("-----------------:::::::::::"+mes);
		 	 JSONObject logjson =  JSONObject.fromObject(mes);
		 	  
		 	 AccessLog   accessLog = (AccessLog) JSONObject.toBean(logjson, AccessLog.class);
			
			
			
			accessLogService.save(accessLog);
		} catch (Exception e) {
			logger.error("处理消息时发生异常.", e);
		}
	}

}
