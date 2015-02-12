package com.huake.saas.seq;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.huake.saas.BaseTransactionalTestCase;
import com.huake.saas.seq.service.SeqRefService;

@TransactionConfiguration(defaultRollback=false)
public class SeqGeneratorTest extends BaseTransactionalTestCase{
	
	@Autowired
	private SeqRefService refService;
	

	@Test
	public void testGenSeqNO(){
		String id = refService.getMCardNO();
		System.out.println("===:" + id);
		id = refService.getOrderNO();
		System.out.println("===:" + id);
	}
}
