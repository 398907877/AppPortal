package com.huake.saas.mall.repositiry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.base.repository.GenericRepository;
import com.huake.saas.mall.entity.OrderProduct;

public interface OrderProductDao extends GenericRepository<OrderProduct, Long> ,PagingAndSortingRepository<OrderProduct,Long>,JpaSpecificationExecutor<OrderProduct>{
	
	public List<OrderProduct> findByOrderId(Long orderId);
}
