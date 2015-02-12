package com.huake.saas.supply.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 供求信息图片Entity
 */
@Entity
@Table(name = "app_s_d_pictures")
public class SupplyDemandPhoto extends IdEntity{

	
	@Column(updatable = false)
	private Long supplyDemandId;
	
	public Long getSupplyDemandId() {
		return supplyDemandId;
	}

	public void setSupplyDemandId(Long supplyDemandId) {
		this.supplyDemandId = supplyDemandId;
	}

	private String filePath;
	
	/**
	 * 图像类型，如origin:原始图, thumbnail:缩略图, small:小图，等
	 */
	private String imgType;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	

}
