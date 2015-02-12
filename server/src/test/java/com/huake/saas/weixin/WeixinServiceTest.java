package com.huake.saas.weixin;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huake.saas.BaseTransactionalTestCase;
import com.huake.saas.base.annonation.TargetAnnonationService;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.weixin.model.WeixinCfg;
import com.huake.saas.weixin.service.WeixinService;

public class WeixinServiceTest extends BaseTransactionalTestCase{

	@Autowired
	private WeixinService weixinService;
	
	private Tenancy tenancy;
	
	@Autowired
	private TargetAnnonationService targetAnnonationService;
	
	//@Before
	public void setup(){
		tenancy = new Tenancy();
		tenancy.setUid(new Long("20140417122551"));
	}
	
	//@Test
	public void queryWeixinCfg() throws Exception{
		WeixinCfg cfg = weixinService.getWeixinCfg(tenancy.getUid());
		
	}
	@Test
	public void testAnno(){
		targetAnnonationService.getTargetInterfaceMethods("newsWeixinTarget");
	}
}
