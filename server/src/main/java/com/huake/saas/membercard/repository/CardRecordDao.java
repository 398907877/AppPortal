package com.huake.saas.membercard.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.base.repository.GenericRepository;
import com.huake.saas.membercard.entity.CardRecord;

public interface CardRecordDao extends GenericRepository<CardRecord, Long> ,PagingAndSortingRepository<CardRecord, Long>,JpaSpecificationExecutor<CardRecord>{

}
