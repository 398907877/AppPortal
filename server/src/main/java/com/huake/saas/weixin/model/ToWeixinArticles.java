package com.huake.saas.weixin.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="Articles")
@XmlAccessorType(XmlAccessType.FIELD) 
public class ToWeixinArticles {

	@XmlElement(name = "item")
	private List<ToWeixinArticle> articles;

	public List<ToWeixinArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<ToWeixinArticle> articles) {
		this.articles = articles;
	}
	
}
