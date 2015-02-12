package com.huake.saas.auth.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.huake.dict.entity.Dictionary;
import com.huake.dict.service.DictViewService;
import com.huake.saas.auth.entity.ActivityAuthCfg;
import com.huake.saas.auth.entity.Auth;
import com.huake.saas.auth.entity.AuthCfg;
import com.huake.saas.auth.entity.BookAuthCfg;
import com.huake.saas.auth.entity.CouponAuthCfg;
import com.huake.saas.auth.entity.InvitationAuthCfg;
import com.huake.saas.auth.entity.NewsAuthCfg;
import com.huake.saas.auth.entity.PaymentAuthCfg;
import com.huake.saas.auth.entity.ProductAuthCfg;
import com.huake.saas.auth.entity.SupplyAuthCfg;
import com.huake.saas.auth.entity.VipAuthCfg;
import com.huake.saas.auth.service.AuthService;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;
import com.huake.saas.redis.repository.AuthCfgRepository;

/**
 * 
 * @author hyl
 * 授权管理模块的相关操作
 *
 */
@Controller
@RequestMapping("/mgr/auth/**")
public class AuthController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private static final String PAGE_SIZE = "5";
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("createDate", "创建时间");
	}
	
	/**
	 * 页面传时间参数时进行转换
	 * @param binder
	 */
	/*@InitBinder
	protected void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class,"issueDate",new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class,"dueDate",new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

	}*/
	/**
	 * 绑定各个业务模块皮配置
	 * 
	 */
	@InitBinder(AuthCfg.AUTH_NEWS)
	protected void initBinderNews(ServletRequestDataBinder binder){
		binder.setFieldDefaultPrefix(AuthCfg.AUTH_NEWS+"_");
	}
	@InitBinder(AuthCfg.AUTH_INVITATION)
	protected void initBinderInvitation(ServletRequestDataBinder binder){
		binder.setFieldDefaultPrefix(AuthCfg.AUTH_INVITATION+"_");
	}
	@InitBinder(AuthCfg.AUTH_ACTIVITY)
	protected void initBinderActivity(ServletRequestDataBinder binder){
		binder.setFieldDefaultPrefix(AuthCfg.AUTH_ACTIVITY+"_");
	}
	@InitBinder(AuthCfg.AUTH_SUPPLY)
	protected void initBinderSupply(ServletRequestDataBinder binder){
		binder.setFieldDefaultPrefix(AuthCfg.AUTH_SUPPLY+"_");
	}
	@InitBinder(AuthCfg.AUTH_BOOK)
	protected void initBinderBook(ServletRequestDataBinder binder){
		binder.setFieldDefaultPrefix(AuthCfg.AUTH_BOOK+"_");
	}
	@InitBinder(AuthCfg.AUTH_PAYMENT)
	protected void initBinderPayment(ServletRequestDataBinder binder){
		binder.setFieldDefaultPrefix(AuthCfg.AUTH_PAYMENT+"_");
	}
	@InitBinder(AuthCfg.AUTH_COUPON)
	protected void initBinderCoupon(ServletRequestDataBinder binder){
		binder.setFieldDefaultPrefix(AuthCfg.AUTH_COUPON+"_");
	}
	@InitBinder(AuthCfg.AUTH_VIP)
	protected void initBinderVip(ServletRequestDataBinder binder){
		binder.setFieldDefaultPrefix(AuthCfg.AUTH_VIP+"_");
	}
	@InitBinder(AuthCfg.AUTH_PRODUCT)
	protected void initBinderProduct(ServletRequestDataBinder binder){
		binder.setFieldDefaultPrefix(AuthCfg.AUTH_PRODUCT+"_");
	}
	@InitBinder("auth")
	protected void initBinderAuth(ServletRequestDataBinder binder){
		binder.setFieldDefaultPrefix("auth_");
	}
	
	public static Map<String, String> getSortTypes() {
		return sortTypes;
	}

	public static void setSortTypes(Map<String, String> sortTypes) {
		AuthController.sortTypes = sortTypes;
	}

	@Autowired
	private AuthCfgRepository<NewsAuthCfg> newsAuthCfgRepository;
	
	@Autowired	
	private AuthService authService;
	
	@Autowired	
	private TenancyService tenancyService;
	
	@Autowired
	private DictViewService dictViewService;

	/**
	 * 授权管理首页
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto")String sortType, Model model,
			ServletRequest request){
		
		
		Long userId = getCurrentUserId();
		logger.debug("userId"+userId+"授权管理首页访问");
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		//查询所有租户
		Page<Tenancy> tenancys = tenancyService.findTenanciesByCondition(userId, searchParams, pageNumber, pageSize, sortType, null);
		//存放所有配置
		Map<String,Object> authCfgs = new HashMap<String,Object>();
		
		List<Dictionary> bizCodes = dictViewService.getDictViewList("BIZ_CODE");//查询业务模块列表
		for(Tenancy tenancy :tenancys){
			Auth auth = authService.findByUid(String.valueOf(tenancy.getUid()));
			if(auth != null){
				for(Dictionary d : bizCodes){
					AuthCfg authCfg = authService.findAuthCfgByAuthId(auth.getId(),d.getKey());
					if(authCfg != null){
						authCfgs.put(d.getKey()+tenancy.getUid(), authCfg);
						break;
					}
				}
			}
		}
		model.addAttribute("authCfgs",authCfgs);
		model.addAttribute("bizCodes", bizCodes);
		//model.addAttribute("sortType", sortType);
		//model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		List<Tenancy> list= tenancyService.getAllTenancies();
		model.addAttribute("tenancys", tenancys);
		model.addAttribute("list", list);
		return "auth/index";
	}

	@RequestMapping(value="more",method = RequestMethod.GET)
	public String detail(@RequestParam(value = "id") Long id,Model model) {
		Auth auth = authService.findById(id);
		Tenancy tenancy = tenancyService.getTenancy(Long.valueOf(auth.getUid()));
		
		List<Dictionary> bizCodes = dictViewService.getDictViewList("BIZ_CODE");
		Map<String,Object> authCfgs = new HashMap<String,Object>();
		
		for(Dictionary d : bizCodes){
			authCfgs.put(d.getValue(), authService.findAuthCfgByAuthId(auth.getId(), d.getKey()));
		}
		model.addAttribute("authCfgs",authCfgs);
		model.addAttribute("bizCodes",bizCodes);
		model.addAttribute("tenancy", tenancy);
		return "auth/more";
	}
	
	/**
	 * 添加授权书
	 * @param auth
	 * @param nac
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String save(@ModelAttribute("auth")Auth auth,
			@ModelAttribute(AuthCfg.AUTH_NEWS)NewsAuthCfg newsCfg,
			@ModelAttribute(AuthCfg.AUTH_PRODUCT)ProductAuthCfg productCfg,
			@ModelAttribute(AuthCfg.AUTH_ACTIVITY)ActivityAuthCfg activityCfg,
			@ModelAttribute(AuthCfg.AUTH_BOOK)BookAuthCfg bookCfg,
			@ModelAttribute(AuthCfg.AUTH_COUPON)CouponAuthCfg couponCfg,
			@ModelAttribute(AuthCfg.AUTH_INVITATION)InvitationAuthCfg invitationCfg,
			@ModelAttribute(AuthCfg.AUTH_PAYMENT)PaymentAuthCfg paymentCfg,
			@ModelAttribute(AuthCfg.AUTH_SUPPLY)SupplyAuthCfg supplyCfg,
			@ModelAttribute(AuthCfg.AUTH_VIP)VipAuthCfg vipCfg,
			RedirectAttributes redirectAttributes){
		auth.setStatus(Auth.STATUS_ACTIVE);
		auth.setVersion(1);
		auth = authService.create(auth);
		
		if(newsCfg.getDayLimit()>0 && newsCfg.getMonthVideoLimit()>0){
			newsCfg.setAuthId(auth.getId());
			authService.saveAuthCfg(newsCfg);
		}else{
			authService.deleteAuthCfg(newsCfg);
			newsAuthCfgRepository.removeAuthCfg(newsCfg.getUid());//删除redis相应新闻资讯配置节点
		}
		if (productCfg.getDayLimit() > 0) {
			productCfg.setAuthId(auth.getId());
			authService.saveAuthCfg(productCfg);
		} else {
			authService.deleteAuthCfg(productCfg);
		}
		if (couponCfg.getDayLimit() > 0) {
			couponCfg.setAuthId(auth.getId());
			authService.saveAuthCfg(couponCfg);
		} else {
			authService.deleteAuthCfg(couponCfg);
		}
		if (invitationCfg.getDayLimit() > 0) {
			invitationCfg.setAuthId(auth.getId());
			authService.saveAuthCfg(invitationCfg);
		} else {
			authService.deleteAuthCfg(invitationCfg);
		}
		if(supplyCfg.getDayLimit() >0){
			supplyCfg.setAuthId(auth.getId());
			authService.saveAuthCfg(supplyCfg);
		}else{
			authService.deleteAuthCfg(supplyCfg);
		}
		if (vipCfg.getDayLimit() > 0) {
			vipCfg.setAuthId(auth.getId());
			authService.saveAuthCfg(vipCfg);
		} else {
			authService.deleteAuthCfg(vipCfg);
		}
		if(activityCfg.getDayLimit() >0){
			activityCfg.setAuthId(auth.getId());
			authService.saveAuthCfg(activityCfg);
		}else{
			authService.deleteAuthCfg(activityCfg);
		}
		if (bookCfg.getDayLimit() > 0) {
			bookCfg.setAuthId(auth.getId());
			authService.saveAuthCfg(bookCfg);

		} else {
			authService.deleteAuthCfg(bookCfg);
		}
		if (paymentCfg.getDayLimit() > 0) {
			paymentCfg.setAuthId(auth.getId());
			authService.saveAuthCfg(paymentCfg);
		} else {
			authService.deleteAuthCfg(paymentCfg);
		}
		//TODO 更多业务配置
		redirectAttributes.addFlashAttribute("message", "添加授权成功");
		return "redirect:/mgr/auth/index";
	}

	/**
	 * 更新授权
	 * @param auth
	 * @param nac 新闻权限配置
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("auth")Auth auth,
			@ModelAttribute(AuthCfg.AUTH_NEWS)NewsAuthCfg newsCfg,
			@ModelAttribute(AuthCfg.AUTH_PRODUCT)ProductAuthCfg productCfg,
			@ModelAttribute(AuthCfg.AUTH_ACTIVITY)ActivityAuthCfg activityCfg,
			@ModelAttribute(AuthCfg.AUTH_BOOK)BookAuthCfg bookCfg,
			@ModelAttribute(AuthCfg.AUTH_COUPON)CouponAuthCfg couponCfg,
			@ModelAttribute(AuthCfg.AUTH_INVITATION)InvitationAuthCfg invitationCfg,
			@ModelAttribute(AuthCfg.AUTH_PAYMENT)PaymentAuthCfg paymentCfg,
			@ModelAttribute(AuthCfg.AUTH_SUPPLY)SupplyAuthCfg supplyCfg,
			@ModelAttribute(AuthCfg.AUTH_VIP)VipAuthCfg vipCfg,
			RedirectAttributes redirectAttributes){
		Auth con = authService.findByUid(auth.getUid());
		authService.update(con);
		if(newsCfg.getDayLimit()>0 && newsCfg.getMonthVideoLimit()>0){
			newsCfg.setAuthId(con.getId());
			authService.saveAuthCfg(newsCfg);
		}else{
			authService.deleteAuthCfg(newsCfg);
			newsAuthCfgRepository.removeAuthCfg(newsCfg.getUid());//删除redis相应新闻资讯配置节点
		}
		if (productCfg.getDayLimit() > 0) {
			productCfg.setAuthId(con.getId());
			authService.saveAuthCfg(productCfg);
		} else {
			authService.deleteAuthCfg(productCfg);
		}
		if (couponCfg.getDayLimit() > 0) {
			couponCfg.setAuthId(con.getId());
			authService.saveAuthCfg(couponCfg);
		} else {
			authService.deleteAuthCfg(couponCfg);
		}
		if (invitationCfg.getDayLimit() > 0) {
			invitationCfg.setAuthId(con.getId());
			authService.saveAuthCfg(invitationCfg);
		} else {
			authService.deleteAuthCfg(invitationCfg);
		}
		if(supplyCfg.getDayLimit() >0){
			supplyCfg.setAuthId(con.getId());
			authService.saveAuthCfg(supplyCfg);
		}else{
			authService.deleteAuthCfg(supplyCfg);
		}
		if (vipCfg.getDayLimit() > 0) {
			vipCfg.setAuthId(con.getId());
			authService.saveAuthCfg(vipCfg);
		} else {
			authService.deleteAuthCfg(vipCfg);
		}
		if(activityCfg.getDayLimit() >0){
			activityCfg.setAuthId(con.getId());
			authService.saveAuthCfg(activityCfg);
		}else{
			authService.deleteAuthCfg(activityCfg);
		}
		if (bookCfg.getDayLimit() > 0) {
			bookCfg.setAuthId(con.getId());
			authService.saveAuthCfg(bookCfg);

		} else {
			authService.deleteAuthCfg(bookCfg);
		}
		if (paymentCfg.getDayLimit() > 0) {
			paymentCfg.setAuthId(con.getId());
			authService.saveAuthCfg(paymentCfg);
		} else {
			authService.deleteAuthCfg(paymentCfg);
		}
		//TODO 更多授权模块
		
		redirectAttributes.addFlashAttribute("message", "修改授权成功");
		return "redirect:/mgr/auth/index";
	}
	
	/**
	 * 删除授权
	 * @param uid 租户id
	 * @throws Exception 
	 */
	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void del(@RequestParam(value = "id")String uid) throws Exception{
		Auth auth = authService.findByUid(uid);
		if(auth == null)
		{
			throw new Exception("无授权可删除！");
		}
		authService.delById(auth.getId());
		newsAuthCfgRepository.removeAuthCfg(uid);//删除redis相应新闻资讯配置节点
		//TODO 更多授权模块删除
	}
}
