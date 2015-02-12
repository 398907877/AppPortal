package com.huake.saas.weixin.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import com.huake.base.IdEntity;

/**
 * 微信渠道文本关键字回复定义，与BizMappingCfg相对应，来源于此数据表定义
 * @See BizMappingCfg
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_weixin_rules", indexes = {@Index(name="IDX_WP_RULE_UID", columnList="uid")})
public class WeixinKeywordRule extends IdEntity{
	
	/**
	 * 租户编号
	 */
	private Long uid;
	
	private Date ruleCreate;
	
	
	public Date getRuleCreate() {
		return ruleCreate;
	}

	public void setRuleCreate(Date ruleCreate) {
		this.ruleCreate = ruleCreate;
	}

	/**
	 * 规则名称
	 */
	private String title;
	
	/**
	 * 业务映射，若为空则取相应文本定义内容
	 */
	private String bizCode;
	
	/**
	 * 可为空，不映射业务逻辑，返回该文本
	 */
	

	private String text;
	/**
	 * 返回URL
	 */
	private String url;
	
	/**
	 * 映射的业务接口方法
	 */
	private String proxyInterface;
	/**
	 * 方法的中文名字，显示给用户看的！！！
	 */
	private String chineseProxyInterFace;
	
	public String getChineseProxyInterFace() {
		return chineseProxyInterFace;
	}

	public void setChineseProxyInterFace(String chineseProxyInterFace) {
		this.chineseProxyInterFace = chineseProxyInterFace;
	}

	/**
	 * 映射的接口业务方法调用参数
	 */
	private String params;
	
	/**
	 * 创建时间
	 */
	private Date createdAt;
	
	/**
	 * 回复类型（文本，链接，业务）
	 */
	private String wpCategory;
	
	private List<WeixinKeyword> keywords;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "rule")
	public List<WeixinKeyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<WeixinKeyword> keywords) {
		this.keywords = keywords;
	}
	
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	
	@Column( columnDefinition="LONGTEXT") 
	public String getText() {
	
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getProxyInterface() {
		return proxyInterface;
	}

	public void setProxyInterface(String proxyInterface) {
		this.proxyInterface = proxyInterface;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWpCategory() {
		return wpCategory;
	}

	public void setWpCategory(String wpCategory) {
		this.wpCategory = wpCategory;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
