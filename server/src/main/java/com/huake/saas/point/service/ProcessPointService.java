package com.huake.saas.point.service;

import com.huake.base.ServiceException;
import com.huake.saas.point.entity.PointRule;

/**
 * 不同业务模型积分处理接口
 * @author laidingqing
 *
 * @param <T>
 */
public interface ProcessPointService<T> {
	
	public abstract void processPoint(T entity, PointRule rule) throws ServiceException;
	
}
