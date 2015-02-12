package com.huake.saas.mall.web.api.v1;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.huake.saas.mall.service.AddressService;

@Controller
@RequestMapping(value = "/api/v1/address")
public class AddressApiController extends BaseApiController{
	@Autowired
	private AddressService addressService;
	
	/**
	 * 查询用户收货地址
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Address> getAddress(
			@RequestParam(value = "userId", required = true) Long userId,
			@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize",defaultValue="10", required = false) Integer pageSize){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		return addressService.findByApiCondition(searchParams, pageNum, pageSize, null).getContent();
	}
	
	/**
	 * 用户收货地址详细
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Address getAddressDetail(
			@RequestParam(value = "id", required = true) Long id){
		return addressService.findById(id);
	}
	/**
	 * 更新用户收货地址详细
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,String> updateAddress(@RequestParam(value = "uid", required = true) Long userId,
			@RequestBody Address address){
		
		address.setMemberId(userId);
		addressService.update(address);
		return SUCCESS_RESULT;
	}
	/**
	 * 添加用户收货地址详细
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,String> addAddress(@RequestParam(value = "uid", required = true) Long userId,
			@RequestBody Address address){
		address.setCrDate(new Date());
		address.setMemberId(userId);
		addressService.update(address);
		return SUCCESS_RESULT;
	}
	/**
	 * 用户收货地址详细
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,String> getAddress(
			@RequestParam(value = "id", required = true) Long id){
		addressService.delete(id);
		return SUCCESS_RESULT;
	}
}
