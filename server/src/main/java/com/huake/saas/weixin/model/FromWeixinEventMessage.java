package com.huake.saas.weixin.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
public class FromWeixinEventMessage extends AbstractWeixinMessage {

	public FromWeixinEventMessage(){
		
	}
	
	public FromWeixinEventMessage(String from, String to, Date crtdate, String type, String msgId, String event, String eventKey){
		setFromUserName(from);
		setToUserName(to);
		super.setMessageType(type);
		this.eventKey = eventKey;
		this.event = event;
	}
	
	@XmlElement(name = "Event")
	private String event;
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	@Override
	public String getMessageEventType() {
		return AbstractWeixinMessage.TYPE_EVENT;
	}
	
	@XmlElement(name = "EventKey")
	private String eventKey;
	

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("from:").append(super.getFromUserName());
		sb.append("eventKey:").append(this.eventKey);
		return sb.toString();
	}

}
