package com.huake.saas.supply.service;

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

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.mobileResource.entity.MobileResource;
import com.huake.saas.mobileResource.service.MobileResourceService;
import com.huake.saas.supply.entity.SupplyDemand;
import com.huake.saas.supply.repository.SupplyDemandDao;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.ToWeixinArticle;
import com.huake.saas.weixin.model.ToWeixinArticles;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;

@Service("supplyDemandWeixinTarget")
public class SupplyDemandWeixinTargetImpl implements SupplyDemandWeixinTarget{

	@Autowired 
	private SupplyDemandDao sdDao;
	
	@Autowired
	private MobileResourceService mobileService;
	
	@Value("#{envProps.mobileUrl}")
	private String mobileUrl;
	
	
	
	@Override
	public ToWeixinNewsMessage getSupply(String uid, String resultCount) {
		Pageable pageable = new PageRequest(0, new Integer(resultCount), new Sort(Direction.DESC, new String[] { "crtDate" }));
		Page<SupplyDemand> result = sdDao.findSupplyDemandByType(BaseEntity.STATUS_VALIDE, SupplyDemand.SUPPLY_DEMAND_SUP, uid, pageable);
	
	    WebContext context = WebContext.getInstance();
		ToWeixinNewsMessage msg = new ToWeixinNewsMessage();
		msg.setToUserName(context.getFromUserName());
		msg.setFromUserName(context.getToUserName());
		msg.setCreateTime(System.currentTimeMillis());
		
		List<ToWeixinArticle> articles = new ArrayList<ToWeixinArticle>();
		
		for(SupplyDemand n : result.getContent()){
			ToWeixinArticle toWa = new ToWeixinArticle();
			toWa.setTitle(n.getTitle());
			toWa.setThumbnail(n.getThumb());
			String url = createDetailUrl(n.getId().toString());
			toWa.setUrl(url);
			toWa.setDescription(n.getIntroduce());
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

	@Override
	public ToWeixinNewsMessage getDemand(String uid, String resultCount) {
		Pageable pageable = new PageRequest(0, new Integer(resultCount), new Sort(Direction.DESC, new String[] { "crtDate" }));
		Page<SupplyDemand> result = sdDao.findSupplyDemandByType(BaseEntity.STATUS_VALIDE, SupplyDemand.SUPPLY_DEMAND_DEM, uid, pageable);
	
	    WebContext context = WebContext.getInstance();
		ToWeixinNewsMessage msg = new ToWeixinNewsMessage();
		msg.setToUserName(context.getFromUserName());
		msg.setFromUserName(context.getToUserName());
		msg.setCreateTime(System.currentTimeMillis());
		
		List<ToWeixinArticle> articles = new ArrayList<ToWeixinArticle>();
		
		for(SupplyDemand n : result.getContent()){
			ToWeixinArticle toWa = new ToWeixinArticle();
			toWa.setTitle(n.getTitle());
			toWa.setThumbnail(n.getThumb());
			String url = createDetailUrl(n.getId().toString());
			toWa.setUrl(url);
			toWa.setDescription(n.getIntroduce());
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
	 * 生成供求关系首面连接
	 * @param uid
	 * @return
	 */
	private String createIndexUrl(String uid) {
		MobileResource mResource = mobileService.findByBizCodeAndRole(MobileResource.BIZ_supply, MobileResource.ROLE_NONE);
	    String target = mResource.getTarget();
		return mobileUrl+target+"?uid="+uid;
	}
	/**
	 * 生成供求关系详细连接
	 * @param uid
	 * @return
	 */
	private String createDetailUrl(String id) {
		MobileResource mResource = mobileService.findByBizCodeAndRole(MobileResource.BIZ_supply, MobileResource.ROLE_NONE);
	    String target = mResource.getTarget();
		return mobileUrl+target+"/"+id;
	}

}
