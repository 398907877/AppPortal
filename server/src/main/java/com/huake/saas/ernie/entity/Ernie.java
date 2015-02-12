package com.huake.saas.ernie.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huake.base.IdEntity;

/**
 * 摇奖实体，说明活动，等
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_ernies", indexes = {@Index(name="IDX_ERNIE_UID", columnList="uid"), @Index(name="IDX_GROUPON_STATE", columnList="status")})
public class Ernie extends IdEntity {
	
	/**
	 * 博饼
	 */
	public static final String CATEGORY_VALID="3";

	/**
	 * 租户编号
	 */
	private Long uid;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 简要说明
	 */
	private String description;
	/**
	 * 详细内容
	 */
	private String content;
	
	/**
	 * 项目总数
	 */
	private Integer itemCount;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 开始时间。
	 */
	private Date startDate;
	
	private Date endDate;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新时间
	 */
	private Date updatedAt;
	/**
	 * 抽奖次数
	 */
	private Integer time;
	/**
	 * 营销种类（抽奖、砸蛋、博饼）
	 */
	private String category;
	
	/**
	 * 缩略图
	 */
	private String image;
	
	
	/**
	 * 中奖概率
	 */
	private BigDecimal probability;
	
	public BigDecimal getProbability() {
		return probability;
	}
	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}
	private List<ErnieItem> items;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Lob
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Lob
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	/**
	 * 抽奖项
	 * @return
	 */
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ernie")
	public List<ErnieItem> getItems() {
		return items;
	}
	public void setItems(List<ErnieItem> items) {
		this.items = items;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
