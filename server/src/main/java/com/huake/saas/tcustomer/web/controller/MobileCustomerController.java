package com.huake.saas.tcustomer.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.web.controller.BaseMobileController;
import com.huake.saas.tcustomer.entity.Tcustomer;
import com.huake.saas.tcustomer.service.TcustomerService;

@Controller
@RequestMapping("/m/customer")
public class MobileCustomerController extends BaseMobileController{

	@Autowired	
	private TcustomerService TCservice;
	

	

	

	/**
	 * 商会企业首页
	 * @param pageNum
	 * @param UID
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(@RequestParam("uid")  final String UID, 
			@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum)throws AppBizException{
			ModelAndView mav = new ModelAndView();
			if(UID == null){
				throw new AppBizException("无效的访问");
			}
			Map<String, Object> searchParams = new HashMap<String, Object>();
			Page<Tcustomer> customers = TCservice.findByCondition2(Long.valueOf(UID),searchParams, pageNum, 5, "auto");
			mav.addObject("UID", UID);
			mav.addObject("customers", customers);
			mav.setViewName("mobile/coc/index");
			return mav;
	
	}
	
	/**
	 * 分页获取企业列表。
	 * @param UID
	 * @param pageNum
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<Tcustomer> listCoc(@RequestParam("UID")  final String UID,
	@RequestParam(value = "pageNum",defaultValue="2", required = false) Integer pageNum)throws AppBizException{
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<Tcustomer> customers = TCservice.findByCondition2(Long.valueOf(UID),searchParams, pageNum, 5, "auto").getContent();
		return customers;
	}
	
	/**
	 * 网页展示企业详情，可以单独调用该页
	 * @param id
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable final Long id) throws AppBizException{
		Tcustomer tcustomer = TCservice.get(id);
		if(null == tcustomer){
			throw new AppBizException("出现错误了", "Object No found.");
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("tcustomer", tcustomer);
		mav.setViewName("mobile/coc/show");
		return mav;
	}
	
}
