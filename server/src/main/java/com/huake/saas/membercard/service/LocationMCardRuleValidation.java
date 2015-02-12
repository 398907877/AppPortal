package com.huake.saas.membercard.service;

import com.huake.base.ServiceException;
import com.huake.saas.user.entity.TenancyUser;

/**
 * 位置规则，适用于领取会员卡
 * @author skylai
 *
 */
public class LocationMCardRuleValidation implements ReceiveMCardRule{

	@Override
	public boolean check(TenancyUser user) throws ServiceException {
		return false;
	}

}
