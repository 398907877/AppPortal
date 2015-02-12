package com.huake.saas.redis.repository;


import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.huake.saas.auth.entity.NewsAuthCfg;

import redis.clients.jedis.Jedis;


@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(defaultRollback = false)
public class TenancyRepositoryImplTest extends SpringTransactionalTestCase{

	@Autowired
	private AuthCfgRepository<NewsAuthCfg> authCfgRepository;
	 @Autowired  
	 private JedisConnectionFactory jedisConnectionFactory; 
	@Test
	public void testPutTenancy(){
		Long uid = new Long( "20140417122551");
		Jedis j = jedisConnectionFactory.getShardInfo().createResource();
		System.out.println(j.ping());
		
		long dbSizeStart = j.dbSize();  
        System.out.println(dbSizeStart);  
  
        
        NewsAuthCfg authCfg = new NewsAuthCfg();
        authCfg.setUid(uid.toString());
        authCfg.setDayLimit(10);
        authCfg.setMonthVideoLimit(100);
        //authCfgRepository.putAuthCfg(authCfg);
        
		System.out.println(uid);
		
	}
	
	@Test
	public void testGetAuthCfg(){
		NewsAuthCfg authCfg = authCfgRepository.getAuthCfg("20140417122551");
		System.out.println(authCfg.getDayLimit());
		System.out.println(authCfg.getUid());
		
	}
	
	@Test
	public void testUpdateAuthCfg(){
		NewsAuthCfg authCfg = new NewsAuthCfg();
        authCfg.setUid("20140417122551");
        authCfg.setDayLimit(10);
        authCfg.setMonthVideoLimit(10);
        authCfgRepository.updateAuthCfg(authCfg);;
		
	}
	
	@Test 
	public void testRemoveAuthCfg(){
		NewsAuthCfg authCfg = new NewsAuthCfg();
        authCfg.setUid("20140417122551");
	}
}
