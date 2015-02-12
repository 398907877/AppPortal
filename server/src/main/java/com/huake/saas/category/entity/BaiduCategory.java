package com.huake.saas.category.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.huake.base.IdEntity;
import com.huake.saas.baidu.entity.BaiduDevice;
/**
 * @author wujiajun
 * @time    2014-06-03
 * @description  用于推送的分类
 */
@Entity
@Table(name="baidu_category",indexes = {@Index(name="IDX_PUSHCATEGORY_PID",columnList="parentId")})
public class BaiduCategory extends IdEntity{

	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 分类描述
	 */
	private  String description;
	
	/**
	 * 父级编号
	 */
	private long parentId;
	
	/**
	 * 创建时间
	 */
	private Date crtDate;

	/**
	 *当前为多 ------ 对一   
	 */
	private BaiduCategory parentCategory;
	/**
	 * 当前为一-------对多
	 */
	private List<BaiduCategory> children;
	
	/**
	 * 设备，一个设备对应多个分类
	 * 多对一关系
	 */
	private BaiduDevice baiduDevice;
	@ManyToOne()
	@JoinColumn(name="BD")
	public BaiduDevice getBaiduDevice() {
		return baiduDevice;
	}
	public void setBaiduDevice(BaiduDevice baiduDevice) {
		this.baiduDevice = baiduDevice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	
	@ManyToOne
	@JoinColumn(name="pc")
	public BaiduCategory getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(BaiduCategory parentCategory) {
		this.parentCategory = parentCategory;
	}
	@OneToMany(mappedBy="parentCategory",cascade=CascadeType.ALL)
	public List<BaiduCategory> getChildren() {
		return children;
	}
	public void setChildren(List<BaiduCategory> children) {
		this.children = children;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
