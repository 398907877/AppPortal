package com.huake.saas.tcustomer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.tcustomer.entity.CustomerType;


public interface CustomerTypeDao  extends PagingAndSortingRepository< CustomerType, Long>,JpaSpecificationExecutor<CustomerType> {

	
	List<CustomerType> findListByUidAndStatus(long id, int status);
	
}
