package com.huake.saas.tcustomer.entity;

import java.util.List;

public class CostomerDetail {
	
	/**
	 * 客户类型
	 */
	private List<CustomerType> types;
    
	
	public List<CustomerType> getTypes() {
		return types;
	}

	public void setTypes(List<CustomerType> types) {
		this.types = types;
	}

	/**
	 * 客户列表
	 * @return
	 */
	public List<Tcustomer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Tcustomer> customers) {
		this.customers = customers;
	}

	private List<Tcustomer> customers;
	
	
}
