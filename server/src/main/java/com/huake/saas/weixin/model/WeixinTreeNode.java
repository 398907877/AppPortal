package com.huake.saas.weixin.model;

import javax.persistence.Entity;
import javax.persistence.Table;


import com.huake.base.IdEntity;



@Entity
@Table(name = "app_weixin_treenode")
public class WeixinTreeNode  extends IdEntity{
	//@JsonProperty("id")
	private Long treeid;
	private String  name;
	private   Long pId;
	private String menutype;
	private String menukey;
	private long uid;
	
	private int seq;
	
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public Long getTreeid() {
		return treeid;
	}
	public void setTreeid(Long treeid) {
		this.treeid = treeid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public String getMenutype() {
		return menutype;
	}
	public void setMenutype(String menutype) {
		this.menutype = menutype;
	}
	public String getMenukey() {
		return menukey;
	}
	public void setMenukey(String menukey) {
		this.menukey = menukey;
	}
	public WeixinTreeNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	

	
	
	
	
}
