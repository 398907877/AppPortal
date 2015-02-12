package com.huake.saas.ernie.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huake.base.IdEntity;

/**
 * 摇奖项
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_ernie_items", indexes = {@Index(name="IDX_EI_UID", columnList="uid")})
public class ErnieItem extends IdEntity {

	/**
	 * 博饼活动 奖品 等级 
	 */
	public static final String BOBING_ZHUANGYUAN = "zhuangyuan";//状元
	public static final String BOBING_DUITANG = "duitang";//对堂
	public static final String BOBING_SANHONG = "sanhong";//三红
	public static final String BOBING_SIJIN = "sijin";//四进
	public static final String BOBING_ERJU = "erju";//二举
	public static final String BOBING_YIXIU = "yixiu";//一秀
	public static final String BOBING_BUZHONG = "buzhong";//不中
	/**
	 * 企业编号
	 */
	private Long uid;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 图片(手机图片规格：40*40)
	 */
	private String image;
	
	/**
	 * 对应营销互动活动
	 */
	private Ernie ernie;
	
	/**
	 * 奖品数量
	 */
	private int num;
	
	/**
	 * 中奖概率
	 */
	private BigDecimal probability;
	/**
	 * 对应实时获取积分，该奖项为积分时使用，默认为0
	 */
	private Integer integral;

	private String status;
	
	/**
	 * 博饼奖项分类（状元...）
	 */
	private String bobing;

	
	
	public String getBobing() {
		return bobing;
	}

	public void setBobing(String bobing) {
		this.bobing = bobing;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ernieId")
	@JsonIgnore
	public Ernie getErnie() {
		return ernie;
	}

	public void setErnie(Ernie ernie) {
		this.ernie = ernie;
	}

	public BigDecimal getProbability() {
		return probability;
	}

	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	
}
