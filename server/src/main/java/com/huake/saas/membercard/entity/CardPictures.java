package com.huake.saas.membercard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * for the member card logo picture or background picture
 * @author chen weirong
 *
 */
@Entity
@Table(name = "app_card_pictures")
public class CardPictures {
	
	public static final String PARMS_CARD_PICTURE="pic";

	private Long pid;
	
	private Date crtDate;
	
	@Column(name = "card_id", updatable = false)
	private int cardId;
	
	private String url;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
	

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}
	
	
	
	
}
