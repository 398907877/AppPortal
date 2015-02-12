package com.huake.saas.activity.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 活动报名及感兴趣实体
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_event_joins",indexes = {@Index(name="IDX_ENENT_UID",columnList="uid")})
public class EventJoin extends IdEntity {
	
	/**
	 * 参加类型
	 */
	public final static String TYPE_JOIN = "1";
	/**
	 * 感兴趣类型
	 */
	public final static String TYPE_INTEREST = "2";
	/**
	 * 所属租户编号
	 */
	private long uid;
	
	/**
	 * 是否会员，是保存会员编号
	 */
	private long mid;
	
	/**
	 * 活动编号
	 */
	private long eventId;
	
	/**
	 * 报名姓名
	 */
	private String name;
	/**
	 * 报名电话
	 */
	private String tel;
	/**
	 * 单位名称
	 */
	private String companyName;
	/**
	 * 感兴趣or参加
	 */
	private String type;
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public long getMid() {
		return mid;
	}
	public void setMid(long mid) {
		this.mid = mid;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
