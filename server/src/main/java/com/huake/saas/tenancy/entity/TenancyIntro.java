package com.huake.saas.tenancy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.huake.base.IdEntity;



/**
 * 企业介绍实体，用富文本存储
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_tenancy_intro")
public class TenancyIntro extends IdEntity {

	/**
	 * 所属租户编号
	 */
	private long uid;
	
	/**
	 * 富文本内容介绍
	 */
	private String content;
	
	/**
	 * 创建时间
	 */
	private Date crtDate;
	
	/**
	 * 更新时间
	 */
	private Date uptDate;

	@Column(name = "uid", nullable = false)
	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	@Column(name = "content", nullable = true)
	@Lob
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "created_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	@Column(name = "updated_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUptDate() {
		return uptDate;
	}

	public void setUptDate(Date uptDate) {
		this.uptDate = uptDate;
	}
	
	
}
