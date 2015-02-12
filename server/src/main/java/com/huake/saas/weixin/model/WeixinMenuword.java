package com.huake.saas.weixin.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huake.base.IdEntity;
@Entity
@Table(name = "app_weixin_menuwords", indexes = {@Index(name="IDX_KW_UID", columnList="uid"),											@Index(name="IDX_KW_WORD", columnList="word")})
public class WeixinMenuword extends IdEntity{
/**
 * 菜单关键字表
 * @author hxl
 */

		private Long uid;
		
		private String word;

		private WeixinKeywordRule rule;
		
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

		@ManyToOne(cascade=CascadeType.ALL)
		@JoinColumn(name="ruleId")
		public WeixinKeywordRule getRule() {
			return rule;
		}

		public void setRule(WeixinKeywordRule rule) {
			this.rule = rule;
		}

		
}
