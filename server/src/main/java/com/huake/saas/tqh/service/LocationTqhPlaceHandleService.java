package com.huake.saas.tqh.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huake.base.ServiceException;
import com.huake.saas.location.service.AbstractLocationHandleService;
import com.huake.saas.tqh.model.Place;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.AbstractWeixinMessage;
import com.huake.saas.weixin.model.ToWeixinArticle;
import com.huake.saas.weixin.model.ToWeixinArticles;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;
import com.huake.saas.weixin.service.LocationEventTargetAdapter;

/**
 * 台球会位置服务接口实现类。
 * @author laidingqing
 *
 */
@Service("locationTqhPlaceHandleService")
public class LocationTqhPlaceHandleService extends  AbstractLocationHandleService<Place> {

	private final static Logger log = LoggerFactory.getLogger(LocationTqhPlaceHandleService.class);
	
	@Autowired
	private PoolService poolService;
	
	@Override
	public AbstractWeixinMessage locationHandle(BigDecimal lat, BigDecimal lng, BigDecimal scale, WebContext context) throws ServiceException {
		
		List<Place> places = poolService.findNearPlaces(lat, lng);
		
		ToWeixinNewsMessage msg = new ToWeixinNewsMessage();
		msg.setToUserName(context.getFromUserName());
		msg.setFromUserName(context.getToUserName());
		msg.setCreateTime(System.currentTimeMillis());
		
		List<ToWeixinArticle> articles = new ArrayList<ToWeixinArticle>();
		log.debug("query places size is :" + places.size());
		for(Place n : places){
			ToWeixinArticle toWa = new ToWeixinArticle();
			toWa.setTitle(n.getName());
			if(n.getPictures().size() > 0){
				toWa.setThumbnail(n.getPictures().get(0));
			}
			toWa.setUrl("#");
			toWa.setDescription(n.getIntro());
			articles.add(toWa);
        }
		
		ToWeixinArticles tas = new ToWeixinArticles();
		tas.setArticles(articles);
		msg.setArticleCount(articles.size());
		msg.setArticles(tas);
		return msg;
		
	}


}
