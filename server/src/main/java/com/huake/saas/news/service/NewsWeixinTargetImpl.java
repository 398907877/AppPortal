package com.huake.saas.news.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.huake.saas.mobileResource.entity.MobileResource;
import com.huake.saas.mobileResource.service.MobileResourceService;
import com.huake.saas.news.entity.News;
import com.huake.saas.news.repository.NewsDao;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.ToWeixinArticle;
import com.huake.saas.weixin.model.ToWeixinArticles;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;
/**
 * 
 * @author wujiajun
 * 新闻的业务
 *
 */
@Service("newsWeixinTarget")
public class NewsWeixinTargetImpl implements NewsWeixinTarget {

	@Autowired
	private NewsDao newsDao;
	
	@Autowired
	private MobileResourceService mobileService;
	
	@Value("#{envProps.mobileUrl}")
	private String mobileUrl;
	
	@Override
	public ToWeixinNewsMessage getNewsByCategory(String uid, String category, String resultCount) {
		Pageable pageable = new PageRequest(0, new Integer(resultCount), new Sort(Direction.DESC, new String[] { "banner","stick","crtDate" }));
		Page<News> result = newsDao.findPublishedNewsByCategory(uid, News.STATUS_VALIDE,News.IS_PUBLILSH, new Integer(category), pageable);
	
	    WebContext context = WebContext.getInstance();
		ToWeixinNewsMessage msg = new ToWeixinNewsMessage();
		msg.setToUserName(context.getFromUserName());
		msg.setFromUserName(context.getToUserName());
		msg.setCreateTime(System.currentTimeMillis());
		
		List<ToWeixinArticle> articles = new ArrayList<ToWeixinArticle>();
		
		for(News n : result.getContent()){
			ToWeixinArticle toWa = new ToWeixinArticle();
			toWa.setTitle(n.getTitle());
			toWa.setThumbnail(n.getPic());
			toWa.setUrl(n.getUrl());
			toWa.setDescription(n.getIntro());
			articles.add(toWa);
        }
		
		
		
		
		//默认加入 查看更多的记录
		//String readpath= context.getServletContext().getContextPath();
		String url = createIndexUrl(uid);			
		ToWeixinArticle toWa = new ToWeixinArticle();
		toWa.setTitle("显示更多");
		toWa.setUrl(url);
		articles.add(toWa);
		
		ToWeixinArticles tas = new ToWeixinArticles();
		tas.setArticles(articles);
		msg.setArticleCount(articles.size());
		msg.setArticles(tas);
		return msg;
	
	}
	
	@Override
	public ToWeixinNewsMessage gettest(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ToWeixinNewsMessage getNews(String uid, String resultCount) {
		Pageable pageable = new PageRequest(0, new Integer(resultCount), new Sort(Direction.DESC, new String[] { "crtDate" }));
		Page<News> result = newsDao.findPublishedNews(uid, News.STATUS_VALIDE,News.IS_PUBLILSH, pageable);

		WebContext context = WebContext.getInstance();
		ToWeixinNewsMessage msg = new ToWeixinNewsMessage();
		msg.setToUserName(context.getFromUserName());
		msg.setFromUserName(context.getToUserName());
		msg.setCreateTime(System.currentTimeMillis());
		
		List<ToWeixinArticle> articles = new ArrayList<ToWeixinArticle>();
		
		for(News n : result.getContent()){
			ToWeixinArticle toWa = new ToWeixinArticle();
			toWa.setTitle(n.getTitle());
			toWa.setThumbnail(n.getPic());
			toWa.setUrl(n.getUrl());
			toWa.setDescription(n.getIntro());
			articles.add(toWa);
        }
		
		
		//默认加入 查看更多的记录
				//String readpath= context.getServletContext().getContextPath();
		String url = createIndexUrl(uid);			
		ToWeixinArticle toWa = new ToWeixinArticle();
		toWa.setTitle("显示更多");
		toWa.setUrl(url);
		articles.add(toWa);
		
		ToWeixinArticles tas = new ToWeixinArticles();
		tas.setArticles(articles);
		msg.setArticleCount(articles.size());
		msg.setArticles(tas);
		return msg;
	}

	/**
	 * 生成新闻资讯首面连接
	 * @param uid
	 * @return
	 */
	private String createIndexUrl(String uid) {
		MobileResource mResource = mobileService.findByBizCodeAndRole(MobileResource.BIZ_news, MobileResource.ROLE_NONE);
	    String target = mResource.getTarget();
		return mobileUrl+target+"?uid="+uid;
	}

}
