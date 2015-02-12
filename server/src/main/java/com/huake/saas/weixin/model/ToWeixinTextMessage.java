package com.huake.saas.weixin.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
public class ToWeixinTextMessage extends AbstractWeixinMessage {

	public ToWeixinTextMessage(){
		super.setMessageType(TYPE_TEXT);
	}
	
	@Override
	public String getMessageEventType() {
		
		return AbstractWeixinMessage.TYPE_TEXT;
	}
	
	@XmlElement(name = "Content")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
