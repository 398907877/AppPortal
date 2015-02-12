package com.huake.saas.membercard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.huake.saas.base.entity.BaseEntity;

/**
 * for member card usage record
 * @author chen weirong
 *
 */
@Entity
@Table(name = "app_card_record")
public class CardRecord extends BaseEntity{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String PARMS_CARD_RECORD= "record";
	
	public static final String PARMS_CARD_RECORDS= "records";
	
	@Column(name = "card_number", updatable = false)
	private String cardNumber;
	
	private String location;
	
	private boolean isOnline;
	
	private String relations;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public String getRelations() {
		return relations;
	}

	public void setRelations(String relations) {
		this.relations = relations;
	}

}
