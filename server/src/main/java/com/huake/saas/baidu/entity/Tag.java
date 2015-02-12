package com.huake.saas.baidu.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huake.saas.user.entity.TenancyUser;



/**
 * @Description   用于百度分组
 * */


@Entity
@Table(name = "baidu_tag")
public class Tag{
	
	@Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private  long id;
	
	/**
	 * 设备分组的名字
	 */
	private String name;
	/**
	 * 用户，一个分组多个用户。
	 * 一对多的关系
	 */
	@OneToMany(mappedBy="tag",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<TenancyUser> tenancyUser;
	
	
	public List<TenancyUser> getTenancyUser() {
		return tenancyUser;
	}
	public void setTenancyUser(List<TenancyUser> tenancyUser) {
		this.tenancyUser = tenancyUser;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}