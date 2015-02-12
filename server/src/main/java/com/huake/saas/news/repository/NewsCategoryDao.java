package com.huake.saas.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.news.entity.NewsCategory;

public interface NewsCategoryDao extends PagingAndSortingRepository<NewsCategory, Long>, JpaSpecificationExecutor<NewsCategory>{
	@Query("from NewsCategory c where c.status= :status")
    List<NewsCategory> findNewsCategroysByStatus(@Param("status")String status);
	
	@Query("from NewsCategory c where c.status= :status and c.uid= :uid")
    List<NewsCategory> findNewsCategroysByStatusAndUid(@Param("status")String status,@Param("uid")String uid);
}
