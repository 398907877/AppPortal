package com.huake.saas.supply.entity;

import java.util.List;

public class SupplyDemandDetail {
	
	/**
	 * 供类型
	 */
	private List<SupplyDemandType> supplys; 
	
	/**
	 * 求类型
	 */
	private List<SupplyDemandType> demands; 
	
	
	/**
	 * 供求信息
	 */
	private List<SupplyDemand> supplyDemands;

	

	public List<SupplyDemandType> getSupplys() {
		return supplys;
	}

	public void setSupplys(List<SupplyDemandType> supplys) {
		this.supplys = supplys;
	}

	public List<SupplyDemandType> getDemands() {
		return demands;
	}

	public void setDemands(List<SupplyDemandType> demands) {
		this.demands = demands;
	}

	public List<SupplyDemand> getSupplyDemands() {
		return supplyDemands;
	}

	public void setSupplyDemands(List<SupplyDemand> supplyDemands) {
		this.supplyDemands = supplyDemands;
	}
}
