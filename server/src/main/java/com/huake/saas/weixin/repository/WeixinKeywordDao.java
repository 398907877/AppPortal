package com.huake.saas.weixin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.weixin.model.WeixinKeyword;
import com.huake.saas.weixin.model.WeixinKeywordRule;

public interface WeixinKeywordDao extends PagingAndSortingRepository<WeixinKeyword, Long>,JpaSpecificationExecutor<WeixinKeyword>{

	List<WeixinKeyword> findByUidAndWord(Long uid, String word);
	
	@Query("from WeixinKeyword w Where rule.id= :id")
	List<WeixinKeyword> findByruldId(@Param("id") Long id);
	
	
	Page<WeixinKeyword> findByUid(Long uid, Pageable pageable);
	
} 
