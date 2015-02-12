package com.huake.saas.weixin.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 来自微信的图片信息报文
 * @author laidingqing
 *
 */
@XmlRootElement(name="xml")
public class FromWeixinImageMessage extends AbstractWeixinMessage {

public FromWeixinImageMessage(){}
	
	public FromWeixinImageMessage(String from, String to, Date crtdate, String type, String msgId, String picUrl){
		setFromUserName(from);
		setToUserName(to);
		setCreateTime(crtdate.getTime());
		super.setMessageType(type);
		this.messageId = msgId;
		this.picUrl = picUrl;
	}
	@Override
	public String getMessageEventType() {
		
		return AbstractWeixinMessage.TYPE_IMAGE;
	}
	
	@XmlElement(name = "PicUrl")
	private String picUrl;
	
	@XmlElement(name = "MsgId")
	private String messageId;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
