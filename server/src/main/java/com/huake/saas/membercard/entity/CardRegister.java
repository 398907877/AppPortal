package com.huake.saas.membercard.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.huake.saas.base.entity.BaseEntity;

/**
 * for the card and the member associated 
 * @author chen weirong
 *
 */
@Entity
@Table(name = "app_card_register")
public class CardRegister extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PARMS_CARD_REGISTER= "register";
	
	public static final String PARMS_CARD_REGISTERS= "registers";

	@Column(name = "card_id", updatable = false)
	private int cardId;
	
	private String cardNumber;
	
	private String holderName;
	
	private String nickName;
	
	private String getMethod;

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGetMethod() {
		return getMethod;
	}

	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}
	
	
}
