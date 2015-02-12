package com.huake.saas.membercard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 会员卡领取规则设定
 * @author skylai
 *
 */
@Entity
@Table(name = "app_vip_card_rules", indexes = {@Index(name="IDX_VIP_RULE", columnList="cardId")})
public class MCardRule extends IdEntity {
	
	public final static String RULE_INTEGRAL = "integral";
	public final static String RULE_PAYMENT = "payment";
	public final static String RULE_LOCATION = "location";

	private Long cardId;
	
	@Column(name = "name", length = 10)
	private String name;
	
	@Column(name = "value", length = 20)
	private String value;

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
