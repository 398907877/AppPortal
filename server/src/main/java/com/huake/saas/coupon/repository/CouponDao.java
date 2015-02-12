package com.huake.saas.coupon.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.coupon.entity.Coupon;

public interface CouponDao extends PagingAndSortingRepository<Coupon, Long>,JpaSpecificationExecutor<Coupon>{

}
