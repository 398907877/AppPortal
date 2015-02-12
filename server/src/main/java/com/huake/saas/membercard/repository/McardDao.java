package com.huake.saas.membercard.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.activity.entity.Event;
import com.huake.saas.base.repository.GenericRepository;
import com.huake.saas.membercard.entity.Mcard;

public interface McardDao extends GenericRepository<Mcard, Long> ,PagingAndSortingRepository<Mcard, Long>,JpaSpecificationExecutor<Mcard>{

}
