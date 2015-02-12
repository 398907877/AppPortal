package com.huake.saas.location.service;

import java.math.BigDecimal;

import com.huake.base.ServiceException;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.AbstractWeixinMessage;

/**
 * 业务服务结合位置接口定义
 * @author laidingqing
 *
 * @param <T>
 */
public interface LocationRuleHandleService<T> {

	public AbstractWeixinMessage locationHandle(BigDecimal lat, BigDecimal lng, BigDecimal scale, WebContext context) throws ServiceException; 
}
