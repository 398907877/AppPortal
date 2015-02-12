package com.huake.saas.weixin.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.web.controller.BaseMobileController;
import com.huake.saas.weixin.model.AbstractWeixinMessage;
import com.huake.saas.weixin.model.BindWeixinMessage;
import com.huake.saas.weixin.model.FromWeixinEventMessage;
import com.huake.saas.weixin.model.FromWeixinImageMessage;
import com.huake.saas.weixin.model.FromWeixinLinkMessage;
import com.huake.saas.weixin.model.FromWeixinLocationMessage;
import com.huake.saas.weixin.model.FromWeixinTextMessage;
import com.huake.saas.weixin.service.EventMessageFactory;

/**
 * 微信平台接入口
 * @author laidingqing
 *
 */
@RestController("weixinApiController")
@RequestMapping(value = "/weixin")
public class WeixinApiController extends BaseMobileController{

	private static final Logger logger = LoggerFactory.getLogger(WeixinApiController.class);
	
	@Resource( name = "eventMessageFactory")
	private EventMessageFactory eventFactory;
	
	/**
	 * 业务处理,TODO 需要处理绑定到各自对象.
	 * @param fromMessage
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/", method = {RequestMethod.POST}, produces="application/xml;charset=UTF-8")
	@ResponseBody
	public AbstractWeixinMessage processEvent(@RequestBody BindWeixinMessage fromMessage, HttpServletRequest request) throws AppBizException{
		logger.debug("start process event from mp..." + fromMessage.getMessageType() + "," + fromMessage.getEvent());
		
		AbstractWeixinMessage message = null;
		
		//不好意思，这样写不是很好的.
		if("text".equals(fromMessage.getMessageType())){
			message = new FromWeixinTextMessage(fromMessage.getFromUserName(), fromMessage.getToUserName(), fromMessage.getCreateTime(),
					fromMessage.getMessageType(), fromMessage.getMessageId(), fromMessage.getContent() );
		}else if("image".equals(fromMessage.getMessageType())){
			message = new FromWeixinImageMessage(fromMessage.getFromUserName(), fromMessage.getToUserName(), fromMessage.getCreateTime(),
					fromMessage.getMessageType(), fromMessage.getMessageId(), fromMessage.getPicUrl());
		}else if("location".equals(fromMessage.getMessageType())){
			message = new FromWeixinLocationMessage(fromMessage.getFromUserName(), fromMessage.getToUserName(), fromMessage.getCreateTime(),
					fromMessage.getMessageType(), fromMessage.getMessageId(), fromMessage.getLongitude(), fromMessage.getLatitude(), 
					fromMessage.getScale(), fromMessage.getLabel());
		}else if("link".equals(fromMessage.getMessageType())){
			message = new FromWeixinLinkMessage(fromMessage.getFromUserName(), fromMessage.getToUserName(), fromMessage.getCreateTime(),
					fromMessage.getMessageType(), fromMessage.getMessageId(), fromMessage.getTitle(), fromMessage.getDescription(), fromMessage.getPicUrl());
		}else if("event".equals(fromMessage.getMessageType())){
			message = new FromWeixinEventMessage(fromMessage.getFromUserName(), fromMessage.getToUserName(), fromMessage.getCreateTime(),
					fromMessage.getMessageType(), fromMessage.getMessageId(), fromMessage.getEvent(), fromMessage.getEventKey());
		}else {
			logger.error("request body is invalided...");
			throw new AppBizException("", "request body is invalided.");//基础类要对错误进行文本信息的返回
		}
		return eventFactory.render(message);
	}
	
	/**
	 * 认证初始连接
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/", method = {RequestMethod.GET})
	@ResponseBody
	public String processEvent(HttpServletRequest request) {
		logger.debug("start process echostr...");
		return request.getParameter("echostr");
	}
	
}
