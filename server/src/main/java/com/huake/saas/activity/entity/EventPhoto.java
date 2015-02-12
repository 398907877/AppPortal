package com.huake.saas.activity.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 活动图片表，使用统一的图像处理服务及存储
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_event_photos",indexes = {@Index(name="IDX_ENENT_EVENTID",columnList="eventId")})
public class EventPhoto extends IdEntity {

	private Long eventId;

	private String filePath;
	
	/**
	 * 图像类型，如origin:原始图, thumbnail:缩略图, small:小图，等
	 */
	private String imgType;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
}
