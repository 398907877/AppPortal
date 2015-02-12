package com.huake.saas.mall.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huake.saas.user.entity.TenancyUser;

/**
 * 订单实体
 * @author zjy
 *
 */
@Entity
@Table(name = "app_orders")
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 有效
	 * 无效
	 */
	public final static String STATUS_VALIDE="1";
	public final static String STATUS_INVALIDE="0";
	
	/**
	 * 未处理
	 */
	public final static Integer STATE_UNFINISHED = 0;
	/**
	 * 已付款
	 */
	public final static Integer STATE_ISSENDCASH = 1;
	/**
	 * 已收款
	 */
	public final static Integer STATE_ISACCEPTCASH = 2;
	/**
	 * 已发货
	 */
	public final static Integer STATE_ISSENDGOODS = 3;
	/**
	 * 已收货
	 */
	public final static Integer STATE_ISACCEPTGOODS = 4;
	/**
	 * 订单完成
	 */
	public final static Integer STATE_FINISHED = 5;
	/**
	 * 订单已过期
	 */
	public final static Integer STATE_EXPIRED = 6;
	/**
	 * 使用积分
	 */
	public final static String USER_INTEGRAL = "1";
	/**
	 * 不适用积分
	 */
	public final static String NO_INTEGRAL = "0";
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id", updatable = false)
	private long id;
	
	/**
	 * 订单编号
	 */
	private String orderNo;
	
	/**
	 * 卖家uid(租户id)
	 */
	private String uid;
	
	
	@ManyToOne
	@JoinColumn(name="logistic_id")
	private LogisticInfo logistic;
	

	
	/**
	 * 收获地址表Id
	 */
	private Long addId;
	
	@Transient
	private Address address;
	
	@Transient
	private List<OrderProduct> products;
	
	/**
	 * 备注
	 */
	private String message;
	/**
	 * 总价格
	 */
	private double price;
	/**
	 * 运费
	 */
	private double freight;
	/**
	 * 应付
	 */
	private double payment;
	/**
	 * 买家Id
	 */
	private Long userId;
	/**
	 * 买家实体
	 */
	@Transient
	private TenancyUser user;
	
	/**
	 * 是否使用积分 使用 1；不使用2
	 */
	private String isIntegral;
	/**
	 * 是否使用现金 使用 1；不使用2
	 */
	private String isMoney;
	
	/**
	 * 创建日期
	 */
	private Date crtDate;
	
	/**
	 * 更新日期
	 */
	private Date upDate;
	
	private String status;
	
	/**
	 * 订单积分使用数
	 */
	private Integer integralCount;
	
	/**
	 * 订单状态 1、未处理 2、用户已付款 3、商家已收款 4、商家已发货 5、用户已收货  6订单完成
	 */
	private Integer state;

	/**
	 * 取消订单原因
	 */
	private String reason;
	
	/**
	 * 运单号
	 */
	private String logisticNo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getAddId() {
		return addId;
	}

	public void setAddId(Long addId) {
		this.addId = addId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getFreight() {
		return freight;
	}

	public void setFreight(double freight) {
		this.freight = freight;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIsIntegral() {
		return isIntegral;
	}

	public void setIsIntegral(String isIntegral) {
		this.isIntegral = isIntegral;
	}

	public String getIsMoney() {
		return isMoney;
	}

	public void setIsMoney(String isMoney) {
		this.isMoney = isMoney;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public Date getUpDate() {
		return upDate;
	}

	public void setUpDate(Date upDate) {
		this.upDate = upDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIntegralCount() {
		return integralCount;
	}

	public void setIntegralCount(Integer integralCount) {
		this.integralCount = integralCount;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getLogisticNo() {
		return logisticNo;
	}

	public void setLogisticNo(String logisticNo) {
		this.logisticNo = logisticNo;
	}

	public LogisticInfo getLogistic() {
		return logistic;
	}

	public void setLogistic(LogisticInfo logistic) {
		this.logistic = logistic;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<OrderProduct> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public TenancyUser getUser() {
		return user;
	}

	public void setUser(TenancyUser user) {
		this.user = user;
	}
}
