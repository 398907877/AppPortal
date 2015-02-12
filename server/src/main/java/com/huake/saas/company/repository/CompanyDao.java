package com.huake.saas.company.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.company.entity.Company;

public interface CompanyDao extends PagingAndSortingRepository<Company, Long>, JpaSpecificationExecutor<Company>{

	public Company findByUid(String uid);
}
