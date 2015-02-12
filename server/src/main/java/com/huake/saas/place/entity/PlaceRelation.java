package com.huake.saas.place.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 省、市区号关系管理
 * @author Administrator
 *
 */
@Entity
@Table(name="hk_place_relation")
public class PlaceRelation implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 城市等级：省份，直辖市，自治区
	 */
	public static final Integer LEVEL_1=1;
	/**
	 * 城市
	 */
	public  static final Integer LEVEL_2=2;
	/**
	 * 全国
	 */
	public  static final Integer LEVEL_3=3;
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 省、市名称
	 */
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	/**
	 * 级别 省级为1 市为2
	 */
	@Column(name = "level" ,length = 100)
	private Integer level;
	
	/**
	 * 父级
	 */
	@Column(name = "parent_Level",length = 100)
	private Integer parentLevel; 
	
	/**
	 * 城市区号
	 */
	@Column(name = "area_Code",length = 100)
	private String areaCode;
	/**
	 * 百度城市代码
	 */
	@Column(name="city_id")
	private String cityId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(Integer parentLevel) {
		this.parentLevel = parentLevel;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
}
