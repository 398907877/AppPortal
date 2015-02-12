package com.huake.saas.invitation.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.invitation.entity.InvitationResources;

public interface InvitationResourcesDao extends PagingAndSortingRepository<InvitationResources, Long>,JpaSpecificationExecutor<InvitationResources>{
	@Query("from InvitationResources c where c.invitationId= :invitationId")
    List<InvitationResources> findInvitationResourcesByInvitationId(@Param("invitationId")Long invitationId);
}
