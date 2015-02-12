package com.huake.saas.tenancy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.tenancy.entity.Tenancy;

/**
 * 租户实体DAO
 * @author laidingqing
 *
 */
public interface TenancyDao extends PagingAndSortingRepository<Tenancy, Long>,JpaSpecificationExecutor<Tenancy>{

    @Modifying
    @Query("from Tenancy tenancy where tenancy.status= :nodel")
    List<Tenancy> findAllBystatus(@Param("nodel")String nodel);

	Tenancy findByUid(long uid);
	
}
