package com.huake.saas.supply.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.supply.entity.SupplyDemand;

public interface SupplyDemandDao extends PagingAndSortingRepository<SupplyDemand, Long>, JpaSpecificationExecutor<SupplyDemand> {
	
	@Query("select s from SupplyDemand s where s.type = :type and s.uid = :uid and status = :status")
	Page<SupplyDemand> findSupplyDemandByType(@Param("status") String status,@Param("type")Integer type,@Param("uid")String uid,Pageable pageable);
}
