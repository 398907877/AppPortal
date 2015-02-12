package com.huake.saas.coupon.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 折扣券子类
 * @author laidingqing
 *
 */
@Entity
@DiscriminatorValue(Coupon.COUPON_CATEGORY_DISCOUNT)
public class DiscountCoupon extends Coupon {

}
