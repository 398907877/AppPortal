/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.huake.saas.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.account.entity.User;
import com.huake.saas.base.repository.GenericRepository;

public interface UserDao extends GenericRepository<User, Long>,PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {
    
	User findByLoginName(String loginName);
	
    List<User> findByUid(long uid);
	
    User findByLoginNameAndUid(String loginName,Long uid);
	
}
