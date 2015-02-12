package com.huake.saas.mobileResource.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.mobileResource.entity.MobileResource;



public interface MobileResourceDao extends PagingAndSortingRepository<MobileResource, Long>, JpaSpecificationExecutor<MobileResource>{

	
	 MobileResource findByRoleAndBizCode(String role,String bizCode);
}
