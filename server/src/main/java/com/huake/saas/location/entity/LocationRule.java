package com.huake.saas.location.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 位置服务登记，注意，一个租户只能有一项规则
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_location_rules", indexes = {@Index(name="IDX_LOCATION_UID", columnList="uid"), @Index(name="IDX_LOCATION_STATE", columnList="status")})
public class LocationRule extends IdEntity {

	private Long uid;
	
	/**
	 * 对应biz.xml中的locationRule
	 */
	private String ruleName;
	
	private Date createdAt;
	
	private String status;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
