package com.huake.saas.tcustomer.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huake.saas.category.entity.IndustryCategory;
import com.huake.saas.tenancy.entity.Tenancy;
/**
 * 租户下的自定义客户，前台该客户可以通过所属的租户来查询相关的数据
 * @author hyl
 *
 */
@Entity
@Table(name="app_tenancy_customer")
public class Tcustomer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int del = 0;
	
	public static final int normal =1;
	
	
	/**
	 * 客户ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false)	
	private long id;
	
	private String pic;
	
//	/**
//	 * 租户ID
//	 */
//	@Column(name = "uid")
//	private long uid;
	
	/**
	 * 创建时间
	 */
	@Column(name = "crt_date")
	private Date createDate;
	
	/**
	 * 状态
	 */
	@Column(name = "status")
	private int status;
	
	/**
	 * 修改时间
	 */
	@Column(name = "up_date")
	private Date updateDate;
	
	/**
	 * 客户姓名
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 客户传真
	 */
	@Column(name = "fax")
	private String fax;
	
	/**
	 * 客户地址
	 */
	@Column(name = "address")
	private String address;
	
	/**
	 * 客户介绍
	 */
	@Column(name = "content")
	@Lob
	private String content;
	
	/**
	 * 客户电话
	 */
	@Column(name = "tel")
	private String tel;
	/**
	 * 联系人
	 */
	private String linkman;
	/**
	 * 经营范围
	 */
	private String businessScope;
	/**
	 * 客户产品
	 */
	@Lob
	private String products;
	
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="uid")
	private Tenancy tenancy;
	
	
	
	@ManyToMany
    @JoinTable(name = "app_customer_category", joinColumns = @JoinColumn(name = "c_id"),
    		         inverseJoinColumns=@JoinColumn(name="t_id"))
	private Set<IndustryCategory> customerTypes = new HashSet<IndustryCategory>();
	

    @JsonIgnore
	public Tenancy getTenancy() {
		return tenancy;
	}

	public void setTenancy(Tenancy tenancy) {
		this.tenancy = tenancy;
	}
	@JsonIgnore
	public Set<IndustryCategory> getCustomerTypes() {
		return customerTypes;
	}
	
	public void setCustomerTypes(Set<IndustryCategory> customerTypes) {
		this.customerTypes = customerTypes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	
}
