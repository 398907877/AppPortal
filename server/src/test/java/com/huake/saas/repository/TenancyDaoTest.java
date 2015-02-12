package com.huake.saas.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.huake.saas.BaseTransactionalTestCase;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;
import com.huake.saas.user.service.TenancyUserService;

@TransactionConfiguration(defaultRollback = false)
public class TenancyDaoTest extends BaseTransactionalTestCase{

	@Autowired
	private TenancyService tenancyService;
	@Autowired 
	private TenancyUserService userService;
	
	
	@Test
	public void testAddTenancy(){
		Tenancy tenancy = new Tenancy();
		tenancy.setName("福州华科信息科技有限公司");
		tenancy.setAddress("福州市鼓楼区梅峰支路118号恒升大厦5楼");
		tenancy.setTel("83928188");
		tenancyService.createTenancy(tenancy);
	}
	
	@Test
	public void testFindUser(){
		System.out.println(userService.findByLoginnameAndUid("10000", new Long("20140417122551")));
	}
}
