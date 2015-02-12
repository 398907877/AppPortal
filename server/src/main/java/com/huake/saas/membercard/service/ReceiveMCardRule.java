package com.huake.saas.membercard.service;

import com.huake.base.ServiceException;
import com.huake.saas.user.entity.TenancyUser;

/**
 * 用户是否能领取会员卡规则接口，可以重叠多个规则
 * @author skylai
 *
 */
public interface ReceiveMCardRule {

	/**
	 * 根据用户上下文进行校验。
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	public boolean check(TenancyUser user) throws ServiceException;
}
