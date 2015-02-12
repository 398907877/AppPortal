package com.huake.saas.mall.repositiry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.base.repository.GenericRepository;
import com.huake.saas.mall.entity.Address;


public interface AddressDao extends GenericRepository<Address, Long> ,PagingAndSortingRepository<Address,Long>,JpaSpecificationExecutor<Address>{
	
	public List<Address> findByMemberId(Long memberId);
}
