package com.huake.saas.membercard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.huake.base.ServiceException;
import com.huake.saas.membercard.entity.MCardRule;
import com.huake.saas.membercard.repository.MCardRuleDao;
import com.huake.saas.user.entity.TenancyUser;

/**
 * 校验会员卡申请权限
 * 规则包含：积分是否达到，是否有消费，
 * @author skylai
 *
 */
@Component
public class ValidationMCardRule {

	@Autowired
	private MCardRuleDao mCardRuleDao;
	/**
	 * 根据多重规则校验
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	public boolean validation(TenancyUser user, long id) throws ServiceException{
		Assert.isNull(user, "未知对象");
		Assert.isNull(user.getUid(), "非法的租户");
		List<ReceiveMCardRule> rules = generateRuleById(id);
		for(ReceiveMCardRule rule : rules){
			boolean result = rule.check(user);
			if(result) continue;
			break;
		}
		return false;
	}

	private List<ReceiveMCardRule> generateRuleById(long id){
		List<ReceiveMCardRule> rules = new ArrayList<ReceiveMCardRule>();
		List<MCardRule> cardRules = mCardRuleDao.findByCardId(id);
		for(MCardRule originRule: cardRules){
			//构造校验接口实例
			if( MCardRule.RULE_INTEGRAL.equals(originRule.getName())){
				//构造积分
			}else if(MCardRule.RULE_LOCATION.equals(originRule.getName())){
				LocationMCardRuleValidation locatonValidation = new LocationMCardRuleValidation();
				rules.add(locatonValidation);
			}else if(MCardRule.RULE_PAYMENT.equals(originRule.getName())){
				PaymentMCardRuleValidation payValidation = new PaymentMCardRuleValidation();
				//TODO
				rules.add(payValidation);
			}
		}
		return rules;
	}
	
	
}
