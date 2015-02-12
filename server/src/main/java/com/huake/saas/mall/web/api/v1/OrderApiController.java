package com.huake.saas.mall.web.api.v1;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.huake.saas.base.web.controller.BaseApiController;
import com.huake.saas.mall.entity.Address;
import com.huake.saas.mall.entity.Order;
import com.huake.saas.mall.entity.OrderField;
import com.huake.saas.mall.entity.OrderPayWay;
import com.huake.saas.mall.entity.OrderProduct;
import com.huake.saas.mall.service.AddressService;
import com.huake.saas.mall.service.OrderPayWayService;
import com.huake.saas.mall.service.OrderService;
import com.huake.saas.product.entity.Product;
import com.huake.saas.product.service.ProductService;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

/**
 * 订单api接口
 * @author zjy
 *
 */
@Controller("orderApiController")
@RequestMapping(value = "/api/v1/order")
public class OrderApiController extends BaseApiController{
	private static final Logger logger = LoggerFactory
			.getLogger(OrderApiController.class);
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private TenancyUserService userService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private OrderPayWayService payService;
	/**
	 * 生成订单（多）
	 * @param orders 订单列表
	 * @return
	 */
	@RequestMapping(value = "/addOrderList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,String> addOrderList(@RequestParam(value="userId",required=true)Long userId,@RequestBody List<Order> orders){
		Map<String,String> map = new HashMap<String, String>();
		if(orders == null || orders.size()==0)//订单列表为null或者订单列表没有订单
		{
			map.put("success", "false");
			map.put("message", "请选择订单！");
			return map;
		}	
		/**
		 * 遍历订单 判断订单中的商品库存是否足够 
		 */
		for(Order order:orders){
			List<OrderProduct> ops = order.getProducts();
			if(ops!=null){
				for(OrderProduct op:ops){
					Product p = productService.getProduct(op.getProductId());
					if(p.getTotal() == 0 || op.getNum()>p.getTotal()){
						map.put("success", Product.NO_STOCK);
						map.put("message","您选择的商品中 "+p.getTitle()+" 库存不足！");
						return map;
					}
				}
			}
		}
		/**
		 * 保存订单相关信息
		 */
		for(Order order : orders){
			/**
			 * 保存订单
			 */
	
			String orderNo = orderService.getOrderNo(userId);
			order.setUserId(userId);
			order.setOrderNo(orderNo);
			order.setState(Order.STATE_UNFINISHED);
			orderService.addOrder(order);
			/**
			 * 保存订单产品
			 */
			List<OrderProduct> ops =order.getProducts();
			if(ops != null){
				for(OrderProduct op : ops){
					Product p = productService.getProduct(op.getProductId());
					op.setOrderId(order.getId());
					op.setName(p.getTitle());
					op.setDescription(p.getIntro());
					op.setBuyLimit(p.getBuyLimit());
					orderService.saveOrderProduct(op);
					
					p.setSales(p.getSales()+op.getNum().intValue());
					p.setTotal(p.getTotal()-op.getNum().intValue());
					productService.updateProduct(p);
					
					if(op.getOrderFields()!=null&&op.getOrderFields().size()>0){
						orderService.addFields(op.getOrderFields(),op.getId());//保存购买商品的拓展字段信息
						
					}
				}
			}
			//记录日志
			TenancyUser user = userService.findById(userId);
	        StringBuffer sb = new StringBuffer();
	        sb.append("客户").append(user.getName()).append("进行订单（").append(orderNo).append(")新增");
			logger.debug(sb.toString());
		}
		return SUCCESS_RESULT;
	}
	
	/**
	 * 生成订单(单)
	 * @param order 订单
	 * @return
	 */
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,String> addOrder(@RequestParam(value="userId",required=true) Long userId,@RequestBody Order order){
		Map<String,String> map = new HashMap<String, String>();
		if(order == null)//订单列表为null或者订单列表没有订单
		{
			map.put("success", "false");
			map.put("message", "请选择订单！");
			return map;
		}	
		/**
		 * 遍历订单 判断订单中的商品库存是否足够 
		 */
		order.setUserId(userId);
		List<OrderProduct> ops=order.getProducts();
		if(ops!=null){
			for(OrderProduct op: ops){
				Product p = productService.getProduct(op.getProductId());
				if(p.getTotal() == 0 || op.getNum()>p.getTotal()){
					map.put("success", Product.NO_STOCK);
					map.put("message","您选择的商品中 "+p.getTitle()+" 库存不足！");
					return map;
				}
			}
		}
		/**
		 * 保存订单相关信息
		 */
		/**
		* 保存订单
		*/
		String orderNo=orderService.getOrderNo(userId);
		order.setOrderNo(orderNo);
		order.setState(Order.STATE_UNFINISHED);
		orderService.addOrder(order);
		/**
		 * 保存订单产品
		 */
		if(ops != null){
			for(OrderProduct op : ops){
				Product p = productService.getProduct(op.getProductId());
				op.setOrderId(order.getId());
				op.setName(p.getTitle());
				op.setDescription(p.getIntro());
				op.setBuyLimit(p.getBuyLimit());
				orderService.saveOrderProduct(op);
					
				p.setSales(p.getSales()+op.getNum().intValue());
				p.setTotal(p.getTotal()-op.getNum().intValue());
				productService.updateProduct(p);
					
				if(op.getOrderFields()!=null&&op.getOrderFields().size()>0){
					orderService.addFields(op.getOrderFields(),op.getId());//保存购买商品的拓展字段信息
						
				}
			}
		}
		//记录日志
		TenancyUser user = userService.findById(userId);
	    StringBuffer sb = new StringBuffer();
	    sb.append("客户").append(user.getName()).append("进行订单（").append(orderNo).append(")新增");
		logger.debug(sb.toString());
		return SUCCESS_RESULT;
	}
	
