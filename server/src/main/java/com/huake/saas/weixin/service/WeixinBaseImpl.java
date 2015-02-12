package com.huake.saas.weixin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.ToWeixinArticle;
import com.huake.saas.weixin.model.ToWeixinArticles;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;
/**
 * 
 * @author hxl
 * 通用
 *
 */
@Service("weixinBase")
public class WeixinBaseImpl implements WeixinBase {

	@Override
	public ToWeixinNewsMessage getDetail(String uid, String detail) {
		   WebContext context = WebContext.getInstance();
			ToWeixinNewsMessage msg = new ToWeixinNewsMessage();
			msg.setToUserName(context.getFromUserName());
			msg.setFromUserName(context.getToUserName());
			msg.setCreateTime(System.currentTimeMillis());
			
			List<ToWeixinArticle> articles = new ArrayList<ToWeixinArticle>();
			
		
				ToWeixinArticle toWa = new ToWeixinArticle();
				toWa.setTitle(detail);
			
				articles.add(toWa);
	 
			
			ToWeixinArticles tas = new ToWeixinArticles();
			tas.setArticles(articles);
			msg.setArticleCount(articles.size());
			msg.setArticles(tas);
			return msg;
		
	}

}
