package com.huake.saas.supply.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 4.14	租户供求信息表
 * @author zhongshuhui
 *
 */
@Entity
@Table(name = "app_s_d_type")
public class SupplyDemandType {
	
	public static final String PARMS_SUPPLY_DEMAND_TYPE= "supplyDemandType";
	
	public static final String PARMS_SUPPLY_DEMAND_TYPES= "supplyDemandTypes";
	
	/**
	 * 供
	 */
	public static final Integer TYPE_VALUE_SUPPLY= 0;
	
	/**
	 * 求
	 */
	public static final Integer TYPE_VALUE_DEMAND= 1;
	
	/**
	 * 供求
	 */
	public static final Integer TYPE_VALUE_SUPPLY_DEMAND= 2;
	/**
	 * 有效
	 */
	public static final Integer STATUS_VALIDE=1;
	/**
	 * 无效
	 */
	public static final Integer STATUS_INVALIDE=0;
	
	
	private Long id;
	
	/**
	 * 供求信息分类类型如服务提供，
	 * 产品提供，产品需求，人事需求，
	 * 信息需求，服务需求)
	 */
	@Column(name = "supply_demand_type", updatable = false)
	private String  supplyDemandType;
	
	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 信息类型
	 */
	private int type;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getSupplyDemandType() {
		return supplyDemandType;
	}

	public void setSupplyDemandType(String supplyDemandType) {
		this.supplyDemandType = supplyDemandType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
