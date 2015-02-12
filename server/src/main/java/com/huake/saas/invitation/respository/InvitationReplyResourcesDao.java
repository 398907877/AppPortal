package com.huake.saas.invitation.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.invitation.entity.InvitationReplyResources;

public interface InvitationReplyResourcesDao extends PagingAndSortingRepository<InvitationReplyResources, Long>,JpaSpecificationExecutor<InvitationReplyResources>{
	@Query("from InvitationReplyResources c where c.invitationReplyId= :invitationReplyId")
    List<InvitationReplyResources> findInvitationReplyResourcesByInvitationReplyId(@Param("invitationReplyId")Long invitationReplyId);
}
