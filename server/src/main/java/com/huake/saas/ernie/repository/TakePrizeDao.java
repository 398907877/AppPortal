package com.huake.saas.ernie.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.ernie.entity.TakePrize;


public interface TakePrizeDao extends PagingAndSortingRepository<TakePrize, Long>,JpaSpecificationExecutor<TakePrize>{
	@Query("select tp from TakePrize tp where tp.ernieId = :ernieId and tp.memberId = :memberId order by createdAt desc")
	TakePrize findByMemberIdAndErnieId(@Param("ernieId")Long ernieId,@Param("memberId")Long memberId);
}
