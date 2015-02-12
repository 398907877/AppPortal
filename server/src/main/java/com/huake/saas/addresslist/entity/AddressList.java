package com.huake.saas.addresslist.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "app_addresslist")
public class AddressList {
	
	public final static int normal = 1;
	public final static int del = 0 ;
	
	public final static String PUBLIC = "1";
	public final static String NO_PUBLIC = "0";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false)	
	private long id;
	
	/**
	 * 相关注册会员 id
	 */
	private Long userId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "office_phone")
	private String officePhone;
	/**
	 * 是否公开办公电话 office_phone
	 */
	private String publicPhone;
	
	@Column(name = "tel")
	private String tel;
	/**
	 * 是否公开私人电话
	 */
	private String publicTel;
	
	@Column(name = "content")
	@Lob
	private String content;
	
   /**
    * 职位
    */
	private String position;
	/**
	 * 部门 手动填写
	 */
	private String dept;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 是否公开邮箱
	 */
	private String publicEmail;
	/**
	 * 详细地址
	 */
	private String address;
	
	@Column(name = "crt_date",updatable=false)
	private Date createDate;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "dept_id")
	private Long deptId;
	
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 头像
	 */
	private String avatar;
	
	
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Column(name = "uid")
	private long uid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getPublicPhone() {
		return publicPhone;
	}

	public void setPublicPhone(String publicPhone) {
		this.publicPhone = publicPhone;
	}

	public String getPublicTel() {
		return publicTel;
	}

	public void setPublicTel(String publicTel) {
		this.publicTel = publicTel;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPublicEmail() {
		return publicEmail;
	}

	public void setPublicEmail(String publicEmail) {
		this.publicEmail = publicEmail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
