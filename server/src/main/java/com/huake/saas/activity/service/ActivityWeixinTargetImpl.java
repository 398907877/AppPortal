package com.huake.saas.activity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.huake.saas.activity.entity.Event;
import com.huake.saas.activity.repository.EventDao;
import com.huake.saas.mobileResource.entity.MobileResource;
import com.huake.saas.mobileResource.service.MobileResourceService;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.ToWeixinArticle;
import com.huake.saas.weixin.model.ToWeixinArticles;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;

/**
 * 
 * @author hxl 活动的业务
 */
@Component("activityWeixinTarget")
public class ActivityWeixinTargetImpl implements ActivityWeixinTarget {
	@Autowired
	private EventDao eventDao;
	@Autowired
	private MobileResourceService mobileService;

	@Value("#{envProps.mobileUrl}")
	private String mobileUrl;

	@Override
	public ToWeixinNewsMessage getAllActivities(String uid, String resultCount) {
		Pageable pageable = new PageRequest(0, new Integer(resultCount),
				new Sort(Direction.DESC, new String[] { "crtDate" }));
		Page<Event> events = eventDao.findByStatus(Event.STATUS_VALID,
				new Long(uid), pageable);

		WebContext context = WebContext.getInstance();
		ToWeixinNewsMessage msg = new ToWeixinNewsMessage();
		msg.setToUserName(context.getFromUserName());
		msg.setFromUserName(context.getToUserName());
		msg.setCreateTime(System.currentTimeMillis());

		List<ToWeixinArticle> articles = new ArrayList<ToWeixinArticle>();
		for (Event e : events) {
			ToWeixinArticle toWa = new ToWeixinArticle();
			toWa.setTitle(e.getTitle());
			toWa.setDescription(e.getInfo());
			String url = createDetailUrl(String.valueOf(e.getId()));
			toWa.setUrl(url);
			toWa.setThumbnail(e.getPoster());
			articles.add(toWa);
		}
		
		//更多显示
		String url = createIndexUrl(uid);
		ToWeixinArticle toWa = new ToWeixinArticle();
		toWa.setTitle("显示更多");
		toWa.setThumbnail("");
		toWa.setUrl(url);
		articles.add(toWa);
				
		
		ToWeixinArticles tas = new ToWeixinArticles();
		tas.setArticles(articles);
		msg.setArticleCount(articles.size());
		msg.setArticles(tas);

		return msg;
	}
	/**
	 * 生成活动首面连接
	 * @param uid
	 * @return
	 */
	private String createIndexUrl(String uid) {
		MobileResource mResource = mobileService.findByBizCodeAndRole(MobileResource.BIZ_activity, MobileResource.ROLE_NONE);
	    String target = mResource.getTarget();
		return mobileUrl+target+"?uid="+uid;
	}
	/**
	 * 生成活动详细连接
	 * @param uid
	 * @return
	 */
	private String createDetailUrl(String id) {
		MobileResource mResource = mobileService.findByBizCodeAndRole(MobileResource.BIZ_activity, MobileResource.ROLE_NONE);
	    String target = mResource.getTarget();
		return mobileUrl+target+"/"+id;
	}

}
