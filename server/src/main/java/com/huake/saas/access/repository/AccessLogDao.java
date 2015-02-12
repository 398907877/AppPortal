package com.huake.saas.access.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.access.entity.AccessLog;
import com.huake.saas.base.repository.GenericRepository;

public interface AccessLogDao extends GenericRepository<AccessLog, Long> ,PagingAndSortingRepository<AccessLog, Long>,JpaSpecificationExecutor<AccessLog>{

}
