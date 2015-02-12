package com.huake.saas.addresslist.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.huake.saas.addresslist.entity.AddressList;
import com.huake.saas.addresslist.service.AddressListService;
import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.web.controller.BaseMobileController;

@Controller
@RequestMapping("/m/addressList")
public class AddressListMobileController extends BaseMobileController{

	@Autowired	
	private AddressListService service;
	
	/**
	 * 通讯录首页
	 * @param pageNum
	 * @param UID
	 * @return
	 */

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(@RequestParam("uid")  final String UID,@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum)throws AppBizException{
			ModelAndView mav = new ModelAndView();
			if(UID == null){
				throw new AppBizException("无效的访问");
			}
			Map<String, Object> searchParams = new HashMap<String, Object>();
			Page<AddressList> lists = service.findByCondition(Long.valueOf(UID), searchParams, pageNum, 5, "auto");
			mav.addObject("UID", UID);
			mav.addObject("lists", lists);
			mav.setViewName("mobile/addressList/index");
			return mav;
	
	}
	
	/**
	 * 分页获取通讯录列表。
	 * @param UID
	 * @param pageNum
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<AddressList> list(@RequestParam("UID")  final String UID,
	@RequestParam(value = "pageNum",defaultValue="2", required = false) Integer pageNum)throws AppBizException{
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<AddressList> lists = service.findByCondition(Long.valueOf(UID), searchParams, pageNum, 5, "auto").getContent();
		return lists;
	}
	
}
