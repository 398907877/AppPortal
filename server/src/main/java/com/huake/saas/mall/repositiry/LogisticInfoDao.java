package com.huake.saas.mall.repositiry;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.base.repository.GenericRepository;
import com.huake.saas.mall.entity.LogisticInfo;

public interface LogisticInfoDao extends GenericRepository<LogisticInfo, Integer> ,PagingAndSortingRepository<LogisticInfo,Integer>,JpaSpecificationExecutor<LogisticInfo>{

}
