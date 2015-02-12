package com.huake.saas.base.entity;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.huake.base.IdEntity;


/**
 * 业务映射
 * @author laidingqing
 *
 */
@Entity
@Table(name="app_biz_mapping_cfg")
public class BizMappingCfg extends IdEntity{
	
	/**
	 * 微信对应业务接口名称
	 */
	@Column(name="bean_name", updatable=false)
	private String beanName;
	/**
	 * 描述
	 */
	@Column(name="description", updatable=false)
	private String description;
	
	/**
	 * 业务代码，来自数据字典BIZCODE
	 */
	@Column(name="biz_code", updatable=false)
	private String bizCode;
	
	/**
	 * 服务接口定义,Key为接口方法名称，Value为描述
	 */
	@ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "biz_code")
    @CollectionTable(name = "app_biz_functions", joinColumns = @JoinColumn(name = "id_mapping_cfg"))
    @Fetch(FetchMode.SUBSELECT)
	private Map<String, String> functions;
	
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getBizCode() {
		return bizCode;
	}
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
}
