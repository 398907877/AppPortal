package com.huake.saas.activity.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huake.base.IdEntity;

/**
 * 活动，租户发起的活动，与图文资讯不同，活动可以进行报名。
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_events",indexes = {@Index(name="IDX_ENENT_UID",columnList="uid")})
public class Event extends IdEntity {
	/**
	 * 有效
	 */
	public static final String STATUS_VALID = "1";
	/**
	 * 无效状态
	 */
	public static final String STATUS_INVALID = "0";
	/**
	 * 所属租户编号
	 */
	private long uid;
	
	/**
	 * 活动标题
	 */
	private String title;
	
	/**
	 * 活动详情
	 */
	private String info;
	
	/**
	 * 感兴趣人数
	 */
	private int wishCount;
	
	/**
	 * 参加人数
	 */
	private int doCount;
	
	/**
	 * 海报or缩略图 TODO 限定最小300px.
	 */
	private String poster;
	
	/**
	 * 起始时间
	 */
	private Date startDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	/**
	 * 创建时间
	 */
	private Date crtDate;
	/**
	 * 更新时间
	 */
	private Date uptDate;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 图文资讯详情url
	 */
	private String url;
	
	/**
	 * 活动地点
	 */
	private String address;
	/**
	 * 联系人
	 */
	private String linkman;
	

	/**
	 * 联系电话
	 */
	private String tel;
	
	/**
	 * 活动参与信息
	 */
	private List<EventJoin> eventJoins;
	
	/**
	 * 是否已参加活动
	 */
	private String singUp;
	@Transient
	public String getSingUp() {
		return singUp;
	}
	public void setSingUp(String singUp) {
		this.singUp = singUp;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Lob
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getWishCount() {
		return wishCount;
	}
	public void setWishCount(int wishCount) {
		this.wishCount = wishCount;
	}
	public int getDoCount() {
		return doCount;
	}
	public void setDoCount(int doCount) {
		this.doCount = doCount;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getUptDate() {
		return uptDate;
	}
	public void setUptDate(Date uptDate) {
		this.uptDate = uptDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column(name = "status", length = 1, nullable = false)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Transient
	public List<EventJoin> getEventJoins() {
		return eventJoins;
	}
	
	public void setEventJoins(List<EventJoin> eventJoins) {
		this.eventJoins = eventJoins;
	}
	
}
