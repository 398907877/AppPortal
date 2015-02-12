package com.huake.saas.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.auth.entity.AuthCfg;

public interface AuthCfgDao extends PagingAndSortingRepository<AuthCfg, Long>,JpaSpecificationExecutor<AuthCfg>{
	
	@Query("select a from AuthCfg a where a.authId = :authId and bizcode = :bizcode ")
	AuthCfg findByAuthId(@Param("authId")Long authId,@Param("bizcode")String bizcode);
	
	@Query("select a from AuthCfg a where a.authId = :authId")
	List<AuthCfg> findAll(@Param("authId")Long authId);
}
