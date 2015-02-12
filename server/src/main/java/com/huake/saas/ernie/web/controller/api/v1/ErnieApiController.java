package com.huake.saas.ernie.web.controller.api.v1;

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
import com.huake.saas.ernie.entity.Ernie;
import com.huake.saas.ernie.entity.ErnieLog;
import com.huake.saas.ernie.entity.TakePrize;
import com.huake.saas.ernie.service.ErnieItemService;
import com.huake.saas.ernie.service.ErnieLogService;
import com.huake.saas.ernie.service.ErnieService;
import com.huake.saas.ernie.service.TakePrizeService;
import com.huake.saas.user.service.TenancyUserService;

@Controller
@RequestMapping(value = "/api/v1/ernie")
public class ErnieApiController extends BaseApiController{
	
	@Autowired
	private ErnieService ernieService;
	
	@Autowired
	private TenancyUserService userService;
	
	@Autowired
	private ErnieLogService logSerivce;
	
	@Autowired
	private ErnieItemService itemService;
	
	@Autowired
	private TakePrizeService takePrizeService;
	
	/**
	 * 进行摇奖活动
	 * @param uid
	 * @param ernieId
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/doErnie", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,Object> doErnie(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestParam(value = "ernieId", required = true) Long ernieId,
			@RequestParam(value = "memberId", required = true) Long memberId){
		Map<String,Object> result = new HashMap<String, Object>();
		result.putAll(ernieService.hasPower(ernieId, memberId, uid));
		if("true".equals(result.get("success"))){
			Ernie ernie = ernieService.findById(ernieId);
			if(ernie.getCategory().equals(Ernie.CATEGORY_VALID)){
				result.putAll(ernieService.doBobing(ernieId, memberId, uid));
			}else{
				result.putAll(ernieService.draw(ernieId, memberId));
			}
		}
		return result;
	}
	/**
	 * 获取营销活动基本信息
	 * @param uid
	 * @param ernieId
	 * @return
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Ernie getInfo(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestParam(value = "ernieId", required = true) Long ernieId){
		Ernie ernie = ernieService.findById(ernieId);
		
		return ernie;
	}
	/**
	 * 获取我的中奖信息
	 * @param uid
	 * @param ernieId
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/myPrize", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<ErnieLog> getMyPrize(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestParam(value = "ernieId", required = true) Long ernieId,
			@RequestParam(value = "memberId", required = true) Long memberId){
		return logSerivce.findByMemberIdAndErnieId(ernieId, memberId);
	}
	/**
	 * 获取 营销互动 中奖信息
	 * @param uid
	 * @param ernieId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/ernieLogs", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<ErnieLog> getErnieLogs(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestParam(value = "ernieId", required = true) Long ernieId,
			@RequestParam(value = "pageNum", required = false,defaultValue="0") int pageNum,
			@RequestParam(value = "pageSize", required = false,defaultValue="20") int pageSize){
		return logSerivce.findByErnieId(pageSize, pageNum, ernieId);
	}
	
	/**
	 * 获取正在进行的 营销互动
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/getErnie", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Ernie> getErnie(@RequestParam(value = "uid", required = true) Long uid){
		return ernieService.findNowErnieByUid(uid);
	}
	/**
	 * 兑奖
	 * @param uid
	 * @param takePrize
	 * @return
	 */
	@RequestMapping(value = "/takePrize", method = RequestMethod.POST,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,String> takePrize(@RequestParam(value = "uid", required = true) Long uid,@RequestBody TakePrize takePrize){
		return takePrizeService.doTakePrize(takePrize);
	}
	
}
