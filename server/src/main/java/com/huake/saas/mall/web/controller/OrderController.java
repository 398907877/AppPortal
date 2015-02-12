package com.huake.saas.mall.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.huake.dict.service.DictViewService;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.mall.entity.LogisticInfo;
import com.huake.saas.mall.entity.Order;
import com.huake.saas.mall.service.OrderService;

@Controller
@RequestMapping("/mgr/order")
public class OrderController extends BaseController{
	private static Logger logger = LoggerFactory
			.getLogger(OrderController.class);
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private DictViewService dictViewService;
	
	/**
	 * 进入订单管理首页
	 * @param pageNumber 页码
	 * @param pageSize 页大小
	 * @param sortType 排序字段
	 * @param model 模型视图
	 * @param request 请求
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType,
			Model model, ServletRequest request){
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, Message.SEARCH_CONDITIONS);
		
		Page<Order> orders = orderService.findOrder(getCurrentUID(), searchParams,
				pageNumber, pageSize, sortType);
		
		model.addAttribute("orderState", dictViewService.getDictViewList("ORDER_STATE"));
		model.addAttribute("orders", orders);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute(Message.SEARCH_PARAMS, Servlets
						.encodeParameterStringWithPrefix(searchParams,
								Message.SEARCH_CONDITIONS));
		return "order/index";
	}
	
	/**
	 * 进入订单详细页面
	 * @param oid 订单id
	 * @param model 
	 * @return
	 */
	@RequestMapping(value="detail/{oid}",method=RequestMethod.GET)
	public String detail(@PathVariable("oid")Long oid,Model model){
		Order order = orderService.findById(oid);
		model.addAttribute("order", order);
		model.addAttribute("orderState", dictViewService.getDictViewList("ORDER_STATE"));
		
		return "order/detail";
	}
	
	/**
	 * 进入修改订单页面
	 * @param oid 订单id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="update/{oid}",method=RequestMethod.GET)
	public String update(@PathVariable("oid")Long oid,Model model){
		Order order = orderService.findById(oid);
		List<LogisticInfo> lis = orderService.findLogisticInfos();
		model.addAttribute("lis", lis);
		model.addAttribute("order", order);
		return "order/edit";
	}
	/**
	 * 保存修改的订单
	 * @param order
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(Order order, RedirectAttributes redirectAttributes){
		Order oldOrder = orderService.findById(order.getId());//查询出修改前的订单
		log(oldOrder,order);
		
		Integer state = oldOrder.getState();
		
		if (oldOrder.getIsIntegral().equals(Order.USER_INTEGRAL)  && 
				state.equals(Order.STATE_UNFINISHED) && order.getState().equals(Order.STATE_ISSENDCASH)  ) {
			// TODO 订单积分使用 待完善
		}
		oldOrder.setState(order.getState());
		oldOrder.setLogistic(orderService.findLogisticInfoById(order.getLogistic().getId()));
		oldOrder.setLogisticNo(order.getLogisticNo());
		orderService.updateOrder(oldOrder);

		if(Order.STATE_ISSENDGOODS.equals(oldOrder.getState())||Order.STATE_FINISHED.equals(oldOrder.getState())){
				//TODO 消息推送 待完善
				/*pushMessageService.pushMessageByMemberId(order1.getMember().getId(), "",
						messageSource.getMessage("product_shipped",new String[] { order1.getOrderNumber() },	Locale.SIMPLIFIED_CHINESE),Messages.CATEGORY_SHIP, order1.getId());*/
		}
		
		redirectAttributes.addFlashAttribute("message", "修改订单成功");
		return "redirect:/mgr/order";
	}
	/**
	 * 记录订单修改日志
	 * @param oldOrder
	 * @param order
	 */
	private void log(Order oldOrder, Order order) {
		Long upUser = getCurrentUserId();
		StringBuffer sb = new StringBuffer();
		sb.append("用户"+upUser+"订单（").append(order.getOrderNo()).append(")修改");
		if (oldOrder.getState() != null && order.getState() != null
				&& !oldOrder.getState().equals(order.getState()))
			sb.append("<br/>修改状态" + oldOrder.getState() + "到" + order.getState());
		if (oldOrder.getLogisticNo() != null && order.getLogisticNo() != null
				&& !oldOrder.getLogisticNo().equals(order.getLogisticNo()))
			sb.append("<br/>修改运单号" + oldOrder.getLogisticNo() + "到"
					+ order.getLogisticNo());
		logger.debug(sb.toString());
	}
	
	@RequestMapping(value = "showInfo")
	public String showInfo() {
		return "order/info";
	}
}
