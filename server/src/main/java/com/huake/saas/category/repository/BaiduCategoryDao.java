package com.huake.saas.category.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


import com.huake.saas.base.repository.GenericRepository;
import com.huake.saas.category.entity.BaiduCategory;

/**
 * @author wujiajun
 * @time    2014-06-03
 * @description  dao
 */
public interface 	BaiduCategoryDao extends GenericRepository<BaiduCategory, Long>,PagingAndSortingRepository<BaiduCategory, Long>, JpaSpecificationExecutor<BaiduCategory>{

}
