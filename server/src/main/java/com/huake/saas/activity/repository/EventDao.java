package com.huake.saas.activity.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.activity.entity.Event;

public interface EventDao extends PagingAndSortingRepository<Event, Long>,JpaSpecificationExecutor<Event>{
	//查询最新活动信息	
	@Query("select e from Event e where e.status = :status and e.uid = :uid")
	public Page<Event> findByStatus(@Param("status")String status,@Param("uid") Long uid,Pageable pageable);
		
}
