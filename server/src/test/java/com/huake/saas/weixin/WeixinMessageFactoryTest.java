package com.huake.saas.weixin;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.huake.saas.BaseTransactionalTestCase;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.AbstractWeixinMessage;
import com.huake.saas.weixin.model.FromWeixinEventMessage;
import com.huake.saas.weixin.service.EventMessageFactory;

public class WeixinMessageFactoryTest extends BaseTransactionalTestCase {

	
	@Resource( name = "eventMessageFactory")
	private EventMessageFactory eventFactory;
	
	@Test
	public void testSubscribeMessage() throws Exception{
		
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();
		
		WebContext.create(req, res, null);
		WebContext.getInstance().setUid("20140417122551");
		
		AbstractWeixinMessage message = new FromWeixinEventMessage("3333", "ewrewr", new Date(), "event", "2323", "subscribe", null);
		eventFactory.render(message);
	}
}
