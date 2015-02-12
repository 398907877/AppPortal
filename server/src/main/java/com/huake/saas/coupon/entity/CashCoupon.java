package com.huake.saas.coupon.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 现金抵用券
 * @author laidingqing
 *
 */
@Entity
@DiscriminatorValue(Coupon.COUPON_CATEGORY_CASH)
public class CashCoupon extends Coupon {

}
