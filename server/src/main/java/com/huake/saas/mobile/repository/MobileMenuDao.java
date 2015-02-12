package com.huake.saas.mobile.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.mobile.entity.MobileMenu;

/**
 * 手机网站菜单数据操作
 * @author laidingqing
 *
 */
public interface MobileMenuDao extends PagingAndSortingRepository<MobileMenu, Long>, JpaSpecificationExecutor<MobileMenu>{

}
