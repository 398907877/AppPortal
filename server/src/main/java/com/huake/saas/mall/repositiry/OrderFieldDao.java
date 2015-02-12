package com.huake.saas.mall.repositiry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.base.repository.GenericRepository;
import com.huake.saas.mall.entity.OrderField;

public interface OrderFieldDao extends GenericRepository<OrderField, Long> ,PagingAndSortingRepository<OrderField,Long>,JpaSpecificationExecutor<OrderField>{
	
	@Modifying
	@Query("delete from OrderField ofd where ofd.orderProductId= :id")
	void deleteByOrderProductId(@Param("id")Long id);
	
	public List<OrderField> findByOrderProductId(Long orderProductId);
}
