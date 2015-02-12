package com.huake.saas.ernie.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.huake.saas.ernie.entity.Ernie;
import com.huake.saas.mobileResource.entity.MobileResource;
import com.huake.saas.mobileResource.service.MobileResourceService;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.ToWeixinArticle;
import com.huake.saas.weixin.model.ToWeixinArticles;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;
/**
 * 
 * @author wujiajun
 * 营销互动
 *
 */
@Service("ernieWeixinTarget")
public class ErnieWeixinTargetImpl implements ErnieWeixinTarget {

	@Autowired
	private ErnieService ernieService;
	
	@Autowired
	private MobileResourceService mobileService;
	
	@Value("#{envProps.mobileUrl}")
	private String mobileUrl;
	
	
	


	@Override
	public ToWeixinNewsMessage getErnie(String uid, String resultCount) {
	
		//Ernie   的DAO   find  最新的  Ernie对象！
		Pageable pageable = new PageRequest(0, 5, new Sort(
				Direction.DESC, new String[] { "updatedAt" }));
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<Ernie> ernies = ernieService.getNowAllErnie(Long.valueOf(uid), searchParams, pageable);
		
		WebContext context = WebContext.getInstance();
		ToWeixinNewsMessage msg = new ToWeixinNewsMessage();
		msg.setToUserName(context.getFromUserName());
		msg.setFromUserName(context.getToUserName());
		msg.setCreateTime(System.currentTimeMillis());
		
		List<ToWeixinArticle> articles = new ArrayList<ToWeixinArticle>();
		for (Ernie e : ernies) {
			ToWeixinArticle toWa = new ToWeixinArticle();
			toWa.setTitle(e.getTitle());
			toWa.setDescription(e.getContent());
			String url = createDetailUrl(String.valueOf(e.getId()),String.valueOf(e.getUid()),String.valueOf(e.getCategory()));
			toWa.setUrl(url);
			toWa.setThumbnail(e.getImage());
			articles.add(toWa);
		}
		//默认加入 查看更多的记录
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
	 * 生成营销互动首面连接
	 * @param uid
	 * @return
	 */
	private String createIndexUrl(String uid) {
		MobileResource mResource = mobileService.findByBizCodeAndRole(MobileResource.BIZ_ernie, MobileResource.ROLE_NONE);
	    String target = mResource.getTarget();
		return mobileUrl+target+"?uid="+uid;
	}
	
	/**
	 * 生成营销互动详细连接
	 * @param uid
	 * @return
	 */
	private String createDetailUrl(String id,String uid,String category) {
		MobileResource mResource = mobileService.findByBizCodeAndRole(MobileResource.BIZ_ernie, MobileResource.ROLE_NONE);
	    String target = mResource.getTarget();
	    if(category.equals("3")){
	    	return mobileUrl+target+"/choujiang/bobing/"+id+"?uid="+uid;
	    }else if(category.equals("1")){
	    	return mobileUrl+target+"/choujiang/"+id+"?uid="+uid;
	    }
	    return null;
	}

}
