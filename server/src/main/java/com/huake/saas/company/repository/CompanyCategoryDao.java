package com.huake.saas.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.company.entity.CompanyCategory;

public interface CompanyCategoryDao extends PagingAndSortingRepository<CompanyCategory, Long>, JpaSpecificationExecutor<CompanyCategory> {
	
	@Query("from CompanyCategory c where c.status= :status")
    List<CompanyCategory> findCompanyCategroysByStatus(@Param("status")  String status);
	
	@Query("from CompanyCategory c where c.status= :status and c.uid= :uid")
    List<CompanyCategory> findCompanyCategroysByStatusAndUid(@Param("status")String status,@Param("uid")String uid);
}
