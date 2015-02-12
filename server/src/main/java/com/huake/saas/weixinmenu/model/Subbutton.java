package com.huake.saas.weixinmenu.model;
/**
 * 二级菜单
 * @author hxl
 */
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;



public class Subbutton {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("sub_button")
	private List<Object> sub_button;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Object> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<Object> sub_button) {
		this.sub_button = sub_button;
	}

	

	
	
	
}
