package com.huake.saas.invitation.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.invitation.entity.InvitationReply;

public interface InvitationReplyDao extends PagingAndSortingRepository<InvitationReply, Long>,JpaSpecificationExecutor<InvitationReply>{
	
	@Query("from InvitationReply c where c.invitationId= :invitationId and c.status= :status order by c.crtDate asc")
    List<InvitationReply> findInvitationReplyByInvitationIdAndStatus(@Param("invitationId")Long invitationId,@Param("status")String status);
}
