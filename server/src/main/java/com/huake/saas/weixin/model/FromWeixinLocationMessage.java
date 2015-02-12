package com.huake.saas.weixin.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 来自微信的位置信息报文
 * @author laidingqing
 */
@XmlRootElement(name="xml")
public class FromWeixinLocationMessage extends AbstractWeixinMessage {
	
	public FromWeixinLocationMessage(){}
	
	public FromWeixinLocationMessage(String from, String to, Date crtdate, String type, String msgId, BigDecimal longitude, BigDecimal latitude, BigDecimal scale,String label){
		setFromUserName(from);
		setToUserName(to);
		setCreateTime(crtdate.getTime());
		super.setMessageType(type);
		this.messageId = msgId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.scale = scale;
		this.label = label;
	}
	@Override
	public String getMessageEventType() {
		return AbstractWeixinMessage.TYPE_LOCATION;
	}
	
	@XmlElement(name = "Location_X")
	private BigDecimal longitude;
	
	@XmlElement(name = "Location_Y")
	private BigDecimal latitude;
	
	@XmlElement(name = "Scale")
	private BigDecimal scale;
	
	@XmlElement(name = "Label")
	private String label;
	
	@XmlElement(name = "MsgId")
	private String messageId;

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

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

}
