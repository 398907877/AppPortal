package com.huake.saas.location.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.location.entity.LocationRule;

public interface LocationRuleDao extends PagingAndSortingRepository<LocationRule, Long>,JpaSpecificationExecutor<LocationRule>{

	@Query("select e from LocationRule e where e.uid = :uid")
	public Page<LocationRule> findAllWithPage(@Param("uid") Long uid, Pageable pageable);
	
	public List<LocationRule> findByUidAndStatus(Long uid, String status);
	
	@Modifying
	@Query("update LocationRule rule set rule.status= '0' where rule.uid= :uid")
	public void updateAllRuleWithInvalid(@Param("uid")  Long uid);
}
