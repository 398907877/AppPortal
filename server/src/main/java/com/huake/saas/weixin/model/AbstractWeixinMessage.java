package com.huake.saas.weixin.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 来自微信报文抽像公共类
 * @author laidingqing
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractWeixinMessage {
	public static final String TYPE_TEXT = "text";
	public static final String TYPE_IMAGE = "image";
	public static final String TYPE_LOCATION = "location";
	public static final String TYPE_LINK = "link";
	public static final String TYPE_EVENT = "event";
	
	public static final String TYPE_NEWS = "news";

	@XmlElement(name = "ToUserName")
	private String toUserName;
	
	@XmlElement(name = "FromUserName")
	private String fromUserName;
	
	// TODO 这里要格式化为整型。接口约定
	@XmlElement(name = "CreateTime")
	private long createTime;
	
	@XmlElement(name = "MsgType")
	private String messageType;
	
	public abstract String getMessageEventType();

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
}
