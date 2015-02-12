package com.huake.saas.weixinmenu.model;
/**
 * json 最外层button，包含所有菜单
 * @author hxl
 */
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuButton {
	
	@JsonProperty("button")
	private List<Object> button;

	public List<Object> getButton() {
		return button;
	}

	public void setButton(List<Object> button) {
		this.button = button;
	}

}
