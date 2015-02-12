package com.huake.saas.coupon.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 时间体验券, TODO 可暂不实现
 * @author laidingqing
 *
 */
@Entity
@DiscriminatorValue(Coupon.COUPON_CATEGORY_TIME)
public class FreeCoupon extends Coupon {

}
