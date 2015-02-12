package com.huake.saas.weixin.service;

import com.huake.saas.weixin.model.AbstractWeixinMessage;

/**
 * 处理微信消息服务接口
 * @author laidingqing
 *
 */
public interface MessageRender {
	public AbstractWeixinMessage render(AbstractWeixinMessage message);
}
