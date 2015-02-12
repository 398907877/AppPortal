package com.huake.saas.weixin.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 关键字表
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_weixin_keywords", indexes = {@Index(name="IDX_KW_UID", columnList="uid"),
												@Index(name="IDX_KW_WORD", columnList="word")})
public class WeixinKeyword extends IdEntity{
	
	private Long uid;
	
	private String word;

	private WeixinKeywordRule rule;
	
	private Date keywordCreate;
	
	
	public Date getKeywordCreate() {
		return keywordCreate;
	}

	public void setKeywordCreate(Date keywordCreate) {
		this.keywordCreate = keywordCreate;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@ManyToOne()
	@JoinColumn(name="ruleId")
	public WeixinKeywordRule getRule() {
		return rule;
	}

	public void setRule(WeixinKeywordRule rule) {
		this.rule = rule;
	}

	
}
