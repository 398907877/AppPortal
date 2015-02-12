package com.huake.saas.weixin.service;

import com.huake.saas.tenancy.service.TenancyService;
import com.huake.saas.weixin.model.AbstractWeixinMessage;
import com.huake.saas.weixin.model.FromWeixinTextMessage;
import com.huake.saas.weixin.model.ToWeixinTextMessage;

/**
 * 文本类型消息处理事件，参向业务处理为根据关键字定义搜索回复，或是自定义回复，TODO 待实现业务
 * @author laidingqing
 *
 */
public class WeixinTextMessageHandle implements WeixinMessageHandle {

	private TenancyService tenancyService;
	
	private MessageRender eventAdapterTarget;
	
	@Override
	public AbstractWeixinMessage render(AbstractWeixinMessage message) {
		AbstractWeixinMessage to = eventAdapterTarget.render(message);
		if( to == null){
			
			FromWeixinTextMessage meg =(FromWeixinTextMessage) message;
			//如果这边为空的话，就调用 默认添加的那条keyword-----help
			System.out.println("到这里了");
			ToWeixinTextMessage text = new ToWeixinTextMessage();
			
			text.setFromUserName(message.getToUserName());
			text.setToUserName( message.getFromUserName());
			text.setCreateTime(System.currentTimeMillis());
			//返回默认的help  没有help的话，返回默认信息（下面的这个）
			
			text.setContent( "您输入的："+meg.getContent()+"\n找不到哦！请检查一下是否输入正确哦！ ^ - ^");
			to = text;
		}
		return to;
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

}
