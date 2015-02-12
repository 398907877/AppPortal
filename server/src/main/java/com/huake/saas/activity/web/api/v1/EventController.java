package com.huake.saas.activity.web.api.v1;

import java.util.ArrayList;
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

import com.huake.saas.activity.entity.Event;
import com.huake.saas.activity.entity.EventJoin;
import com.huake.saas.activity.service.EventService;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.web.controller.BaseApiController;
import com.huake.saas.user.entity.TenancyUser;

/**
 * 租户活动api接口
 * @author laidingqing
 *
 */
@Controller("eventApiController")
@RequestMapping(value = "/api/v1/events")
public class EventController extends BaseApiController{
	
	
	@Autowired
	private EventService eventService;
	
	/**
	 * 活动列表
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
	public List<Event> list(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum,
			@RequestParam(value="userId",required=false)Long userId) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_status", "1");
		List<Event> list = eventService.getAllEventsByUid(uid, searchParams, pageNum, BaseEntity.DATE_MAX,
				BaseEntity.PAGE_CRTDATE_DESC).getContent();
		if(list != null && userId != null){
			for(int i=0;i<list.size();i++){
				List<EventJoin> eventJoins = new ArrayList<EventJoin>();
				eventJoins.add(eventService.findByEventIdAndMid(list.get(i).getId(), userId));
				list.get(i).setEventJoins(eventJoins);
			}
		}
		return list;
	}
	/**
	 * 活动详细
	 * 
	 * @param categoryId
	 * @param first
	 * @param max
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Event detail(@RequestParam(value = "id", required = true) Long id) {
		return eventService.findById(id);
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
	@RequestMapping(value = "/partake", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,String> partake(
			@RequestBody EventJoin join) {
		eventService.partake(join);
        return SUCCESS_RESULT;
	}

	/**
	 * 取消报名 
	 * @param eventId 活动id 
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,String> cancel(
			@RequestParam(value="eventId",required=true)Long eventId,
			@RequestParam(value="userId",required=true)Long userId) {
		eventService.cancel(eventId,userId);
		return SUCCESS_RESULT;
	}
	/**
	 * 我的活动
	 * @param uid 租户id
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/myEvent", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Event> myEvent(
			@RequestParam(value="uid")Long uid,
			@RequestParam(value="userId")Long userId) {
		List<Event> list = eventService.findByUidAndMid(uid, userId);
		if(list != null && userId != null){
			for(int i=0;i<list.size();i++){
				List<EventJoin> eventJoins = new ArrayList<EventJoin>();
				eventJoins.add(eventService.findByEventIdAndMid(list.get(i).getId(), userId));
				list.get(i).setEventJoins(eventJoins);
			}
		}
		return list;
	}
	
	@RequestMapping(value = "/eventJoins", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<TenancyUser> getEventJoins(@RequestParam(value="uid",required=true)Long uid,
			@RequestParam(value="eventId",required=true)Long eventId){
		return eventService.findEntered(eventId);
	}
}
