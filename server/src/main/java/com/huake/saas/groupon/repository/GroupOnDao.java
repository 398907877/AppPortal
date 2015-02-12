package com.huake.saas.groupon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.groupon.entity.GroupOn;


public interface GroupOnDao extends PagingAndSortingRepository<GroupOn, Long>, JpaSpecificationExecutor<GroupOn>{
	
	@Query("select g from GroupOn g where g.uid = :uid and g.status = :status")
	Page<GroupOn> findByUid(@Param("uid")Long uid,@Param("status")String status,Pageable pageable);
}
