package com.huake.saas.ernie.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.ernie.entity.Ernie;

public interface ErnieDao extends PagingAndSortingRepository<Ernie, Long>,JpaSpecificationExecutor<Ernie>{

	@Query("select a from Ernie a where a.uid = :uid and a.status='1'")
	public Page<Ernie> findByUid(@Param("uid") Long uid, Pageable pageable);

	public List<Ernie> findByUidAndStatus(Long currentUID, String statusValide);
	
	
} 
