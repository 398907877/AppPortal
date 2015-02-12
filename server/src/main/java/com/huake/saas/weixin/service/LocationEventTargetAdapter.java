package com.huake.saas.weixin.service;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huake.saas.location.entity.LocationRule;
import com.huake.saas.location.service.LocationRuleHandleService;
import com.huake.saas.location.service.LocationRuleService;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.AbstractWeixinMessage;
import com.huake.saas.weixin.model.FromWeixinLocationMessage;

/**
 * 接收微信用户位置事件业务实现。
 * @author laidingqing
 *
 */
@Service("locationEventTargetAdapter")
public class LocationEventTargetAdapter implements MessageRender {

	private final static Logger log = LoggerFactory.getLogger(LocationEventTargetAdapter.class);
	
	@Autowired
	private LocationRuleService ruleService;
	
	@Autowired
    private WeixinUserService weixinUserService;
	
	@Resource(name = "locationRule")
	private Map<String, Object> locationRule;
	
	@Override
	public AbstractWeixinMessage render(AbstractWeixinMessage message) {
		if( message instanceof FromWeixinLocationMessage){
			FromWeixinLocationMessage from = (FromWeixinLocationMessage)message;
			WebContext context = WebContext.getInstance();
			if( context.getUid() == null ){
				log.error("无效的请求标识,uid is null");
				return null;
			}
			log.debug("LocationEventTargetAdapter :" + context.getUid());
			//TODO 待保存微信用户位置信息
			weixinUserService.updateByLocation(context, from.getLatitude(), from.getLongitude());
			LocationRule rule = ruleService.findLocationByValid(new Long(context.getUid()));
			if( null == rule) return null;
			String beanName = rule.getRuleName();
			log.debug("proxy bean name:" + beanName);
			LocationRuleHandleService<?> ruleHandle = (LocationRuleHandleService<?>)locationRule.get(beanName);
			return ruleHandle.locationHandle(from.getLatitude(), from.getLongitude(), from.getScale(), context);
		}
		return null;
	}

}
