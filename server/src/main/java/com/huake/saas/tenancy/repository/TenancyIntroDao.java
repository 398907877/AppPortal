package com.huake.saas.tenancy.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.tenancy.entity.TenancyIntro;

/**
 * 租户详细资料
 * @author laidingqing
 *
 */
public interface TenancyIntroDao extends PagingAndSortingRepository<TenancyIntro, Long>,JpaSpecificationExecutor<TenancyIntro>{

}
