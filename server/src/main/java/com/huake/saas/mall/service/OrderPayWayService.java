package com.huake.saas.mall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.huake.saas.mall.entity.OrderPayWay;
import com.huake.saas.mall.repositiry.OrderPayWayDao;

@Component
@Transactional
public class OrderPayWayService {
	@Autowired
	private OrderPayWayDao payWayDao;
	
	public void save(OrderPayWay payWay)
	{
		payWayDao.save(payWay);
	}
}
