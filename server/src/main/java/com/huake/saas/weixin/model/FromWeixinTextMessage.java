package com.huake.saas.weixin.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="xml")
public class FromWeixinTextMessage extends AbstractWeixinMessage {

	public FromWeixinTextMessage(){}
	
	public FromWeixinTextMessage(String from, String to, Date crtdate, String type, String msgId, String content){
		setFromUserName(from);
		setToUserName(to);
		setCreateTime(crtdate.getTime());
		super.setMessageType(type);
		this.messageId = msgId;
		this.content = content;
	}
	
	@Override
	public String getMessageEventType() {
		return AbstractWeixinMessage.TYPE_TEXT;
	}
	
	@XmlElement(name = "Content")
	private String content;
	
	@XmlElement(name = "MsgId")
	private String messageId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
