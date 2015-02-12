package com.huake.saas.addresslist.entity;

import java.util.List;

public class AddressListDetail {
	
	
	/**
	 * 通讯录类型列表
	 */
  private List<AddressListType> addressListTypes;
	
	/**
	 * 通讯录列表
	 */
   private List<AddressListApi> addressLists;

	public List<AddressListType> getAddressListTypes() {
		return addressListTypes;
	}

	public void setAddressListTypes(List<AddressListType> addressListTypes) {
		this.addressListTypes = addressListTypes;
	}

	public List<AddressListApi> getAddressLists() {
		return addressLists;
	}

	public void setAddressLists(List<AddressListApi> addressLists) {
		this.addressLists = addressLists;
	}


	
	
   
  
}
