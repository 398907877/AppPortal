/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.huake.saas.account.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.huake.saas.account.entity.User;
import com.huake.saas.account.repository.UserDao;
import com.huake.saas.account.service.ShiroDbRealm.ShiroUser;
import com.huake.saas.base.MemcachedObjectType;
import com.huake.saas.base.service.ServiceException;

import org.springside.modules.cache.memcached.SpyMemcachedClient;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	private UserDao userDao;
	
	private Clock clock = Clock.DEFAULT;

	@Autowired(required = false)
	private SpyMemcachedClient memcachedClient;
	
	private final JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
	
	
	/**
	 * 查询用户
	 * @return
	 */
	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}
	
	/**
	 * 通过ID查询用户
	 * @param id
	 * @return
	 */
	public User getUser(Long id) {
		return userDao.findOne(id);
	}

	/**
	 * 通过登入名查询用户
	 * @param loginName
	 * @return
	 */
	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}
	/**
	 * 通过登入名与租户ID查询用户
	 * @param loginName
	 * @return
	 */
	public User findUserByLoginNameAndUid(String loginName,long id){
		return userDao.findByLoginNameAndUid(loginName,id);
	}
	
	

	/**
	 * 注册一个用户
	 * @param user
	 */
	public void registerUser(User user) {
		entryptPassword(user);
		user.setRegisterDate(clock.getCurrentDate());
		userDao.save(user);
	}
    /**
     * 修改用户
     * @param user
     */
	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userDao.save(user);
	}

	/**
	 * 删除用户
	 * @param id
	 */
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
		
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		User user = this.getUser(id);
		return user.getRoles().equals("admin");
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 通过查询缓存的用户
	 * @param id
	 * @return
	 */
	private User findByidWithMemcached(Long id){
        String key = MemcachedObjectType.TENANCY.getPrefix() + id.toString();
        System.out.println("zzzzzzzzzzzzzzz"+key+"zzzzzzzzzzzzzzz");
		String jsonString = memcachedClient.get(key);
		if (jsonString != null) {
			return jsonMapper.fromJson(jsonString, User.class);
		} else {
			User user = userDao.findOne(id);
			if (user != null) {
				jsonString = jsonMapper.toJson(user);
				memcachedClient.set(key, MemcachedObjectType.TENANCY.getExpiredTime(), jsonString);
			}
			return user;
		}
	}
	
	/**
	 * 根据条件分页查询用户
	 * @param userId
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<User> findByCondition(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType){
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<User> spec = buildSpecification(userId,searchParams);
		return userDao.findAll(spec, pageRequest);	
	}
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "registerDate");
		} else if ("createDate".equals(sortType)) {
			sort = new Sort(Direction.DESC, "registerDate");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	
	/**
	 * 创建动态查询条件组合.
	 * @param userId 
	 */
	private Specification<User> buildSpecification( Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		if(userId!=null&&!"".equals(userId)){
			User user = getUser(userId);
			if(!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())){
				filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));
			}
		}
		Specification<User> spec = DynamicSpecifications.bySearchFilter(filters.values(), User.class);
		return spec;
	}
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}
}
