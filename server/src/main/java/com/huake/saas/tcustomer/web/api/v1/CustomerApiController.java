package com.huake.saas.tcustomer.web.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.huake.saas.base.web.controller.BaseApiController;
import com.huake.saas.category.entity.IndustryCategory;
import com.huake.saas.category.service.IndustryCategoryService;
import com.huake.saas.tcustomer.entity.Tcustomer;
import com.huake.saas.tcustomer.service.TcustomerService;

@Controller
@RequestMapping(value = "/api/v1/customer")
public class CustomerApiController extends BaseApiController{

	@Autowired
	private TcustomerService service;

	@Autowired
	private IndustryCategoryService categoryService;
	/**
	 * 客户列表
	 * 
	 * @param categoryId
	 * @param first
	 * @param max
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Tcustomer> getTcustomers(
			@RequestParam(value = "uid", required = true) String uid,
			@RequestParam(value = "typeId",defaultValue="0", required = true) long typeId,
			@RequestParam(value = "pageNum",defaultValue="0", required = false) Integer pageNum) {
		
		
		//Map<String, Object> searchParams = new HashMap<String, Object>();
		//searchParams.put("EQ_customer.tenancy.uid", uid);
	//	searchParams.put("EQ_type.id", typeId);
		List<Tcustomer> pageList = service.findTcustomerByType(typeId, new Long(uid), pageNum).getContent();
		//Map<String, List<Tcustomer>> map = new HashMap<String, List<Tcustomer>>();
		return pageList;
	}
	
	/**
	 * 搜索 会员企业
	 * 
	 * @param codition 搜索条件
	 * @param first
	 * @param max
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Tcustomer> getTcustomers(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestParam(value = "condition",defaultValue="", required = false) String condition,
			@RequestParam(value = "pageNum",defaultValue="0", required = false) Integer pageNum) {
		List<Tcustomer> pageList = service.findByCondition(uid, pageNum,"%"+condition+"%" ).getContent();
		return pageList;
	}

	/**
	 * 客户详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Tcustomer detail(
			@RequestParam(value = "id", required = true) Long id) {
		Tcustomer customer = service.get(id);
		customer.setTenancy(null);
		customer.setCustomerTypes(null);
	    return customer;
	}
	
	/**
	 * 会员企业分类列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/category", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<IndustryCategory> getCategory(@RequestParam(value = "uid", required = true)Long uid){
		return categoryService.getAllCategory();
	}

}
