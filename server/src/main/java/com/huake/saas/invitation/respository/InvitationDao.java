package com.huake.saas.invitation.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.invitation.entity.Invitation;

public interface InvitationDao extends PagingAndSortingRepository<Invitation, Long>,JpaSpecificationExecutor<Invitation>{

	@Query("select i from Invitation i where i.status = :status and i.uid = :uid")
	Page<Invitation> findInvitation(@Param("status")String status,@Param("uid")String uid,Pageable pageable);
	
	@Query("select count(*) from Invitation i where i.status = :status and i.uid = :uid")
	int count(@Param("uid")String uid,@Param("status")String status);
	
	
}
