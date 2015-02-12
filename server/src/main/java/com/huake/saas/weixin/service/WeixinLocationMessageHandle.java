package com.huake.saas.weixin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.AbstractWeixinMessage;
import com.huake.saas.weixin.model.FromWeixinLocationMessage;
import com.huake.saas.weixin.web.controller.WeixinApiController;

public class WeixinLocationMessageHandle implements WeixinMessageHandle {

	private static final Logger logger = LoggerFactory.getLogger(WeixinLocationMessageHandle.class);
	private MessageRender locationEventTargetAdapter;
	
	@Override
	public AbstractWeixinMessage render(AbstractWeixinMessage message) {
		FromWeixinLocationMessage fwem = null;
		WebContext context = WebContext.getInstance();
		if( message instanceof FromWeixinLocationMessage){
			logger.debug("location...is loaded...");
			fwem = (FromWeixinLocationMessage)message;
			return locationEventTargetAdapter.render(message);
		}
		return null;
	}

	public MessageRender getLocationEventTargetAdapter() {
		return locationEventTargetAdapter;
	}

	public void setLocationEventTargetAdapter(
			MessageRender locationEventTargetAdapter) {
		this.locationEventTargetAdapter = locationEventTargetAdapter;
	}
	
}
