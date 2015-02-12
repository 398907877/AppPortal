package com.huake.saas.invitation.service;

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
import com.huake.saas.invitation.entity.Invitation;
import com.huake.saas.invitation.respository.InvitationDao;
import com.huake.saas.mobileResource.entity.MobileResource;
import com.huake.saas.mobileResource.repository.MobileResourceDao;
import com.huake.saas.mobileResource.service.MobileResourceService;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.ToWeixinArticle;
import com.huake.saas.weixin.model.ToWeixinArticles;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;

/**
 * 帖子业务
 * @author Administrator
 *
 */
@Service("invitationWeixinTarget")
public class InvitationWeixinTargetImpl implements InvitationWeixinTarget{

	@Autowired
	private InvitationDao invitationDao;
	
	@Autowired
	private MobileResourceService mobileService;
	
	@Value("#{envProps.mobileUrl}")
	private String mobileUrl;
	
	
	@Override
	public ToWeixinNewsMessage getInvitations(String uid, String resultCount) {
		Pageable pageable = new PageRequest(0, new Integer(resultCount), new Sort(Direction.DESC, new String[] { "crtDate" }));
		Page<Invitation> result = invitationDao.findInvitation(BaseEntity.STATUS_VALIDE,uid, pageable);
	
	    WebContext context = WebContext.getInstance();
		ToWeixinNewsMessage msg = new ToWeixinNewsMessage();
		msg.setToUserName(context.getFromUserName());
		msg.setFromUserName(context.getToUserName());
		msg.setCreateTime(System.currentTimeMillis());
		
		List<ToWeixinArticle> articles = new ArrayList<ToWeixinArticle>();
		
		String url = createIndexUrl(uid);
		
		for(Invitation n : result.getContent()){
			ToWeixinArticle toWa = new ToWeixinArticle();
			toWa.setTitle(n.getTitle());
			toWa.setThumbnail("");
			toWa.setUrl(url);//TODO 帖子链接待定
			toWa.setDescription(n.getIntroduce());
			articles.add(toWa);
        }
		//更多显示
		ToWeixinArticle toWa = new ToWeixinArticle();
		toWa.setTitle("显示更多");
		toWa.setThumbnail("");
		toWa.setUrl(url);//TODO 帖子链接待定
		articles.add(toWa);
		
		ToWeixinArticles tas = new ToWeixinArticles();
		tas.setArticles(articles);
		msg.setArticleCount(articles.size());
		msg.setArticles(tas);
		return msg;
	}

	/**
	 * 生成论坛首面连接
	 * @param uid
	 * @return
	 */
	private String createIndexUrl(String uid) {
		MobileResource mResource = mobileService.findByBizCodeAndRole(MobileResource.BIZ_invitation, MobileResource.ROLE_NONE);
	    String target = mResource.getTarget();
		return mobileUrl+target+"?uid="+uid;
	}

}
