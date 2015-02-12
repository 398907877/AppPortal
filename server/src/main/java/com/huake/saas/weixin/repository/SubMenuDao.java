package com.huake.saas.weixin.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.weixin.model.SubMenu;


/**
 * 微信菜单
 * @author wujiajun
 *
 */
public interface SubMenuDao extends PagingAndSortingRepository<SubMenu, Long>,JpaSpecificationExecutor<SubMenu>{

	public SubMenu findById(long id);

}
