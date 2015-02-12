package com.huake.saas.supply.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huake.saas.base.entity.BaseEntity;

/**
 * 4.14	租户供求信息分类表（只有管理员才有权限）
 * @author zhongshuhui
 *
 */
@Entity
@Table(name = "app_supply_demand")
public class SupplyDemand extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 供
	 */
	public static final int SUPPLY_DEMAND_SUP = 0;
	
	/**
	 * 求
	 */
	public static final int SUPPLY_DEMAND_DEM = 1;
	
	public static final String PARMS_SUPPLY_DEMAND= "supplyDemand";
			
	public static final String PARMS_SUPPLY_DEMANDS= "supplyDemands";
	/**
	 * 信息类型（供：0    求：1）
	 */
	private Integer type;
	
	/**
	 * 所属行业
	 */
	@Column(name = "s_d_type_id")
	private  Long typeId;
	
	@Lob
	private String detailInfo;
	
	/**
	 * 价格
	 */
	private Double price;
	
	/**
	 * 总数
	 */
	private Integer total;
	
	/**
	 * 所在地
	 */
	private String address;
	
	/**
	 * 起购数
	 */
	private Integer limitNum;

	/**
	 * 开始日期
	 */
	private Date startDate;
	
	/**
	 * 结束日期
	 */
	private Date endDate;
	
	/**
	 * 关键词
	 */
	private String keyword;
	
	/**
	 * 缩略图
	 */
	private String thumb;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}




	/**
	 * 详细内容
	 */
	private String introduce;
	
	/**
	 * 联系人
	 */
	@Column(name = "to_user")
	private String toUser;
	
	private String title;
	

	/**
	 * 联系电话
	 */
	private String tel;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Lob
	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	@Lob
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

}
