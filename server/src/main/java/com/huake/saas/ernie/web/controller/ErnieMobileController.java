package com.huake.saas.ernie.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.web.controller.BaseMobileController;
import com.huake.saas.ernie.entity.Ernie;
import com.huake.saas.ernie.entity.ErnieItem;
import com.huake.saas.ernie.entity.ErnieLog;
import com.huake.saas.ernie.service.ErnieItemService;
import com.huake.saas.ernie.service.ErnieLogService;
import com.huake.saas.ernie.service.ErnieService;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;
/**
 * 网页控制器
 * @author laidingqing
 *
 */
@Controller
@RequestMapping("/m/ernies")
public class ErnieMobileController extends BaseMobileController {

	@Autowired
	private ErnieService ernieService;
	
	@Autowired
	private ErnieItemService ernieItemService;
	
	@Autowired	
	private TenancyUserService tUserService;
	
	@Autowired
	private ErnieLogService ernieLogService;
	
	/*****************************手机营销互动列表***************************/
	
	/**
	 * 营销互动首页
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
			Pageable pageable = new PageRequest(pageNum - 1, 5, new Sort(
					Direction.DESC, new String[] { "updatedAt" }));
			Map<String, Object> searchParams = new HashMap<String, Object>();
			Page<Ernie> lists = ernieService.getNowAllErnie(Long.valueOf(UID), searchParams, pageable);
			mav.addObject("UID", UID);
			mav.addObject("lists", lists);
			mav.setViewName("mobile/ernie/index");
			return mav;
	
	}
	
	/**
	 * 分页获取营销互动列表。
	 * @param UID
	 * @param pageNum
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<Ernie> list(@RequestParam("UID")  final String UID,
	@RequestParam(value = "pageNum",defaultValue="2", required = false) Integer pageNum)throws AppBizException{
		Pageable pageable = new PageRequest(pageNum - 1, 5, new Sort(
				Direction.DESC, new String[] { "updatedAt" }));
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<Ernie> lists = ernieService.getNowAllErnie(Long.valueOf(UID), searchParams, pageable).getContent();
		return lists;
	}
	
	/**跳转相关活动页面
	 * @param ernieId
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/action/{ernieId}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<String,Object> action(@RequestParam("uid")  final String uid,
			@PathVariable("ernieId")  final String ernieId)throws AppBizException{
		Map<String,Object> map = new HashMap<String,Object>();
		Ernie ernie = ernieService.findById(Long.valueOf(ernieId));
		String c = ernie.getCategory();
		System.out.println(ernieId);
		System.out.println(c);
		map.put("action", "false");
		if(c.equals("3")){
			map.put("action", "true");
			//博饼活动页
			String url = "/m/ernies/choujiang/bobing/"+ernieId+"?uid="+uid;
			map.put("url", url);
		}if(c.equals("1")){
			map.put("action", "true");
			//抽奖活动页
			String url = "/m/ernies/choujiang/"+ernieId+"?uid="+uid;
			map.put("url", url);
		}if(c.equals("2")){
			//砸蛋暂无活动页面
			map.put("action", "false");
		}
		
		return map;
	}
	
	/*****************************抽奖***************************/
	
