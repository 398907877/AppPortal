package com.huake.saas.activity.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.huake.saas.activity.entity.Event;
import com.huake.saas.activity.entity.EventJoin;
import com.huake.saas.activity.service.EventService;
import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.web.controller.BaseMobileController;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

/**
 *活动手机网页 
 * @author 
 *
 */
@Controller
@RequestMapping(value = "/m/events")
public class MobileActivityController extends BaseMobileController{
	
	@Autowired
	private EventService eventService;

	@Autowired	
	private TenancyUserService tUserService;
	
	@Autowired
	private TenancyService tenancyService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("uid")  final String UID,@RequestParam(value="memberId" ,required = false)  final Long memberIds, 
			@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum)throws AppBizException{
		ModelAndView mav = new ModelAndView();
		if(UID == null){
			throw new AppBizException("无效的访问");
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<Event> events = eventService.getAllEventsByUid(Long.valueOf(UID),
				searchParams, pageNum, 5, BaseEntity.PAGE_CRTDATE_DESC);
		HttpSession session = request.getSession();
		Long memberId = null;
		if(session.getAttribute("memberId")!=null){
			memberId = Long.valueOf(session.getAttribute("memberId")+"");
		}
		if(memberId!=null){
			for (Event event : events) {
				String falg = eventService.findSingUp(memberId,event.getId());
				event.setSingUp(falg);
			}
		}
		
		
		for (Event events2 : events) {
			String str = events2.getTitle();
			if(events2.getTitle().length()>15){
				str = str.substring(0,15)+"...";
			}
			events2.setTitle(str);
		}
		mav.addObject("memberId", memberId);
		mav.addObject("UID", UID);
		mav.addObject("events", events);
		mav.setViewName("mobile/activity/index");
		return mav;
	}
	
	/**
	 * 分页获取活动列表。
	 * @param UID
	 * @param pageNum
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<Event> list(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("UID")  final String UID,@RequestParam(value="memberId" ,required = false)  final Long memberIds, 
			@RequestParam(value = "pageNum",defaultValue="2", required = false) Integer pageNum)throws AppBizException{
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<Event> events = eventService.getAllEventsByUid(Long.valueOf(UID),
				searchParams, pageNum, 5, BaseEntity.PAGE_CRTDATE_DESC).getContent();
		HttpSession session = request.getSession();
		Long memberId = null;
		if(session.getAttribute("memberId")!=null){
			memberId = Long.valueOf(session.getAttribute("memberId")+"");
		}
		if(memberId!=null){
			for (Event event : events) {
				String falg = eventService.findSingUp(memberId,event.getId());
				event.setSingUp(falg);
			}
		}
		for (Event events2 : events) {
			String str = events2.getTitle();
			if(events2.getTitle().length()>10){
				str = str.substring(0,15)+"...";
			}
			events2.setTitle(str);
		}
		return events;
	}
	
	/**
	 * 网页展示活动细项，可以单独调用该页
	 * @param eventId
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/{eventId}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable final Long eventId) throws AppBizException{
		Event event = eventService.findById(eventId);
		if(null == event){
			throw new AppBizException("出现错误了", "Object No found.");
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("event", event);
		mav.setViewName("mobile/activity/show");
		return mav;
	}
	
	/**
	 * 参与活动
	 * 
	 * @param categoryId
	 * @param first
	 * @param max
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/partake", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,Object> partake(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="eventId",required=true)Long eventId,
			@RequestParam(value="uid",required=true)Long uid) {
		EventJoin join = new EventJoin();
		Map<String,Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		result.put("login","false");
		if(session.getAttribute("login")==null||session.getAttribute("login")=="false"||session.getAttribute("login")==""){
			result.put("login","false");
		}else{
			result.put("login","true");
		Long userId = Long.valueOf(session.getAttribute("memberId")+"");
		Tenancy tenancy = tenancyService.getTenancy(uid);
		String companyName = tenancy.getName();
		TenancyUser tenancyUser = tUserService.findById(userId);
		String name = tenancyUser.getName();
		String tel = tenancyUser.getMobile();
		join.setEventId(eventId);
		join.setMid(userId);
		join.setUid(uid);
		join.setType(EventJoin.TYPE_JOIN);
		join.setName(name);
		join.setTel(tel);
		join.setCompanyName(companyName);
		eventService.partake(join);
		result.put("success", "true");
		}
		return result;
	}

	/**
	 * 取消报名 
	 * @param eventId 活动id 
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,String> cancel(
			@RequestParam(value="eventId",required=true)Long eventId,
			@RequestParam(value="userId",required=true)Long userId) {
		eventService.cancel(eventId,userId);
		Map<String,String> map = new HashMap<String, String>();
		map.put("success", "true");
		return map;
	}
}
