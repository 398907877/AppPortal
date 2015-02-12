package com.huake.saas.checkin.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.huake.base.ServiceException;
import com.huake.saas.checkin.entity.CheckIn;
import com.huake.saas.location.service.AbstractLocationHandleService;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.AbstractWeixinMessage;

/**
 * 位置服务对应签到实现
 * @author laidingqing
 *
 */
@Service("locationCheckInHandleService")
public class LocationCheckInHandleService extends  AbstractLocationHandleService<CheckIn>{

	@Override
	public AbstractWeixinMessage locationHandle(BigDecimal lat, BigDecimal lng, BigDecimal scale, WebContext context) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	
}
