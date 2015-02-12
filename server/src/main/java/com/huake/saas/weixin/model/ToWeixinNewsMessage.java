package com.huake.saas.weixin.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD) 
public class ToWeixinNewsMessage extends AbstractWeixinMessage {

	public ToWeixinNewsMessage(){
		super.setMessageType("news");
	}
	
	@Override
	public String getMessageEventType() {
		return AbstractWeixinMessage.TYPE_NEWS;
	}

	@XmlElement(name = "ArticleCount")
	private int articleCount;
	
	@XmlElement(name = "Articles")
	private ToWeixinArticles articles;

	public int getArticleCount() {
		return articles.getArticles().size();
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public ToWeixinArticles getArticles() {
		return articles;
	}

	public void setArticles(ToWeixinArticles articles) {
		this.articles = articles;
	}

}
