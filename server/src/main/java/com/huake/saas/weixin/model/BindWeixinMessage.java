package com.huake.saas.weixin.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD) 
public class BindWeixinMessage {
	
	@XmlElement(name = "ToUserName")
	private String toUserName;
	
	@XmlElement(name = "FromUserName")
	private String fromUserName;
	
	@XmlElement(name = "CreateTime")
	private Date createTime;
	
	@XmlElement(name = "MsgType")
	private String messageType;
	
	@XmlElement(name = "Event")
	private String event;
	
	@XmlElement(name = "EventKey")
	private String eventKey;
	
	@XmlElement(name = "PicUrl")
	private String picUrl;
	
	@XmlElement(name = "Title")
	private String title;
	
	@XmlElement(name = "Description")
	private String description;
	
	@XmlElement(name = "Url")
	private String url;
	
	
	@XmlElement(name = "Location_X")
	private BigDecimal longitude;
	
	@XmlElement(name = "Location_Y")
	private BigDecimal latitude;
	
	@XmlElement(name = "Scale")
	private BigDecimal scale;
	
	@XmlElement(name = "Label")
	private String label;
	
	@XmlElement(name = "Content")
	private String content;
	
	@XmlElement(name = "MsgId")
	private String messageId;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

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

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getScale() {
		return scale;
	}

	public void setScale(BigDecimal scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

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
