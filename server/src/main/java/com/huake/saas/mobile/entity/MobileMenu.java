package com.huake.saas.mobile.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 手机网站菜单定义
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_m_menu", indexes = {@Index(name="IDX_MM_UID", columnList="uid"), @Index(name="IDX_MM_PAR", columnList="parentId")})
public class MobileMenu extends IdEntity {

	
	private Long uid;
	
	/**
	 * 菜单名称
	 */
	private String title;
	
	/**
	 * 菜单图标
	 */
	private String icon;
	
	/**
	 * 目标地址
	 */
	private String target;
	
	/**
	 * 涉及二级菜单部份
	 */
	private Long parentId;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	
	
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
