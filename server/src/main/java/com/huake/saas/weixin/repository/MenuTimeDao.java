package com.huake.saas.weixin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.weixin.model.MenuTime;


public interface MenuTimeDao extends PagingAndSortingRepository<MenuTime, Long>,JpaSpecificationExecutor<MenuTime> {

	
	/**
	 * 
	 * 获取当前uid下面的 时间！只存在一个
	 */
	MenuTime findByUid(Long uid);
}
