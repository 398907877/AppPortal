package com.huake.saas.auth.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.auth.entity.Auth;

/**
 * 
 * @author hyl
 * 
 *
 */
public interface AuthDao extends PagingAndSortingRepository<Auth, Long>,JpaSpecificationExecutor<Auth>{

	
	
	@Modifying
	@Query("update Auth auth set auth.status= :status where auth.uid= :uid")
	void delByTeancyId(@Param("status") int status , @Param("uid") String uid);
	
	@Query("select a from Auth a where a.status=1 and a.uid= :uid")
	Auth findByUid(@Param("uid") String uid);
	
}
