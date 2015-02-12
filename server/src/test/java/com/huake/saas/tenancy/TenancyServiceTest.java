package com.huake.saas.tenancy;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huake.saas.BaseTransactionalTestCase;
import com.huake.saas.tenancy.service.TenancyService;

public class TenancyServiceTest extends BaseTransactionalTestCase{

	@Autowired
	private TenancyService tenancyService;
	
	@Test
	public void testQueryTenancyWithMemcached() throws Exception{
		tenancyService.getTenancy(new Long("20140417122551"));
	}
}
