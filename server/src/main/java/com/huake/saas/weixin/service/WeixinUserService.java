package com.huake.saas.weixin.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.huake.base.ServiceException;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.repository.TenancyUserDao;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.WeixinUser;
import com.huake.saas.weixin.repository.WeixinUserDao;

/**
 * 微信用户服务，CRM部份
 * @author laidingqing
 *
 */
@Service("weixinUserService")
public class WeixinUserService {

	private static final Logger logger = LoggerFactory.getLogger(WeixinUserService.class);
	
	@Value("#{envProps.weixin_api_uri}")
	private String weixinApiURI;
	
	@Autowired
	private WeixinUserDao userDao;
	
	@Autowired
	private TenancyUserDao tuserDao;
	
	/**
	 * 登记一个微信OpenID,并向微信获取基本信息.
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	public WeixinUser subcribe(WeixinUser user) throws ServiceException{
		
		logger.debug("subcribe...." + new Date());
		List<WeixinUser> users = userDao.findByOpenid(user.getOpenid());
		if( users.size() > 0){
			//获取第一条、重新获取信息并更新状态
			WeixinUser originWinxinUser = users.get(0);
			originWinxinUser.setStatus(WeixinUser.WEIXIN_USER_STATUS_NORMAL);
			WeixinUser fetchUser = getWeixinUserInfo(user.getOpenid(), user.getUid());
			//重新赋值给originWeixinUser保持最新用户信息
			
			
		}else{
			
		}
		
		return user;
	}
	
	/**
	 * 向微信服务发起请求用户信息并封装返回。
	 * @param openId
	 * @param uid
	 * @return
	 * @throws ServiceException
	 */
	private WeixinUser getWeixinUserInfo(String openId, long uid) throws ServiceException{
		//TODO
		return null;
	}
	
	public WeixinUser getByOpenid(String openid){
		List<WeixinUser> users = userDao.findByOpenid(openid);
		return users.size()>0?users.get(0):null;
	}
	/**
	 * 保存微信用户信息 同步绑定 或者 更新app用户信息  
	 * @param user
	 */
	public void saveWeixinUser(WeixinUser user){
		if(user == null){
			return;
		}
		TenancyUser tuser = tuserDao.findByOpenid(user.getOpenid());
		//微信用户信息同步到 会员用户信息
		if(tuser != null){
			tuser.setUptDate(new Date());
		}else{
			tuser = new TenancyUser();
			tuser.setOpenid(user.getOpenid());
			tuser.setCreateDate(new Date());
			tuser.setSex(user.getSex());
			tuser.setUid(user.getUid());
			tuser.setStatus(TenancyUser.normal);
		}
		tuser.setNickname(user.getNickname());//同步昵称
		tuser.setAvatar(user.getHeadimgurl());//同步头像
		tuserDao.save(tuser);
		
		user.setStatus(WeixinUser.WEIXIN_USER_STATUS_NORMAL);
		userDao.save(user);
	}
	
	/**
	 * 根据用户发送的位置更新当前用户的位置属性。
	 * @param context
	 * @param lat
	 * @param lng
	 * @return
	 */
	public WeixinUser updateByLocation(WebContext context, BigDecimal lat, BigDecimal lng){
		
		return null;//TODO
	}
}
