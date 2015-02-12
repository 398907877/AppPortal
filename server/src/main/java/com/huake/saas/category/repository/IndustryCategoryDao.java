package com.huake.saas.category.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.base.repository.GenericRepository;
import com.huake.saas.category.entity.IndustryCategory;

public interface IndustryCategoryDao extends GenericRepository<IndustryCategory, Long>,PagingAndSortingRepository<IndustryCategory, Long>, JpaSpecificationExecutor<IndustryCategory>{

}
