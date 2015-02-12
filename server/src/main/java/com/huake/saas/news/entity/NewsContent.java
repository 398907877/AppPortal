package com.huake.saas.news.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huake.base.IdEntity;

/**
 * 新闻内容类
 * @author zhangjiayong
 *
 */
@Entity
@Table(name = "app_news_content", indexes = {@Index(name="IDX_NEWS_CONT_NID", columnList="newsId")})
public class NewsContent extends IdEntity{
	/**
	 * 新闻id
	 */
	@Column(name="news_id",updatable=false)
	private long newsId;
	
	/**
	 * 内容详细
	 */
	
	private String detail;
	
	/**
	 * 内容图片
	 */
	private String photo;
	/**
	 * 各种大小图片地址集合
	 */
	private Map<String,String> photos;
	
	@Transient
	public Map<String, String> getPhotos() {
		return photos;
	}

	public void setPhotos(Map<String, String> photos) {
		this.photos = photos;
	}

	/**
	 * 内容视频
	 */
	private String video;
	
	/**
	 * 内容在新闻里的排序
	 */
	private int seq;

	/**
	 * 图片标题
	 */
	@Column(name="photo_title")
	private String photoTitle;
	
	/**
	 * 视频标题
	 */
	@Column(name="video_title")
	private String videoTitle;
	
	
	public String getPhotoTitle() {
		return photoTitle;
	}

	public void setPhotoTitle(String photoTitle) {
		this.photoTitle = photoTitle;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public long getNewsId() {
		return newsId;
	}

	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}
	
	@Lob
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	
}
