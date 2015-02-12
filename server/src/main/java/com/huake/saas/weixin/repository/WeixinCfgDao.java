package com.huake.saas.weixin.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.weixin.model.WeixinCfg;

/**
 * 微信配置表数据DAO
 * @author laidingqing
 *
 */
public interface WeixinCfgDao extends PagingAndSortingRepository<WeixinCfg, Long>,JpaSpecificationExecutor<WeixinCfg>{

	public WeixinCfg findByUid(long uid);
}
