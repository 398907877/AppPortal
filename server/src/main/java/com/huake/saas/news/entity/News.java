package com.huake.saas.news.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huake.saas.base.entity.BaseEntity;


/**
 * 新闻资讯管理
 * @author zhongshuhui
 */
@Entity
@Table(name = "app_news",  indexes = {@Index(name="IDX_NEWS_UID", columnList="uid"), 
									@Index(name="IDX_NEWS_CAT", columnList="categoryId"),
									@Index(name="IDX_NEWS_STATUS", columnList="status"),
									@Index(name="IDX_NEWS_BANNER",columnList="banner")})
public class News extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String PARMS_NEWSS = "newss";
	
	public static final String PARMS_NEWS = "news";
	
	public static final String IS_PUBLILSH = "1";
	
	public static final String NOT_PUBLILSH = "0";
	
	public static final String IS_BANNER = "1";
	
	public static final String NOT_BANNER = "0";
	/**
	 * 分类  关联category
	 */
	private Integer categoryId;
	
	/**
	 * 详细内容
	 */
	private String detail;
	
	/**
	 * 介绍
	 */
	private String intro;

	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 图文资讯详情url
	 */
	private String url;
	
	
	private String publish;
	
	/**
	 * 图片
	 */
	private String pic;
	
	/**
	 * 新闻内容
	 */
	private List<NewsContent> contents;
	
	/**
	 * 置顶序号
	 */
	private Long stick;
	
	/**
	 * 
	 * 是否banner
	 */
	private String banner;
	
	@Transient
	public List<NewsContent> getContents() {
		return contents;
	}

	public void setContents(List<NewsContent> contents) {
		this.contents = contents;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	@JsonIgnore
	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@JsonIgnore
	public Integer getCategoryId() {
		return categoryId;
	}
	@Lob
	@JsonIgnore
	public String getDetail() {
		return detail;
	}

	@Lob
	@NotBlank
	public String getIntro() {
		return intro;
	}

	@NotBlank
	public String getTitle() {
		return title;
	}


	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	

	
	public void setTitle(String title) {
		this.title = title;
	}

	public Long getStick() {
		return stick;
	}

	public void setStick(Long stick) {
		this.stick = stick;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}
}
