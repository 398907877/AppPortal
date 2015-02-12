package com.huake.saas.groupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.groupon.entity.GroupOnFields;


public interface GroupOnFieldsDao  extends PagingAndSortingRepository<GroupOnFields, Long>, JpaSpecificationExecutor<GroupOnFields>{
	
	List<GroupOnFields> findByGroupOnId(Long groupOnId);
}