	/**
	 * 查看用户订单列表
	 * @param pageNum 页码
	 * @param userId 用户id
	 * @param pageSize 页大小
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET , produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Order> list(@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum,
			@RequestParam(value = "userId", required = true) Long userId,
			@RequestParam(value = "pageSize",defaultValue="10", required = false) Integer pageSize
		 ){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_userId", userId.toString());//创建查询条件
		List<Order> list = orderService.findByApiCondition(searchParams, pageNum, pageSize, "auto").getContent();
		if(list!=null && list.size()>0){
			for(Order order:list){
				List<OrderProduct> ops = orderService.findProductByOrderId(order.getId());//订单产品
				order.setProducts(ops);
				for(OrderProduct op : ops){
					op.setOrderFields(orderService.findOrderFieldByOpid(op.getId()));
				}
				if(order.getAddId()!=null){
					Address address = addressService.findById(order.getAddId());//收货地址详情
					order.setAddress(address);
				}
			}
		}
		return list;
	}
	
	/**
	 * 查看订单详情
	 * @param 订单id
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET , produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Order detail(
			@RequestParam(value = "id", required = true) Long id ){
		Order order = orderService.findById(id);
		List<OrderProduct> ops = orderService.findById(id).getProducts();
		if(ops!=null&&ops.size()>0){
			for(OrderProduct op:ops){
				List<OrderField> ofs = orderService.findOrderFieldByOpid(op.getId());
				op.setOrderFields(ofs);
			}
		}
		order.setProducts(ops);
       
		return order;
	}
	
	/**
	 * 删除订单
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delOrders",method = RequestMethod.GET , produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String, String> delete(@RequestParam(value = "ids",required = true) List<Long> ids) throws Exception{
		for (Long id : ids) {
			//记录日志
			Order order = orderService.findById(id);
			TenancyUser user = userService.findById(order.getUserId());
	        StringBuffer sb = new StringBuffer();
	        sb.append("客户").append(user.getName()).append("进行订单（").append(order.getOrderNo()).append(")删除");
			logger.debug(user.getName()+sb.toString());	
			
			orderService.delOrder(id);
		}
		return SUCCESS_RESULT;
	}
	
	/**
	 * 删除订单中选择的商品
	 * @param ids orderProduct id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delProducts",method = RequestMethod.GET , produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String, String> delProducts(@RequestParam(value = "ids",required = true) List<Long> ids) throws Exception{
		double subPay=0;//删除商品后订单扣除的金额
		Map<String,String> map = new HashMap<String, String>();
		if(ids==null||(ids!=null&&ids.size()==0)){
			map.put("success", "false");
			map.put("message", "请选择商品！");
			return map;
		}
		
		/**获取商品关联的订单*/
		OrderProduct orderProduct = orderService.findByOrderProductId(ids.get(0));
		Order order = orderService.findById(orderProduct.getOrderId());

		for (Long id : ids) {
			OrderProduct op = orderService.findByOrderProductId(id);
			subPay += op.getPrice() * op.getNum();
			orderService.delOrderProduct(op);
		}
		
		order.setPayment(order.getPayment()-subPay);//修改订单应付金额
		orderService.updateOrder(order);
		return SUCCESS_RESULT;
	}
	
	/**
	 * 确认收货
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderReceipt",method = RequestMethod.GET , produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String, String> orderReceipt(@RequestParam(value = "id",required = true) Long id) throws Exception{
		orderService.orderReceipt(id);
		//记录日志
		Order order = orderService.findById(id);
		TenancyUser user = userService.findById(order.getUserId());
		StringBuffer sb = new StringBuffer();
		sb.append("客户").append(user.getName()).append("进行订单（").append(order.getOrderNo()).append(")收货");
		logger.debug(user.getName()+sb.toString());
		return SUCCESS_RESULT;
	}
	
	/**
	 * 付款成功
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/paySuccess",method = RequestMethod.GET , produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String, String> paySuccess(@RequestParam(value = "id",required = true) Long id,
			@RequestParam(value = "returnCode",required = false) String  returnCode,
			@RequestParam(value = "out_trade_no",required = false) String  out_trade_no,
			@RequestParam(value = "total_fee",required = false) String  total_fee

			) throws Exception{
			logger.info("支付成功接口！！！");
			logger.info(returnCode);
	
			orderService.paySuccess(id);
			Order order = orderService.findById(id);
			
			OrderPayWay payWay = new OrderPayWay();
			payWay.setOrderId(order.getId());
			payWay.setPayDate(new Date());
			payWay.setPayId(out_trade_no);
			payWay.setPayMoney(total_fee);
			payWay.setReturnCode(returnCode);
			payService.save(payWay);
			
			//TODO 积分使用 与记录
			if(order.getIsIntegral().equals(Order.USER_INTEGRAL) && order.getState().equals(Order.STATE_UNFINISHED))
			{
			}
			logger.info("支付成功接口成功");
			//记录日志
			TenancyUser user = userService.findById(order.getUserId());
			StringBuffer sb = new StringBuffer();
			sb.append("客户").append(user.getName()).append("进行订单（").append(order.getOrderNo()).append(")支付");
			logger.info(user.getName()+sb.toString());
			return SUCCESS_RESULT;
		}
	

	//TODO 查看物流
	
}
