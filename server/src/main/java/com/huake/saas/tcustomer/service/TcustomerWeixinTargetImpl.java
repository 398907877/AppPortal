package com.huake.saas.tcustomer.service;

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
import com.huake.saas.tcustomer.entity.Tcustomer;
import com.huake.saas.tcustomer.repository.TcustomerDao;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.ToWeixinArticle;
import com.huake.saas.weixin.model.ToWeixinArticles;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;

/**
 * 会员企业业务
 * @author zjy
 *
 */
@Service("tcustomerWeixinTarget")
public class TcustomerWeixinTargetImpl implements TcustomerWeixinTarget{

	@Autowired
	private TcustomerDao tcustomerDao;

	@Autowired
	private MobileResourceService mobileService;
	
	@Value("#{envProps.mobileUrl}")
	private String mobileUrl;
	
	@Override
	public ToWeixinNewsMessage getTcustomer(String uid, String resultCount) {
		Pageable pageable = new PageRequest(0, new Integer(resultCount), new Sort(Direction.DESC, new String[] { "createDate" }));
		Page<Tcustomer> result = tcustomerDao.findTcustomer(Tcustomer.normal,new Long(uid), pageable);
	
	    WebContext context = WebContext.getInstance();
		ToWeixinNewsMessage msg = new ToWeixinNewsMessage();
		msg.setToUserName(context.getFromUserName());
		msg.setFromUserName(context.getToUserName());
		msg.setCreateTime(System.currentTimeMillis());
		
		List<ToWeixinArticle> articles = new ArrayList<ToWeixinArticle>();
		
		for(Tcustomer t : result.getContent()){
			ToWeixinArticle toWa = new ToWeixinArticle();
			toWa.setTitle(t.getName());
			toWa.setThumbnail(t.getPic());
			String url = createDetailUrl(String.valueOf(t.getId()));
			toWa.setUrl(url);
			toWa.setDescription(t.getContent());
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
	 * 生成会员企业首面连接
	 * @param uid
	 * @return
	 */
	private String createIndexUrl(String uid) {
		MobileResource mResource = mobileService.findByBizCodeAndRole(MobileResource.BIZ_tcustomer, MobileResource.ROLE_NONE);
	    String target = mResource.getTarget();
		return mobileUrl+target+"?uid="+uid;
	}
	/**
	 * 生成会员企业详细连接
	 * @param uid
	 * @return
	 */
	private String createDetailUrl(String id) {
		MobileResource mResource = mobileService.findByBizCodeAndRole(MobileResource.BIZ_tcustomer, MobileResource.ROLE_NONE);
	    String target = mResource.getTarget();
		return mobileUrl+target+"/"+id;
	}

	

}
