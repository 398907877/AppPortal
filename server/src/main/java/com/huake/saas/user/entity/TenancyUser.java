package com.huake.saas.user.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huake.base.IdEntity;
import com.huake.saas.addresslist.entity.AddressList;
import com.huake.saas.baidu.entity.BaiduDevice;
import com.huake.saas.baidu.entity.Tag;

/**
 * 注册会员用户
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_session_users")
public class TenancyUser extends IdEntity{

	public final static int normal = 1;
	public final static int del = 0;
	public final static String SEX_MALE = "1";
	public final static String SEX_FEMALE = "0";

	/**
	 * 姓名
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 登录名
	 */
	@Column(name = "loginname")
	private String loginname;

	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;

	/**
	 * 创建时间
	 */
	@Column(name = "crt_date")
	private Date createDate;
	
	@Column(name = "crt_date")
	private Date uptDate;

	/**
	 * 状态
	 */
	@Column(name = "status")
	private int status;

	/**
	 * 租户编号
	 */
	@Column(name = "uid")
	private long uid;

	/**
	 * 头像
	 */
	@Column(name = "avatar")
	private String avatar;

	/**
	 * 个性签名
	 */
	private String abbr;
	
	/**
	 * 出生日期
	 */
	private Date birth;

	
	/**
	 * 联系方式
	 */
	private String mobile;
	
	private String sex;
	
	private String unit;
	
	private String email;
	
	private String qqId;

	/**
	 * 微信 对应当前租户 的公众号的 唯一id
	 */
	private String openid;
	
	private String sinaId;
	
	/**
	 * 用户对应的通讯录
	 */
	private AddressList addressList;
	
	/**
	 * 登录设备，一个用户对应多个设备
	 * 一对多关系
	 */
	private List<BaiduDevice> baiduDevice;
	/**
	 * tag分组，多个用户对应一个分组。
	 * 多对一关系
	 */
	private Tag  tag;	
	/**
	 * 上一次登录时间
	 */
	private Date lastLoginTime;
	
	/**
	 * 会员参加的活动id，不录入数据库。
	 */
	private Long ernieId;
	
	@Transient
	public Long getErnieId() {
		return ernieId;
	}

	public void setErnieId(Long ernieId) {
		this.ernieId = ernieId;
	}

	@ManyToOne()
	@JoinColumn(name="tagId")
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	@OneToMany(mappedBy="tenancyUser",cascade=CascadeType.ALL)
	@JsonIgnore
	public List<BaiduDevice> getBaiduDevice() {
		return baiduDevice;
	}

	public void setBaiduDevice(List<BaiduDevice> baiduDevice) {
		this.baiduDevice = baiduDevice;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public Date getUptDate() {
		return uptDate;
	}

	public void setUptDate(Date uptDate) {
		this.uptDate = uptDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	public String getSinaId() {
		return sinaId;
	}

	public void setSinaId(String sinaId) {
		this.sinaId = sinaId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	@Transient
	public AddressList getAddressList() {
		return addressList;
	}
	public void setAddressList(AddressList addressList) {
		this.addressList = addressList;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
