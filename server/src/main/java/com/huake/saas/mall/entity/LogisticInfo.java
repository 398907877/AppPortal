package com.huake.saas.mall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 物流公司信息表
 * @author zhiyong
 *
 */

@Entity
@Table(name = "app_logistic_info")
public class LogisticInfo {

	public final static String TYPE_HTML="html";
	public final static String TYPE_JSON="json";

	
	@Id
    @Column(name = "logistic_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	

	@Column(name = "logistic_core")
    private String logistiCore;

	@Column(name = "logistic_name")
    private String logisticName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogistiCore() {
		return logistiCore;
	}

	public void setLogistiCore(String logistiCore) {
		this.logistiCore = logistiCore;
	}

	public String getLogisticName() {
		return logisticName;
	}

	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}
	
	
    
}
