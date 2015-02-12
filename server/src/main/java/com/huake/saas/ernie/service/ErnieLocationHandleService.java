package com.huake.saas.ernie.service;

import java.math.BigDecimal;

import com.huake.base.ServiceException;
import com.huake.saas.ernie.entity.Ernie;
import com.huake.saas.location.service.AbstractLocationHandleService;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.AbstractWeixinMessage;

/**
 * 实现一个可以发送当前位置进行抽奖的服务。
 * @author skylai
 *
 */
public class ErnieLocationHandleService extends AbstractLocationHandleService<Ernie> {

	@Override
	public AbstractWeixinMessage locationHandle(BigDecimal lat, BigDecimal lng,
			BigDecimal scale, WebContext context) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
