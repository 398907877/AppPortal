package com.huake.saas.version.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.version.entity.AppVersionInfo;


public interface AppVersionInfoDao  extends PagingAndSortingRepository<AppVersionInfo, Integer>,JpaSpecificationExecutor<AppVersionInfo>{

	@Modifying
	@Query("select vi from AppVersionInfo vi where appCategory= :category order by crtDate desc")
	public List<AppVersionInfo> findByAppCategory(@Param("category")String category);
}
