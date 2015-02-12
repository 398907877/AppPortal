package com.huake.saas.baidu.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.baidu.entity.BaiduDevice;
/**
 * @author wujiajun
 * @time 2014-5
 * @Description  dao层  继承jpa
 * */
public interface BaiduPushDao extends PagingAndSortingRepository<BaiduDevice, Long>,JpaSpecificationExecutor<BaiduDevice>{

	
	
}
