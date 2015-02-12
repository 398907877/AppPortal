package com.huake.saas.activity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.activity.entity.EventJoin;
import com.huake.saas.base.repository.GenericRepository;

public interface EventJoinDao extends GenericRepository<EventJoin, Long> ,PagingAndSortingRepository<EventJoin, Long>,JpaSpecificationExecutor<EventJoin>{
	
	EventJoin findByEventIdAndMid(Long eventId,Long mid);
	
	List<EventJoin> findByUidAndMid(Long uid,Long mid);
	
	List<EventJoin> findByEventId(Long eventId);
}
