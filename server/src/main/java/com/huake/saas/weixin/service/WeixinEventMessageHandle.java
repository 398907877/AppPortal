package com.huake.saas.weixin.service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huake.saas.tenancy.service.TenancyService;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.AbstractWeixinMessage;
import com.huake.saas.weixin.model.FromWeixinEventMessage;
import com.huake.saas.weixin.model.ToWeixinTextMessage;
import com.huake.saas.weixin.model.WeixinKeywordRule;
import com.huake.saas.weixin.model.WeixinUser;
import com.huake.saas.weixin.repository.WeixinKeywordRuleDao;

/**
 * 微信菜单事件处理，待实现，在XML配置文件中定义Bean.
 * 要点
 * 1、上下文可以使用WebContext:uid,domain
 * 2、根据EventKey决定处理逻辑
 * @author laidingqing
 *
 */
public class WeixinEventMessageHandle implements WeixinMessageHandle {

	private static final Logger logger = LoggerFactory.getLogger(WeixinEventMessageHandle.class);
	
	private TenancyService tenancyService;
	
	private MessageRender eventAdapterTarget;
	
	private WeixinUserService userService;
	
	@Autowired
	private WeixinKeywordRuleDao weixinKeywordDao;
	
	@Autowired
	private WeixinCrmNotifyMessageProducer crmProducer;
	
	@Autowired
	private WeixinService weixinService;
	
	@Override
	public AbstractWeixinMessage render(AbstractWeixinMessage message) {
		FromWeixinEventMessage fwem = null;
		WebContext context = WebContext.getInstance();
		logger.debug("process Event Message Handle...");
		if( message instanceof FromWeixinEventMessage){
			fwem = (FromWeixinEventMessage)message;
			if(fwem.getEvent().equals("CLICK")){
				return eventAdapterTarget.render(message);
			}else if( "subscribe".equals(fwem.getEvent())){
				logger.debug("关注事件触发！！");
				//关注触发事件，默认从当前租户的 keyword 中取 help，没有help的话！ 返回默认
				
			    AbstractWeixinMessage to =null;
				ToWeixinTextMessage text = new ToWeixinTextMessage();
				text.setFromUserName(message.getToUserName());
				text.setToUserName( message.getFromUserName());
				text.setCreateTime(System.currentTimeMillis());
				
				WeixinKeywordRule keyword = weixinService.getWeixinKeywordRuleByDefault(new Long(context.getUid()));
				if(keyword!=null){
					text.setContent( keyword.getText());
				}else {
					text.setContent("欢迎关注本公司微信公众账号，持续关注我们获取更多精彩！");
				}
				to = text;
			    return to;
				
			}else if( "unsubscribe".equals(fwem.getEvent())){
				logger.debug("取消关注事件触发！！");

				
				    AbstractWeixinMessage to =null;
		
					//如果这边为空的话，就调用 默认添加的那条keyword-----help
					System.out.println("到这里了");
					ToWeixinTextMessage text = new ToWeixinTextMessage();
					
					text.setFromUserName(message.getToUserName());
					text.setToUserName( message.getFromUserName());
					text.setCreateTime(System.currentTimeMillis());
					text.setContent( "感谢您订阅我们的微信公众号，更多内容请持续关注我们！");
					to = text;

				    return to;
				//TODO 设置记阅用户状态
			}
		}
		return null;
	}

	public TenancyService getTenancyService() {
		return tenancyService;
	}

	public void setTenancyService(TenancyService tenancyService) {
		this.tenancyService = tenancyService;
	}

	public MessageRender getEventAdapterTarget() {
		return eventAdapterTarget;
	}

	public void setEventAdapterTarget(MessageRender eventAdapterTarget) {
		this.eventAdapterTarget = eventAdapterTarget;
	}

	public WeixinUserService getUserService() {
		return userService;
	}

	public void setUserService(WeixinUserService userService) {
		this.userService = userService;
	}
}
