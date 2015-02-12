package com.huake.saas.tenancy.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.auth.entity.Auth;
import com.huake.saas.auth.service.AuthService;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.tenancy.entity.Tenancy;



/**
 * @author hyl
 * 租户管理的相关控制，与租户下用户的操作，租户下授权的新建
 */
@Controller
@RequestMapping("/mgr/crm/**")
public class TenancyController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(TenancyController.class);
	private static final String PAGE_SIZE = "5";

	/**
	 * 到期提醒的天数 可在.property文件中修改
	 */
	@Value("#{envProps.day_limit}")
	private int dayLimit;
	/**
	 * 租户管理员默认登录名
	 */
	@Value("#{envProps.default_admin_name}")
	private String loginName;
	/**
	 * 租户管理员默认密码
	 */
	@Value("#{envProps.default_admin_password}")
	private String password;
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();

	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("createDate", "时间");
	}
	
	public static Map<String, String> getSortTypes() {
		return sortTypes;
	}

	public static void setSortTypes(Map<String, String> sortTypes) {
		TenancyController.sortTypes = sortTypes;
	}

	
	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class,"issueDate",new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class,"dueDate",new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class,"startDate",new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(Date.class,"endDate",new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@Autowired	
	private AccountService accountService;
	
	@Autowired	
	private  AuthService authService;

	@Autowired 
	private DictViewService dictViewService;
	
	
	/**
	 *  租户管理首页
	 * @param pageNumber 当前面
	 * @param pageSize   显示条数
	 * @param sortType  排序
	 * @param model   返回对象
	 * @param request  封装的请求
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto")String sortType, Model model,
			@RequestParam(value="deadline",defaultValue="0")String deadline ,ServletRequest request){
		Long userId = getCurrentUserId();
		logger.info("userId"+userId+"租户管理首页");

		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		/*if("1".equals(deadline)){
			searchParams.put("GTE_endDate", new Date());
			searchParams.put("LT", DateUtils.addDays(new Date(), dayLimit));
		}else if("2".equals(deadline)){
			searchParams.put("LT_endDate", new Date());
		}*/
		
		Page<Tenancy> tenancys = tenancyService.findTenanciesByCondition(userId,searchParams, pageNumber, pageSize, sortType,deadline);
		model.addAttribute("users", tenancys);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("deadline",deadline);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "crm/tenancy/index";
	}
	
	/**
	 * 租户信息编辑页面
	 * @param uid 租户id
	 * @return
	 */
	@RequestMapping(value = "tenancy/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "uid")Long uid,Model model){
		model.addAttribute("tenancy", tenancyService.findByUid(uid));
		return "crm/tenancy/edit";
	}
	
	/**
	 * 更新租户信息
	 * @param tenancy 租户对象
	 * @return
	 */
	@RequestMapping(value = "tenancy/update", method = RequestMethod.POST)
	public String update(Tenancy tenancy,RedirectAttributes redirectAttributes) {
		
		tenancyService.modifyTenancy(tenancy);
		redirectAttributes.addFlashAttribute("message", "修改租户成功");
		return "redirect:/mgr/crm/index";
	}
	
	/**
	 * 新增租户页面
	 * @return
	 */
	@RequestMapping(value = "tenancy/add", method = RequestMethod.GET)
	public String add(Model model){
		return "crm/tenancy/add";
	}
	
	
	/**
	 * 新增租户操作
	 * @param tenancy 租户对象
	 * @return
	 */
	@RequestMapping(value = "tenancy/save", method = RequestMethod.POST)
	public String save(Tenancy tenancy,RedirectAttributes redirectAttributes){
		tenancy = tenancyService.createTenancy(tenancy);
		User defaultAdmin = new User();//为租户添加默认管理员
		defaultAdmin.setName(tenancy.getName());
		defaultAdmin.setLoginName(loginName);
		defaultAdmin.setPassword(password);
		defaultAdmin.setPlainPassword(password);
		defaultAdmin.setRoles(User.USER_ROLE_ADMIN);
		defaultAdmin.setStatus(User.STATUS_VALIDE);
		defaultAdmin.setUid(tenancy.getUid());
		accountService.registerUser(defaultAdmin);
		
		redirectAttributes.addFlashAttribute("message", "创建租户成功，已为该租户添加默认管理员，请设置业务权限。");
		
		return "redirect:/mgr/crm/auth/grant?uid="+tenancy.getUid();
	}
	
	
	/**
	 * 删除租户，并删除租户下的操作员,删除所有合同与授权（真删除）
	 * @param uid
	 */
	@RequestMapping(value = "tenancy/del", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void delTenancy(@RequestParam(value = "uid")Long uid){
		Tenancy tenancy = tenancyService.getTenancy(uid);

		tenancyService.deleteTenancy(tenancy);
	}

	/**
	 * 租户信息展示 
	 * @param uid 租户id
	 * @return
	 */
	@RequestMapping(value = "tenancy/show", method = RequestMethod.GET)
	public String show(@RequestParam(value = "uid")Long uid, Model model){
		model.addAttribute("tenancy", tenancyService.getTenancy(uid));
		model.addAttribute("users", tenancyService.queryUserByUid(uid));
		model.addAttribute("bizcode", dictViewService.getDictViewList("ROLE"));
		return "crm/tenancy/show";
	}
	

	/**
	 * 租户信息展示 
	 * @param uid 租户id
	 * @return
	 */
	@RequestMapping(value = "tenancy/showKey", method = RequestMethod.GET)
	public String showKey(@RequestParam(value = "uid")Long uid, @RequestParam(value = "key")String key,Model model){
		model.addAttribute("tenancy", tenancyService.getTenancy(uid));
		model.addAttribute("key", key);
		return "crm/tenancy/showkey";
	}
	
	/**
	 * 租户基本信息和操作员信息
	 * @param uid 租户ID
	 * @return
	 */
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public String userList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto")String sortType, Model model,
			ServletRequest request){
		Long userId = getCurrentUserId();
		logger.info("userId"+userId+"用户管理首页");
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<User> users = accountService.findByCondition(userId,searchParams, pageNumber, pageSize, sortType);
		List<Tenancy> tenancies = tenancyService.getAllTenancies();
		model.addAttribute("users", users);
		model.addAttribute("tenancies", tenancies);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "crm/operator/info";
	}
	/*-----------------------------操作员管理------------------------------------------*/ 
	/**
	 * 操作员编辑页面
	 * @param oid 用户ID
	 * @return
	 */
	@RequestMapping(value = "/operator/edit", method = RequestMethod.GET)
	public String editOperator(@RequestParam(value = "oid")String id,Model model){
		User user = tenancyService.getUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("id", id);
		User currentUser = tenancyService.getUserById(getCurrentUserId().toString());
		if(User.USER_ROLE_SYS_ADMIN.equals(currentUser.getRoles())){
			model.addAttribute("uid", user.getUid());
			List<Tenancy> tenancies = tenancyService.getAllTenancies();
			model.addAttribute("tenancies", tenancies);
		}
		return "crm/operator/edit";
	}
	
	/**
	 * 操作员更新页面
	 * @param user 用户
	 * @return
	 */
	@RequestMapping(value = "/operator/update", method = RequestMethod.POST)
	public String updateUser(User user,RedirectAttributes redirectAttributes){
		
		
		User u1 = tenancyService.getUserById(String.valueOf(user.getId()));
		if(u1.getUid() != user.getUid() ){
			User u = tenancyService.findByloginNameAndUid(u1.getLoginName(),user.getUid());
	    	if(u != null)
	    	{
				redirectAttributes.addFlashAttribute("message", "修改失败，该租户已存在此用户！");
				return "redirect:/mgr/crm/userList?uid=" + u1.getUid();
	    	}
		}
		u1.setName(user.getName());
		u1.setRoles(user.getRoles());
		u1.setStatus(user.getStatus());
		u1.setUid(user.getUid());
		tenancyService.updateUser(u1);
		redirectAttributes.addFlashAttribute("message", "修改用户成功");
	    return "redirect:/mgr/crm/userList?uid=" + u1.getUid();
	}
	
	/**
	 * 新增操作员页面
	 * @param uid 租户ID
	 * @return
	 */
	@RequestMapping(value = "/operator/add", method = RequestMethod.GET)
	public String addUser(@RequestParam(value = "uid", required = true)long uid,Model model){
		model.addAttribute("uid", uid);
		User currentUser = tenancyService.getUserById(getCurrentUserId().toString());
		if(User.USER_ROLE_SYS_ADMIN.equals(currentUser.getRoles())){
			List<Tenancy> tenancies = tenancyService.getAllTenancies();
			model.addAttribute("tenancies", tenancies);
		}
		return "crm/operator/add";
	}
	
	/**
	 * 新增操作员
	 * @param Usertest 用户
	 * @return
	 */
	@RequestMapping(value = "/operator/save", method = RequestMethod.POST)
	public String saveUser(User user,ServletRequest request,Model model,RedirectAttributes redirectAttributes){
		String password = request.getParameter("confirmPwdCipher");
		user.setPlainPassword(password);
		boolean flag = tenancyService.isOnly(user);
		if(flag){
			accountService.registerUser(user);
			redirectAttributes.addFlashAttribute("message", "添加租户操作员成功");
			return "redirect:/mgr/crm/userList?uid=" + user.getUid();
		}
		model.addAttribute("uid", user.getUid());
		model.addAttribute("message", "用户名重复");
	    return "crm/operator/add"; 

		
	}

	/**
	 * 重置密码 
	 * @param oid 用户ID
	 * @return
	 */
	@RequestMapping(value = "/operator/resetPwd", method = RequestMethod.GET)
	public String resetPwd(@RequestParam(value = "oid")String oid,Model model){
		User user = tenancyService.getUserById(oid);
        model.addAttribute("user", user);
        model.addAttribute("id", oid);
        model.addAttribute("uid",user.getUid());
		return "crm/operator/resetPwd";
	}
	
	/**
	 * 更新密码
	 * @param oid 用户id 
	 * @return
	 */
	@RequestMapping(value = "/operator/savePwd", method = RequestMethod.POST)
	public String savePwd(@RequestParam(value = "id")String oid,ServletRequest request,RedirectAttributes redirectAttributes){
		User user = tenancyService.getUserById(oid);
		String password = request.getParameter("confirmPwdCipher");
		user.setPlainPassword(password);
		accountService.updateUser(user);
		redirectAttributes.addFlashAttribute("message", "修改用户密码成功");
		return "redirect:/mgr/crm/userList?uid=" + user.getUid();
	}

	
	/**
	 * 删除操作员
	 * @param oid 用户id
	 */
	@RequestMapping(value = "/operator/del", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<String,String> delUser(@RequestParam(value = "oid")String oid){
	    tenancyService.delTenancyUser(oid);
	    Map<String,String> map = new HashMap<String, String>();
		map.put("success", "true");
		return map;
	}
	
	/**
	 * 锁定操作员
	 * @param oid 用户id
	 */
	@RequestMapping(value = "/operator/lock", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<String,String> lockUser(@RequestParam(value = "oid")String oid){
		User user = tenancyService.getUserById(oid);
		tenancyService.lockUser(user);
		Map<String,String> map = new HashMap<String, String>();
		map.put("success", "true");
		return map;
	}
	
	/**
	 * 解锁操作员
	 * @param oid 用户id
	 */
	@RequestMapping(value = "/operator/unlock", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<String,String> unlockOperator(@RequestParam(value = "oid")String oid){
		User user = tenancyService.getUserById(oid);
		tenancyService.unlockUser(user);
		Map<String,String> map = new HashMap<String, String>();
		map.put("success", "true");
		return map;
	}
	
	/*-----------------------------授权-----------------------------------*/ 
	/**
	 * 授权
	 * @param uid 租户id
	 * @return
	 */
	@RequestMapping(value = "/auth/grant", method = RequestMethod.GET)
	public String addAuth(@RequestParam(value = "uid")Long uid, 
			Model model){
		Tenancy tenancy = tenancyService.getTenancy(uid);
		
		//删除合同模块，已重新编写
		//加在业务模块列表
		List<Dictionary> bizCodes = (List<Dictionary>)dictViewService.getSelectModelCollection("BIZ_CODE");
		Auth auth = authService.findByUid(uid.toString());//查询是否授权过
		Map<String,Object> authCfgs = new HashMap<String,Object>();
		if(auth != null){
			for(Dictionary d : bizCodes){
				authCfgs.put(d.getKey(), authService.findAuthCfgByAuthId(auth.getId(), d.getKey()));
			}
			model.addAttribute("auth", auth);
			model.addAttribute("authCfgs", authCfgs);
		}
		model.addAttribute("bizCodes", bizCodes);
		model.addAttribute("tenancy", tenancy);
		
		return auth==null ? "auth/add":"auth/edit";
	}
	
	
	/**
	 * 新增授权
	 * @param auth 授权对象
	 * @return
	 */
	/*
	@RequestMapping(value = "/auth/save", method = RequestMethod.POST)
	public String save(Auth auth,ServletRequest request,RedirectAttributes redirectAttributes) {
	    auth.setStatus(Auth.STATUS_ACTIVE);
		auth.setVersion(1);
		authService.create(auth);
		
		
        redirectAttributes.addFlashAttribute("message", "添加授权成功");
		return "redirect:/mgr/auth/index";
	}*/
	

}
