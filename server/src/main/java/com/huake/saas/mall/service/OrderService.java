package com.huake.saas.mall.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.mall.entity.LogisticInfo;
import com.huake.saas.mall.entity.Order;
import com.huake.saas.mall.entity.OrderField;
import com.huake.saas.mall.entity.OrderProduct;
import com.huake.saas.mall.repositiry.AddressDao;
import com.huake.saas.mall.repositiry.LogisticInfoDao;
import com.huake.saas.mall.repositiry.OrderDao;
import com.huake.saas.mall.repositiry.OrderFieldDao;
import com.huake.saas.mall.repositiry.OrderProductDao;
import com.huake.saas.product.entity.Product;
import com.huake.saas.product.service.ProductService;
import com.huake.saas.user.service.TenancyUserService;

@Component
@Transactional
public class OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private OrderProductDao orderProductDao;
	@Autowired
	private TenancyUserService userService;
	
	@Autowired 
	private OrderFieldDao fieldDao;
	
	@Autowired 
	private LogisticInfoDao infoDao;
	
	@Autowired
	private ProductService productService;
	/**
	 * 分页查找 租户的 订单
	 * @param uid 租户id
	 * @param searchParams 搜索参数
	 * @param pageNumber 页码
	 * @param pageSize	页大小
	 * @param sortType 排序字段
	 * @return
	 */
	public Page<Order> findOrder(Long uid, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType){
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Order> spec = buildSpecification(uid, searchParams);
		return orderDao.findAll(spec, pageRequest);
	}
	
	/**
	 * 根据id查找订单
	 * @param id
	 * @return
	 */
	public Order findById(Long id){
		Order order = orderDao.findOne(id);
		order.setAddress(addressDao.findOne(order.getAddId()));//订单收货地址
		order.setProducts(orderProductDao.findByOrderId(id));//查找订单商品
		order.setUser(userService.findById(order.getUserId()));
		return order;
	}
	/**
	 * 根据id删除订单
	 * @param id
	 */
	public void delOrder(Long id){
		Order order = findById(id);
		order.setStatus(Order.STATUS_INVALIDE);
		orderDao.save(order);
		
		List<OrderProduct> ops= order.getProducts();
		
		if(!Order.STATE_EXPIRED.equals(order.getState())){
			for(OrderProduct op:ops){
				Product p= productService.getProduct(op.getProductId());//更新商品信息
				p.setTotal(p.getTotal()+op.getNum().intValue());
				p.setSales(p.getSales()-op.getNum().intValue());
				productService.updateProduct(p);
				orderProductDao.delete(op.getId());
				fieldDao.deleteByOrderProductId(op.getId());
			}
			}else{
				for(OrderProduct op:ops){
					orderProductDao.delete(op.getId());
					fieldDao.deleteByOrderProductId(op.getId());
				}
			}
	}
	/**
	 * 确认收货
	 * @param id
	 */
	public void orderReceipt(Long id){
		Order order = orderDao.findOne(id);
		order.setState(Order.STATE_ISACCEPTGOODS);
		updateOrder(order);
	}
	/**
	 * 支付成功
	 * @param id
	 */
	public void paySuccess(Long id){
		Order order = orderDao.findOne(id);
		order.setState(Order.STATE_ISSENDCASH);
		updateOrder(order);
	}
	/**
	 * 真删除,并修改商品的库存，销售量
	 * @param id
	 */
	public void delOrderProduct(OrderProduct op){
		Product product=productService.getProduct(op.getProductId());
		product.setTotal(product.getTotal()+op.getNum().intValue());
		product.setSales(product.getSales()-op.getNum().intValue());
		productService.updateProduct(product);
		orderProductDao.delete(op.getId());
	}
	/**
	 * 根据订单产品id 查找订单产品
	 * @param id
	 * @return
	 */
	public OrderProduct findByOrderProductId(Long id){
		return orderProductDao.findOne(id);
	}
	/**
	 * 根据订单id查找订单商品
	 * @param oid
	 * @return
	 */
	public List<OrderProduct> findProductByOrderId(Long oid){
		return orderProductDao.findByOrderId(oid);
	}
	
	/**
	 * 根据订单商品id查找订单商品的扩展字段
	 */
	public List<OrderField> findOrderFieldByOpid(Long orderProductId){
		return fieldDao.findByOrderProductId(orderProductId);
	}
	
	/**
	 * 查找所有快递公司
	 * @return
	 */
	public List<LogisticInfo> findLogisticInfos(){
		return infoDao.findAll();
	}
	/**
	 * 根据快递公司id查找快递公司
	 * @param id
	 * @return
	 */
	public LogisticInfo findLogisticInfoById(Integer id){
		return infoDao.findOne(id);
	}
	/**
	 * 
	 * @param order 订单
	 */
	public void updateOrder(Order order){
		order.setUpDate(new Date());
		orderDao.save(order);
	}
	/**
	 * 添加订单
	 * @param order
	 */
	public void addOrder(Order order){
		order.setCrtDate(new Date());
		order.setUpDate(new Date());
		order.setStatus(Order.STATUS_VALIDE);
		orderDao.save(order);
	}
	/**
	 * 保存订单产品
	 * @param op
	 */
	public void saveOrderProduct(OrderProduct op){
		op.setCrtDate(new Date());
		orderProductDao.save(op);
	}
	/**
	 * 保存购买商品的拓展字段
	 * @param ofs
	 * @param orderProductId
	 */
	public void addFields(List<OrderField> ofs,Long orderProductId){
		for(OrderField of : ofs){
			of.setOrderProductId(orderProductId);
			fieldDao.save(of);
		}
	}
	/**
	 * 根据api的条件查找用户的所有订单
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Order> findByApiCondition(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Order> spec = buildSpecByParams(searchParams);
		return orderDao.findAll(spec, pageRequest);
	}
	
	/**
	 * 生成唯一不重复的订单号
	 * 398304913736    000003  
	 * 当前毫秒数后12位+会员id（填充6位或更长）组成大于等于18位的订单号
	 */
	public String getOrderNo(Long memberId) {
		String memberIdStr=memberId.toString();
		String timeStr=(System.currentTimeMillis()+"").substring(1);
		memberIdStr = memberIdStr.length()>=6?memberIdStr:String.format("%06d",memberId);
		StringBuilder sb=new StringBuilder();
		sb.append(timeStr);
		sb.append(memberIdStr);
		return sb.toString();
	}
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if (BaseEntity.PAGE_CRTDATE_DESC.equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if (BaseEntity.PAGE_CRTDATE_ASC.equals(sortType)) {
			sort = new Sort(Direction.ASC, BaseEntity.PAGE_CRTDATE_ASC);
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	/**
	 * 订单列表--查询条件组合for Api
	 */
	private Specification<Order> buildSpecByParams( Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("status", new SearchFilter("status", Operator.EQ, Order.STATUS_VALIDE));
		filters.put("state", new SearchFilter("state", Operator.LT, Order.STATE_EXPIRED));
		Specification<Order> spec = DynamicSpecifications.bySearchFilter(filters.values(), Order.class);
		return spec;
	}
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Order> buildSpecification(Long uid, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status", new SearchFilter("status", Operator.EQ, Constants.STATUS_VALID));
		return DynamicSpecifications.bySearchFilter(filters.values(), Order.class);
	}
}
