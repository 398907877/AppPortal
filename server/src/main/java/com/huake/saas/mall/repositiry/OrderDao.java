package com.huake.saas.mall.repositiry;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.base.repository.GenericRepository;
import com.huake.saas.mall.entity.Order;

public interface OrderDao extends GenericRepository<Order, Long> ,PagingAndSortingRepository<Order, Long>,JpaSpecificationExecutor<Order>{
	
}
