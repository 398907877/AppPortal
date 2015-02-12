package com.huake.saas.baidu.entity;



import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.huake.saas.category.entity.BaiduCategory;
import com.huake.saas.user.entity.TenancyUser;

/**
 * @author wujiajun
 * @time 2014 -05
 * @Description   用于记录登记的设备
 * */


@Entity
@Table(name = "app_device",indexes = {@Index(name="IDX_PUSH_USERID",columnList="userId"),@Index(name="IDX_PUSH_TYPE",columnList="deviceType")})
public class BaiduDevice {
	
	@Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private  long id;
	/**
	 * 用户标识，在Android里，channel_id + userid指定某一个特定client。
	 */
	private String userId;
	
	private int devicecount;
	/**
	 * 通道标识
	 */
	private long channelId;
	/**
	 * 创建日期
	 */
	private  Date crtDate;
	/**
	 * 修改日期
	 */
	private Date upedDate;
	/**
	 * 设备类型
	 * 1：浏览器设备；
	 * 2：PC设备
	 * 3：Andriod设备；
	 * 4：iOS设备；
	 * 5：Windows Phone设备；
	 */
	private int  deviceType;

	@Column(name="appmemberid")
	private long memberId;
	

	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	/**
	 * 设备类型  0消息  1通知
	 */
	private int messageType;
	
	/**
	 * 人群类型   0全部  1 个人 2分组
	 */
	private  int populationType;
	
	private String message;
	private String title;
	/**
	 * 设备状态
	 *                  ！！！！  0---使用中  ！！   默认为0  ，不适用的话通过接口更改？
	 * 其他忽略
	 */
	private int status=0;
	
	/**
	 * 租户id
	 * 
	 * ？？
	 */
	
	private   int  uid ;
	
	
	

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getDevicecount() {
		return devicecount;
	}
	public void setDevicecount(int devicecount) {
		this.devicecount = devicecount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 当前设备对应某个用户   -----后续需要改为manytoone   
	 * 一个用户对应多个设备
	 * 多对一关系
	 */
	@ManyToOne()
	@JoinColumn(name="memberId")
	private  TenancyUser  tenancyUser;
	/**
	 * 当前设备对应多个分类
	 * 一对多关系
	 */
	@OneToMany(mappedBy="baiduDevice",cascade=CascadeType.ALL)
	private List<BaiduCategory>  baiCategory;
	public TenancyUser getTenancyUser() {
		return tenancyUser;
	}
	public void setTenancyUser(TenancyUser tenancyUser) {
		this.tenancyUser = tenancyUser;
	}
	public List<BaiduCategory> getBaiCategory() {
		return baiCategory;
	}
	public void setBaiCategory(List<BaiduCategory> baiCategory) {
		this.baiCategory = baiCategory;
	}


	
	
	
	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getChannelId() {
		return channelId;
	}
	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}
	
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public Date getUpedDate() {
		return upedDate;
	}
	public void setUpedDate(Date upedDate) {
		this.upedDate = upedDate;
	}


	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	public int getPopulationType() {
		return populationType;
	}
	public void setPopulationType(int populationType) {
		this.populationType = populationType;
	}
	
	
	
	
	
	
	

}
