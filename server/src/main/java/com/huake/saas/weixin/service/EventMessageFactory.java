package com.huake.saas.weixin.service;

import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huake.saas.access.entity.AccessLog;
import com.huake.saas.access.service.AccessLogNotifyMessageProducer;
import com.huake.saas.base.Constants;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.AbstractWeixinMessage;
import com.huake.saas.weixin.model.FromWeixinEventMessage;
import com.huake.saas.weixin.model.FromWeixinImageMessage;
import com.huake.saas.weixin.model.FromWeixinLinkMessage;
import com.huake.saas.weixin.model.FromWeixinLocationMessage;
import com.huake.saas.weixin.model.FromWeixinTextMessage;

/**
 * 微信消息处理
 * @author laidingqing jiajun
 *
 */
@Service("eventMessageFactory")
public class EventMessageFactory {
	
	private final Logger log = LoggerFactory.getLogger(EventMessageFactory.class);

	@Resource(name = "eventMessageHandles")
	private Map<String, ?> eventMessageHandles;
	
	@Autowired
	private AccessLogNotifyMessageProducer logProducer;
	
	public AbstractWeixinMessage render(AbstractWeixinMessage message) {
		AccessLog accessLog = new AccessLog();
	
		
		
		try{
			//应该根据不一样的 message type  去 实例化 不一样的 对象
			JAXBContext jaxb =null;
			
			if("text".equals(message.getMessageType())){
		      	
		    	 jaxb = JAXBContext.newInstance(FromWeixinTextMessage.class);  
						
			}else if("image".equals(message.getMessageType())){
				
				 jaxb = JAXBContext.newInstance(FromWeixinImageMessage.class);  
						
			}else if("location".equals(message.getMessageType())){
			     
			 	 jaxb = JAXBContext.newInstance(FromWeixinLocationMessage.class);  
						
			}else if("link".equals(message.getMessageType())){
		     	
		    	 jaxb = JAXBContext.newInstance(FromWeixinLinkMessage.class);  
			}else if("event".equals(message.getMessageType())){
	   	        
	   	 	     jaxb = JAXBContext.newInstance(FromWeixinEventMessage.class);  
						
			}else{
				 jaxb = JAXBContext.newInstance(FromWeixinEventMessage.class);  
			}
			
        	Marshaller marshaller = jaxb.createMarshaller();  
        	StringWriter sw = new StringWriter();
        	marshaller.marshal(message, sw);
        	
        	accessLog.setBody(sw.toString());
        	
        	
		}catch(Exception e){
			log.error("编组消息异常:", e);
		}
		accessLog.setChannelName(Constants.CHANNEL_NAME_WEIXIN);
		accessLog.setReqAt(new Date());
		accessLog.setPath(message.getMessageType());	
		accessLog.setRemoteHost(WebContext.getInstance().getRequest().getRemoteHost());
		accessLog.setClient(WebContext.getInstance().getRequest().getHeader("user-agent"));
		try{
			logProducer.sendQueue(accessLog);
		}catch(Exception e){
			log.error("处理异步请求消息异常:", e);
		}
		return getHandle(message.getMessageEventType()).render(message);
	}

	private WeixinMessageHandle getHandle(String key){
		return (WeixinMessageHandle)eventMessageHandles.get(key);
	}
	
}
