package com.huake.saas.weixin.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 来自微信链接消息报文
 * @author laidingqing
 *
 */
@XmlRootElement(name="xml")
public class FromWeixinLinkMessage extends AbstractWeixinMessage {

public FromWeixinLinkMessage(){}
	
	public FromWeixinLinkMessage(String from, String to, Date crtdate, String type, String msgId,String title, String description, String url){
		setFromUserName(from);
		setToUserName(to);
		setCreateTime(crtdate.getTime());
		super.setMessageType(type);
		this.messageId = msgId;
		this.title = title;
		this.description = description;
		this.url = url;
	}
	
	@Override
	public String getMessageEventType() {
		
		return AbstractWeixinMessage.TYPE_LINK;
	}
	
	@XmlElement(name = "Title")
	private String title;
	
	@XmlElement(name = "Description")
	private String description;
	
	@XmlElement(name = "Url")
	private String url;
	
	@XmlElement(name = "MsgId")
	private String messageId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

}
