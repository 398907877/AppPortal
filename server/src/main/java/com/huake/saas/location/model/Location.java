package com.huake.saas.location.model;

import java.math.BigDecimal;

/**
 * 位置模型，视情况加上精度列
 * @author laidingqing
 *
 */
public class Location {

	private BigDecimal lat;
	
	private BigDecimal lng;
	
	private BigDecimal scale;
	
	public Location(BigDecimal lat, BigDecimal lng, BigDecimal scale){
		this.lat = lat;
		this.lng = lng;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLng() {
		return lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	public BigDecimal getScale() {
		return scale;
	}

	public void setScale(BigDecimal scale) {
		this.scale = scale;
	}
	
	
}
