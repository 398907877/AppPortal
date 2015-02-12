package com.huake.saas.weixin.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.huake.base.IdEntity;


@Entity
@Table(name = "app_weixin_menutime")
public class MenuTime extends IdEntity{

	private  Date  menucreatetime;
	private long uid;
	

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public Date getMenucreatetime() {
		return menucreatetime;
	}

	public void setMenucreatetime(Date menucreatetime) {
		this.menucreatetime = menucreatetime;
	}
	
}
