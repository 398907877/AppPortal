package com.huake.saas.access.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.huake.base.ServiceException;
import com.huake.saas.access.entity.AccessLog;
import com.huake.saas.access.repository.AccessLogDao;
import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.weixin.model.WeixinKeyword;
import com.huake.saas.weixin.model.WeixinKeywordRule;

/**
 * 日志存储服务，来自于JMS消息监听，建议不直接调用。
 * @author laidingqing jiajun
 *
 */
@Component
public class AccessLogService {

	@Autowired
	private AccessLogDao accessLogDao;
	@Autowired
	private AccountService accountService;
	
	/**
	 * 插入数据，不含事务
	 * @param log
	 * @throws ServiceException
	 */
	public void save(AccessLog log) throws ServiceException{
		Assert.notNull(log, "未知对象");
		log.setCreatedAt(new Date());
		accessLogDao.save(log);
	}
	
	
	/**
	 * weixinlog 开始
	 * 创建分页的 weixinlog
	 * 消息的分页 自定义查询
	 * wujiajun
	 */
	
	public Page<AccessLog> getWeixinLogBySpcPageble(Long userId, 
			Map<String, Object> searchParams,
			int pageNumber,
			int pageSize,
			String sortType){
		//TODO
		//1.pagerequest 获取
		//2.spc 获取
		
		PageRequest pageRequest = buildPageRequestWeixinLog(pageNumber, pageSize, sortType);
		Specification<AccessLog> spec = buildSpecificationWeixinLog(userId, searchParams);
		
		
		
		return accessLogDao.findAll(spec, pageRequest);
		
	} 
	
	private PageRequest buildPageRequestWeixinLog(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "reqAt");
		} else if ("reqAt".equals(sortType)) {
			sort = new Sort(Direction.DESC, "reqAt");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	private Specification<AccessLog> buildSpecificationWeixinLog( Long userId, Map<String, Object> searchParams) {
		System.out.println("user  id  ::::"+userId);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		//加上uid限制
		User user = accountService.getUser(userId);
		
		//filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));
		
		
		
		Specification<AccessLog> spec = DynamicSpecifications.bySearchFilter(filters.values(), AccessLog.class);
		return spec;
	}



	
	
	
}
