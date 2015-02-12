
package com.huake.saas.weixin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "app_weinxin_clickmenu")
public class ClickMenu  {
	
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
	@JoinColumn(name="click_menu")
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
	private String evenkey;


	public void setType(MenuType type) {
		this.type = type;
	}

	public ClickMenu() {
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

	public String getEvenkey() {
		return evenkey;
	}

	public void setEvenkey(String evenkey) {
		this.evenkey = evenkey;
	}
	



}
