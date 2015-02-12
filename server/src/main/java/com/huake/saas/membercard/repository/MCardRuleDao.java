package com.huake.saas.membercard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.base.repository.GenericRepository;
import com.huake.saas.membercard.entity.MCardRule;
import com.huake.saas.membercard.entity.Mcard;

public interface MCardRuleDao extends GenericRepository<MCardRule, Long> ,PagingAndSortingRepository<MCardRule, Long>,JpaSpecificationExecutor<Mcard>{

	public List<MCardRule> findByCardId(long cardId);
}
