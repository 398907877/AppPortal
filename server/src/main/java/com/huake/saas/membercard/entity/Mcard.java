package com.huake.saas.membercard.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huake.saas.base.entity.BaseEntity;

/**
 * member card persistence pojo
 * @author chen weirong
 *
 */
@Entity
@Table(name = "app_vip_cards")
public class Mcard extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static final String PARMS_MEMBER_CARD= "mcard";
	
	public static final String PARMS_MEMBER_CARDS= "mcards";
	
	/**
	 * card name like as "master gold card"
	 */
	private String cardName;
	
	/**
	 * holder name like as "chen weirong or memeber nickname
	 */
	private String holderName;
	
	/**
	 * card number like as"5457 5656 7890 1234"
	 */
	private String cardNumber;
	
	/**
	 * picture code for the scanning
	 */
	private String pictureCode;
	
	/**
	 * discount quote
	 */
	private int discount;
	
	/**
	 * expire date
	 */
	private Date expDate;
	
	/**
	 * start date
	 */
	private Date startDate;
	
	/**
	 * for the card get method
	 */
	private String remark;

	/**
	 * tenancy logo
	 */
	private String logo;
	
	/**
	 * card model provided by system
	 */
	private String cardModel;
	
	/**
	 * the background for card create
	 */
	private String background;
	
	public String getCardName() {
		return cardName;
	}


	public void setCardName(String cardName) {
		this.cardName = cardName;
	}


	public String getHolderName() {
		return holderName;
	}


	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}


	public String getPictureCode() {
		return pictureCode;
	}


	public void setPictureCode(String pictureCode) {
		this.pictureCode = pictureCode;
	}

	public int getDiscount() {
		return discount;
	}


	public void setDiscount(int discount) {
		this.discount = discount;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getExpDate() {
		return expDate;
	}


	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getLogo() {
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}


	public String getCardModel() {
		return cardModel;
	}


	public void setCardModel(String cardModel) {
		this.cardModel = cardModel;
	}


	public String getBackground() {
		return background;
	}


	public void setBackground(String background) {
		this.background = background;
	}	
	
	
}
