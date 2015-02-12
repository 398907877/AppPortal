package com.huake.saas.weixin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.weixin.model.WeixinKeywordRule;

/**
 * 关键字回复数据访问接口
 * @author laidingqing
 *
 */
public interface WeixinKeywordRuleDao extends PagingAndSortingRepository<WeixinKeywordRule, Long>,JpaSpecificationExecutor<WeixinKeywordRule>{

	/**
	 * 获取租户下所有关键字
	 * @param uid
	 * @return
	 */
	List<WeixinKeywordRule> findByUid(Long uid);
	/**
	 * 分页获取租户下所有定义规则
	 * @param uid
	 * @param pageable
	 * @return
	 */
	Page<WeixinKeywordRule> findByUid(Long uid, Pageable pageable);
	
	WeixinKeywordRule findById(Long id);
	//根据
	
}
