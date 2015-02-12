/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.huake.saas.springHibernate;

import static org.assertj.core.api.Assertions.*;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * AccountService的测试用例, 测试Service层的业务逻辑.
 * 
 * @author calvin
 */
public class AccountServiceTest {


	@Test
	public void test() {
		
		
		ApplicationContext factory = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		SessionFactory session = (SessionFactory) factory.getBean("sessionFactory");
		
		assertThat(session).isNull();
	}

	public static void main(String[] args) {
		new AccountServiceTest().test();
	}
//	@Test
//	public void updateUser() {
		// 如果明文密码不为空，加密密码会被更新.
//		User user = UserData.randomNewUser();
//		accountService.updateUser(user);
//		assertThat(user.getSalt()).isNotNull();
//
//		// 如果明文密码为空，加密密码无变化。
//		User user2 = UserData.randomNewUser();
//		user2.setPlainPassword(null);
//		accountService.updateUser(user2);
//		assertThat(user2.getSalt()).isNull();
//	}

//	@Test
//	public void deleteUser() {
//		// 正常删除用户.
//		accountService.deleteUser(2l);
//		
//		Mockito.verify(mockUserDao).delete(2);
//
//		// 删除超级管理用户抛出异常, userDao没有被执行
//		try {
//			accountService.deleteUser(1L);
//			failBecauseExceptionWasNotThrown(ServiceException.class);
//		} catch (ServiceException e) {
//			// expected exception
//		}
//		Mockito.verify(mockUserDao, Mockito.never()).delete(1);
//	}

}
