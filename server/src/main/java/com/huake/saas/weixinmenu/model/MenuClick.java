package com.huake.saas.weixinmenu.model;
/**
 * 具体的菜单事件(点击事件)
 * @author hxl
 */

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;



public class MenuClick {

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("key")
	private String key;
	

    
 

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	
	
}
