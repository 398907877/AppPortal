package com.huake.saas.mobileResource.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.huake.saas.mobileResource.entity.MobileResource;
import com.huake.saas.mobileResource.repository.MobileResourceDao;

@Component
@Transactional
public class MobileResourceService {

	
	@Autowired
	public MobileResourceDao mResourceDao;
	
	/**
	 * 通过业务ID与权限查询
	 * @param bizCode
	 * @param role
	 * @return
	 */
	public MobileResource findByBizCodeAndRole(String bizCode,String role)
	{
		return mResourceDao.findByRoleAndBizCode(role, bizCode);
	}
	
	
	public List<MobileResource> findAll()
	{
		return (List<MobileResource>) mResourceDao.findAll();
	}
	
	
	
}
