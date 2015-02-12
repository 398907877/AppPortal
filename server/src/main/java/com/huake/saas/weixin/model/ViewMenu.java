
package com.huake.saas.weixin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "app_weinxin_viewmenu")
public class ViewMenu  {
	
	private Long id;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	private SubMenu subMenu;
	
	
	@ManyToOne()
	@JoinColumn(name="view_menu")
	public SubMenu getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(SubMenu subMenu) {
		this.subMenu = subMenu;
	}
	
	
private String name;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	private MenuType type;
	private String url;


	public void setType(MenuType type) {
		this.type = type;
	}

	public ViewMenu() {
	}



	/**
	 * 菜单的响应动作类型，目前有click、view两种类型
	 * @return
	 */
	public MenuType getType() {
		return type;
	}

	/**
	 * 菜单的响应动作类型，目前有click、view两种类型
	 * @return
	 */
	public void setType(String type) {
		this.type = MenuType.getMenuTypeByName(type);
	}
	
	/**
	 * 网页链接，用户点击菜单可打开链接，不超过256字节
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 网页链接，用户点击菜单可打开链接，不超过256字节
	 * @return
	 */
	public void setUrl(String url) {
		this.url = url;
	}


}
