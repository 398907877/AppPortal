package com.huake.saas.category.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 行业分类
 * @author zjy
 *
 */
@Entity
@Table(name="app_industry_category",indexes = {@Index(name="IDX_INDUSTRYCATEGORY_PID",columnList="parentId")})
public class IndustryCategory extends IdEntity{
	
	private String name;
	
	private String pathCode;
	
	private String usefor;
	
	private Integer parentId;
	
	private String description;

	private Integer level;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPathCode() {
		return pathCode;
	}

	public void setPathCode(String pathCode) {
		this.pathCode = pathCode;
	}

	public String getUsefor() {
		return usefor;
	}

	public void setUsefor(String usefor) {
		this.usefor = usefor;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