	/**
	 * 展示摇奖页面
	 * @param ernieId
	 * @param memberId
	 * @param uid
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/choujiang/{ernieId}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable("ernieId") final Long ernieId,
			@RequestParam(value="memberId" ,required = false)  final Long memberId, 
			@RequestParam("uid")  final String uid) throws AppBizException{
		Ernie ernie = ernieService.findById(ernieId);
		System.out.println("----------"+uid);
		System.out.println("=========="+ernieId);
		Pageable pageable = new PageRequest(0,10, new Sort(
				Direction.DESC, new String[] { "id" }));
		//摇奖项
		Page<ErnieItem> ernie1 = ernieItemService.findAllErnieByUidAndErnieId(
				Long.valueOf(uid),ernieId, pageable);
		Pageable pageable1 = new PageRequest(0, 20, new Sort(
				Direction.DESC, new String[] { "createdAt" }));
		//获奖记录
		Page<ErnieLog> ernies = ernieLogService.findAllErnieLogByUidAndErineId(Long.valueOf(uid),
				ernieId,pageable1);
		long count = ernies.getTotalElements();
		ModelAndView mav = new ModelAndView();
		mav.addObject("uid", uid);
		mav.addObject("memberId", memberId);
		mav.addObject("ernie", ernie);
		mav.addObject("count", count);
		mav.addObject("ernie1", ernie1);
		mav.addObject("ernies", ernies);
		mav.setViewName("mobile/ernie/show");
		return mav;
	}
	
	
	
	/**
	 * 个人中奖信息页面
	 * @return
	 * @throws AppBizException 
	 */
	@RequestMapping(value="/memberInfo/{memberId}/{ErnieItemId}",method=RequestMethod.GET)
	public ModelAndView appRevisionMemberInfo(@PathVariable("memberId") final Long memberId,
			@PathVariable("ErnieItemId") final Long ErnieItemId,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		TenancyUser tu = tUserService.findById(memberId);
		ErnieItem ei = ernieItemService.findById(ErnieItemId);
		long ernieId = ei.getErnie().getId();
		String prizeName = ei.getName();
		String name = tu.getName();
		String uid = ei.getErnie().getUid()+"";
		mav.addObject("name", name);
		mav.addObject("uid", uid);
		mav.addObject("prizeName", prizeName);
		mav.addObject("tenancyUser", tu);
		mav.addObject("memberId", memberId);
		mav.addObject("ernieId", ernieId);
		mav.setViewName("mobile/ernie/memberInfo");
		return mav;
	}
	
	/**
	 * 个人中奖信息页面提交
	 * @return
	 * @throws AppBizException 
	 */
	@RequestMapping(value="/memberInfoSave",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String appRevisionMemberInfoSave(@ModelAttribute(value="tenancyUser")TenancyUser tenancyUser) throws Exception{
		
		TenancyUser tu = tUserService.findById(tenancyUser.getId());
		System.out.println(tu.getId()+" "+tu.getName()+" "+tu.getUid());
		Long uid = tu.getUid();
		Long ernieId = tenancyUser.getErnieId();
		tu.setName(tenancyUser.getName());	
		tu.setMobile(tenancyUser.getMobile());
	    tUserService.updateTenancyUser(tu);
	    return "redirect:/m/ernies/choujiang/"+ernieId+"?uid="+uid;
	}
	
	/**
	 * 查看我的奖品页面
	 * @return
	 */
	@RequestMapping(value="/award/{ernieId}",method=RequestMethod.GET)
	public ModelAndView myAward(@PathVariable("ernieId") final Long ernieId,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="memberId",required = false) final Long memberIds){
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		List<ErnieLog> myAwards = null;
		if(session.getAttribute("memberId")!=null&&session.getAttribute("memberId")!=""){
			Long memberId = Long.valueOf(""+session.getAttribute("memberId"));
			myAwards = ernieLogService.findByMemberIdAndErnieId(ernieId, memberId);
		}
		mav.addObject("myAwards", myAwards);
		mav.setViewName("mobile/ernie/award");
		return mav;
	}
	
	/*****************************博饼***************************/
	
