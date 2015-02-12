package com.huake.saas.weixin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.weixin.model.WeixinKeyword;
import com.huake.saas.weixin.model.WeixinMenuword;

public interface WeixinMenuwordDao extends PagingAndSortingRepository<WeixinMenuword, Long>,JpaSpecificationExecutor<WeixinMenuword>{

	List<WeixinMenuword> findByUidAndWord(Long uid, String word);
	
	@Query("from WeixinMenuword w Where rule.id= :id")
	List<WeixinMenuword> findByruldId(@Param("id")Long id);
} 
