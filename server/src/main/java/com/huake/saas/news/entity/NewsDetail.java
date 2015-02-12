package com.huake.saas.news.entity;

import java.util.List;

public class NewsDetail {
	private List<NewsCategory> newsCategory;

	public List<NewsCategory> getNewsCategory() {
		return newsCategory;
	}

	public void setNewsCategory(List<NewsCategory> newsCategory) {
		this.newsCategory = newsCategory;
	}

	private List<News> news;

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

}
