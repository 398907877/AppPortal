package com.huake.saas.coupon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.huake.saas.coupon.entity.Coupon;
import com.huake.saas.coupon.repository.CouponDao;

@Component
@Transactional
public class CouponService {

	@Autowired
	private CouponDao couponDao;
	
	public List<Coupon> queryAllCouponsByUser(Long memberId) {
		
		return null;
	}
}
