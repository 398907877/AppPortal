package com.huake.saas.groupon.web.controller;

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
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.web.controller.BaseMobileController;
import com.huake.saas.groupon.entity.GroupOn;
import com.huake.saas.groupon.service.GroupOnService;

@Controller
@RequestMapping(value = "/m/groupon")
public class GroupOnMobileController extends BaseMobileController{
	
	@Autowired
	private GroupOnService groupOnService; 
	
	
	/**
	 * 团购列表首页
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
			Page<GroupOn> groupOns = groupOnService.findByCondition(Long.valueOf(UID),
					searchParams, pageNum, 5 , BaseEntity.PAGE_CRTDATE_DESC);
			mav.addObject("UID", UID);
			mav.addObject("groupOns", groupOns);
			mav.setViewName("mobile/groupon/index");
			return mav;
	
	}
	
	/**
	 * 分页获取团购列表。
	 * @param UID
	 * @param pageNum
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<GroupOn> list(@RequestParam("UID")  final String UID,
	@RequestParam(value = "pageNum",defaultValue="2", required = false) Integer pageNum)throws AppBizException{
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<GroupOn> groupOns = groupOnService.findByCondition(Long.valueOf(UID),
				searchParams, pageNum, 5 , BaseEntity.PAGE_CRTDATE_DESC).getContent();
		return groupOns;
	}
	
	/**
	 * 网页展示详细页面，可以单独调用该页
	 * @param eventId
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/detail/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable final Long id) throws AppBizException{
		GroupOn groupOn = groupOnService.findById(id);
		if(null == groupOn){
			throw new AppBizException("出现错误了", "Object No found.");
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("groupOn", groupOn);
		mav.setViewName("mobile/groupon/show");
		return mav;
	}
}
