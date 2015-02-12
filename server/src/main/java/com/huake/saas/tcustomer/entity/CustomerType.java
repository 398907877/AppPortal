package com.huake.saas.tcustomer.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 会员客户分类表
 * @author hyl
 *
 */
@Entity
@Table(name="app_customertype")
public class CustomerType  implements Serializable {


	private static final long serialVersionUID = 1L;
	
	public static final int del = 0;
	
	public static final int normal =1;
	
	

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")	
	private long id;
	
	/**
	 * 内容
	 */
	@Column(name = "Type_content")
	private String typeContent;
	
	/**
	 * 状态
	 */
	@Column(name = "status")
	private int status;
	
	/**
	 * 租户ID
	 */
	@Column(name = "uid")
	private long uid;
	
	
	/**
	 * 租户ID
	 */
	@Column(name = "crt_date")
	private Date createDate;
/*	
	@ManyToMany(mappedBy="customerTypes")
	private Set<Tcustomer> customers = new HashSet<Tcustomer>(); 
	*/
	
/*
	public Set<Tcustomer> getCustomers () {
		return customers;
	}
	
	public void setCustomers (Set<Tcustomer> customers ) {
		this.customers = customers;
	}*/

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTypeContent() {
		return typeContent;
	}

	public void setTypeContent(String typeContent) {
		this.typeContent = typeContent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	
	
}
