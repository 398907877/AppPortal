package com.huake.saas.news.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 新闻资讯管理
 * @author zhongshuhui
 */
@Entity
@Table(name = "app_news_pictures", indexes = {@Index(name="IDX_NEWS_PIC_NID", columnList="newsId")})
public class NewsPictures{
	
	
	public static final String PARMS_NEWS_PICTURES= "pictures";
	/**
	 * 键：图片
	 */
	public static final String NAME_CATEGORY_PIC="pic";
	/**
	 * 缩略图
	 */
	public static final String NAME_CATEGORY_THUMBNAIL="thumbnail";
	
	
	private Long pid;
	
	
     
	/**
	 * 新闻Id
	 */
	@Column(name = "news_id", updatable = false)
	private Integer newsId;
	
	
	private Date crtDate;
	
	
	/**
	 * 图片类型
	 */
	private String category;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
	
	/**
	 * 图片地址
	 */
	private String url;





	public Integer getNewsId() {
		return newsId;
	}


	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	
	
}
