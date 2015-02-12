package com.huake.saas.base.annonation.model;

import java.util.Map;

public class BizCodeInterface {

	private String name;
	
	private String desc;
	
	@SuppressWarnings("rawtypes")
	private Map<String, Class> params;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Class> getParams() {
		return params;
	}

	public void setParams(Map<String, Class> params) {
		this.params = params;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
