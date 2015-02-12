package com.huake.saas.location.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huake.base.ServiceException;
import com.huake.saas.base.Constants;
import com.huake.saas.location.entity.LocationRule;
import com.huake.saas.location.repository.LocationRuleDao;

/**
 * 位置规则服务
 * @author laidingqing
 *
 */
@Component
public class LocationRuleService {

	@Autowired
	private LocationRuleDao ruleDao;
	
	public LocationRule findLocationRuleByUid(Long uid) {
		List<LocationRule> rules = ruleDao.findByUidAndStatus(uid, Constants.STATUS_VALID);
		if( rules.size() >0 ){
			return rules.get(0);
		}
		return null;
	}
	
	public LocationRule findLocationByValid(long uid){
		List<LocationRule> rules = ruleDao.findByUidAndStatus(uid, Constants.STATUS_VALID);
		if( rules.size() >= 0){
			return rules.get(0);
		}
		return null;
	}
	
	public LocationRule saveLocationRule(LocationRule rule) throws ServiceException{
		
		return rule;
	}
}
