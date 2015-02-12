package com.huake.saas.weixinmenu.model;
/**
 * 具体的菜单事件(链接形式)
 */
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;



public class MenuView {

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("name")
	private String name;
	
    @JsonProperty("url")
   private   String url;
    
    
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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



	
	
}