	/**
	 * 博饼页面
	 * @param id   活动id
	 * @param userId   用户id
	 * @return
	 */
	@RequestMapping(value = "/choujiang/bobing/{ernieId}", method = RequestMethod.GET)
    public ModelAndView bobingIndex(@PathVariable(value="ernieId") final Long ernieId,
    		@RequestParam(value="uid") final String uid,@RequestParam(value = "memberId",required = false)String userId) {
		Pageable pageable = new PageRequest(0,10, new Sort(
				Direction.ASC, new String[] { "id" }));
		Pageable pageable1 = new PageRequest(0, 20, new Sort(
				Direction.DESC, new String[] { "createdAt" }));
		//博饼奖品列表
		Page<ErnieItem> ernieItem = ernieItemService.findAllErnieByUidAndErnieId(
				Long.valueOf(uid),ernieId, pageable);
		//获奖记录
		Page<ErnieLog> ernies = ernieLogService.findAllErnieLogByUidAndErineId(Long.valueOf(uid),
				ernieId,pageable1);
		long count = ernies.getTotalElements();
		ModelAndView mav = new ModelAndView();
		Ernie ernie = ernieService.findById(ernieId);
		mav.addObject("ernieItem", ernieItem);
		mav.addObject("ernies", ernies);
		mav.addObject("ernieId", ernieId);
		mav.addObject("uid", uid);
		mav.addObject("ernie", ernie);
		mav.addObject("count", count);
		mav.setViewName("mobile/dice/index");
        return mav;
	}
	
	/**进行博饼获取结果/进行抽奖
	 * @param uid
	 * @param ernieId
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/doErnie/{ernieId}", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,Object> doErnie(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "uid") final Long uid,
			@PathVariable(value = "ernieId") final Long ernieId,
			@RequestParam(value = "memberId", required = false) Long memberIds){
		HttpSession session = request.getSession();
		Map<String,Object> result = new HashMap<String, Object>();
		Long memberId = null;
		result.put("login","false");
		if(session.getAttribute("login")==null||session.getAttribute("login")=="false"||session.getAttribute("login")==""){
			result.put("login","false");
		}else{
			result.put("login","true");
		memberId = Long.valueOf(session.getAttribute("memberId")+"");
		result.putAll(ernieService.hasPower(ernieId, memberId, uid));
		if("true".equals(result.get("success"))){
			Ernie ernie = ernieService.findById(ernieId);
			if(ernie.getCategory().equals(Ernie.CATEGORY_VALID)){
				result.putAll(ernieService.doBobing(ernieId, memberId, uid));
			}else{
				result.putAll(ernieService.draw(ernieId, memberId));
			}
		}
		}
		return result;
	}
	
	
	/**
	 * 个人中奖信息页面
	 * @return
	 * @throws AppBizException 
	 */
	@RequestMapping(value="/bobing/memberInfo/{memberId}/{ErnieItemId}",method=RequestMethod.GET)
	public ModelAndView bobingMemberInfo(@PathVariable("memberId") final Long memberId,
			@PathVariable("ErnieItemId") final Long ErnieItemId,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		TenancyUser tu = tUserService.findById(memberId);
		ErnieItem ei = ernieItemService.findById(ErnieItemId);
		long ernieId = ei.getErnie().getId();
		String prizeName = ei.getName();
		String name = tu.getName();
		String uid = ei.getErnie().getUid()+"";
		mav.addObject("name", name);
		mav.addObject("uid", uid);
		mav.addObject("prizeName", prizeName);
		mav.addObject("tenancyUser", tu);
		mav.addObject("memberId", memberId);
		mav.addObject("ernieId", ernieId);
		mav.setViewName("mobile/dice/memberInfo");
		return mav;
	}
	
	/**
	 * 个人中奖信息页面提交
	 * @return
	 * @throws AppBizException 
	 */
	@RequestMapping(value="/bobing/memberInfoSave",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String bobingMemberInfoSave(@ModelAttribute(value="tenancyUser")TenancyUser tenancyUser) throws Exception{
		
		TenancyUser tu = tUserService.findById(tenancyUser.getId());
		System.out.println(tu.getId()+" "+tu.getName()+" "+tu.getUid());
		Long uid = tu.getUid();
		Long ernieId = tenancyUser.getErnieId();
		tu.setName(tenancyUser.getName());	
		tu.setMobile(tenancyUser.getMobile());
	    tUserService.updateTenancyUser(tu);
	    return "redirect:/m/ernies/choujiang/bobing/"+ernieId+"?uid="+uid;
	}
}

