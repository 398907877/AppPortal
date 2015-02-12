package com.huake.saas.weixin.model;

public enum MenuType {

	click, view;

	public static MenuType getMenuTypeByName(String name) {

		for (MenuType type : values()) {
			if (type.name().equals(name))
				return type;
		}
		return null;
	}
}
