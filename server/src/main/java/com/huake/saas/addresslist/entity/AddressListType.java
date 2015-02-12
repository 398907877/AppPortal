package com.huake.saas.addresslist.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.huake.saas.base.entity.BaseEntity;

/**
 * 通讯录分组管理
 * @author Administrator
 *
 */
@Entity
@Table(name = "app_addresslist_type")
public class AddressListType extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PARMS_ADDRESSLIST_TYPE= "addressListType";
	public static final String PARMS_ADDRESSLIST_TYPES= "addressListTypes";
	
	/**
	 * 名称
	 */
	private String name;
	
	private List<AddressList> address;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public List<AddressList> getAddress() {
		return address;
	}

	public void setAddress(List<AddressList> address) {
		this.address = address;
	}
	
}
