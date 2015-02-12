package com.huake.saas.ernie.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.ernie.entity.ErnieLog;

public interface ErnieLogDao extends PagingAndSortingRepository<ErnieLog, Long>,JpaSpecificationExecutor<ErnieLog>{

	@Query("select a from ErnieLog a where a.uid = :uid ")
	Page<ErnieLog> findByUid(@Param("uid")Long uid, Pageable pageable);
	
	@Query("select a from ErnieLog a where a.uid = :uid and a.erineId = :erineId order by createdAt desc")
	Page<ErnieLog> findByUidAndErineId(@Param("uid")Long uid, @Param("erineId")Long erineId, Pageable pageable);

	@Query("select el from ErnieLog el where el.erineId = :ernieId")
	Page<ErnieLog> findByErnieId(@Param("ernieId")Long ernieId,Pageable pageable);
	
	@Query("select el from ErnieLog el where el.erineId = :ernieId and el.memberId = :memberId order by createdAt desc")
	List<ErnieLog> findByMemberIdAndErnieId(@Param("ernieId")Long ernieId,@Param("memberId")Long memberId);
}
