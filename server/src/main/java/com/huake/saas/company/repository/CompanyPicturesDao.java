package com.huake.saas.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.company.entity.CompanyPictures;

public interface CompanyPicturesDao extends PagingAndSortingRepository<CompanyPictures, Long>, JpaSpecificationExecutor<CompanyPictures>{
	@Query("select c from CompanyPictures c where c.companyId= :companyid")
    List<CompanyPictures> findCompanyPicturesByCompanyId(@Param("companyid")Integer companyid);
}
