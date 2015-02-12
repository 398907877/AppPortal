package com.huake.saas.supply.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.supply.entity.SupplyDemandType;

public interface SupplyDemandTypeDao extends PagingAndSortingRepository<SupplyDemandType, Long>, JpaSpecificationExecutor<SupplyDemandType>{
	
	@Query("from SupplyDemandType c where c.status= :status")
    List<SupplyDemandType> findSupplyDemandTypesByStatus(@Param("status")int status);
	
	@Query("from SupplyDemandType c where c.status= :status and c.type= :type")
    List<SupplyDemandType> findSupplyDemandTypesByStatusAndType(@Param("status")int status,@Param("type")int type);
}
