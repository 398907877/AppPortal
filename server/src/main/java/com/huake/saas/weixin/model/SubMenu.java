
package com.huake.saas.weixin.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "app_weinxin_submenu")
public class SubMenu {
	private Long id;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
private String name;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	


	

	private List<ViewMenu> viewmenu;
	
	
	
	private List<ClickMenu> clickmenu;
	
	
	
	

	@OneToMany(mappedBy="subMenu",cascade=CascadeType.ALL)
	public List<ClickMenu> getClickmenu() {
		return clickmenu;
	}

	public void setClickmenu(List<ClickMenu> clickmenu) {
		this.clickmenu = clickmenu;
	}

	@OneToMany(mappedBy="subMenu",cascade=CascadeType.ALL)
	public List<ViewMenu> getViewmenu() {
		return viewmenu;
	}

	public void setViewmenu(List<ViewMenu> viewmenu) {
		this.viewmenu = viewmenu;
	}
	
	
	


	
}
