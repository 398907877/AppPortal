package com.huake.saas.coupon.service;

import com.huake.saas.weixin.annonation.WeixinTarget;
import com.huake.saas.weixin.service.WeixinTargetIn;

@WeixinTarget("couponWeixinTarget")
public interface CouponWeixinTarget extends WeixinTargetIn{

	//TODO 实现关键字领券/关键字查看券等
}
