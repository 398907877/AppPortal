package com.huake.saas.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.product.entity.Category;

public interface CategoryDao extends PagingAndSortingRepository<Category, Long>, JpaSpecificationExecutor<Category> {
	
	@Query("from Category c where c.status= :status")
    List<Category> findCategroysByStatus(@Param("status")String status);
	
	@Query("from Category c where c.status= :status and c.uid= :uid")
    List<Category> findCategroysByStatusAndUid(@Param("status")String status,@Param("uid")String uid);
	
}
