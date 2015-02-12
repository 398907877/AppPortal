package com.huake.saas.base.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.base.entity.BizMappingCfg;

/**
 * 业务映射数据操作表
 * @author laidingqing
 *
 */
public interface BizMappingCfgDao extends PagingAndSortingRepository<BizMappingCfg, Long>, JpaSpecificationExecutor<BizMappingCfg> {

	BizMappingCfg findByBizCode(String bizCode);
}
