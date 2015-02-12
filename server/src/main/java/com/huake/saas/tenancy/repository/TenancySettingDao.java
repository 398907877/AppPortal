package com.huake.saas.tenancy.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.tenancy.entity.TenancySetting;

public interface TenancySettingDao extends PagingAndSortingRepository<TenancySetting, Long>,JpaSpecificationExecutor<TenancySetting>{

}
