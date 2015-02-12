package com.huake.saas.addresslist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.addresslist.entity.AddressList;
import com.huake.saas.base.repository.GenericRepository;

public interface AddressListDao extends GenericRepository<AddressList, Long> ,PagingAndSortingRepository<AddressList, Long>,JpaSpecificationExecutor<AddressList>{

	List<AddressList> findByDeptIdAndStatusAndUid(Long id,int status,Long uid);
	
	List<AddressList> findByStatusAndUid(int status,Long uid);
	
	AddressList findByUserId(Long userId);
}
