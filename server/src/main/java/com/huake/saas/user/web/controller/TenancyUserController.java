package com.huake.saas.user.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.huake.saas.account.service.ShiroDbRealm.ShiroUser;
import com.huake.saas.baidu.entity.Tag;
import com.huake.saas.baidu.service.TagService;
import com.huake.saas.base.web.controller.BaseController;

import com.huake.saas.tcustomer.web.controller.CustomerController;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;
import com.huake.saas.util.PassWordMd5;

/**
 * 会员注册用户管理
 * @author laidingqing
 *
 */
@Controller("tenancyUserController")
@RequestMapping("/mgr/tenancyUser**")
public class TenancyUserController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	private static final String PAGE_SIZE = "5";
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("createDate", "时间");
	}
	public static Map<String, String> getSortTypes() {
		return sortTypes;
	}

	public static void setSortTypes(Map<String, String> sortTypes) {
		TenancyUserController.sortTypes = sortTypes;
	}
	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, "birth", new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
	
	@Autowired	
	private TenancyUserService service;
	
	@Autowired
	private TagService tagService;
	
	
	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	public TenancyUserService getService() {
		return service;
	}

	public void setService(TenancyUserService service) {
		this.service = service;
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String test(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto")String sortType, Model model,
			ServletRequest request){
		    Long userId = getCurrentUserId();
			logger.info("userId"+userId+"会员管理");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Page<TenancyUser> list = service.findByCondition(userId, searchParams, pageNumber, pageSize, sortType);
			
			model.addAttribute("forumUsers", list);
			model.addAttribute("sortType", sortType);
			model.addAttribute("sortTypes", sortTypes);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

			return "sessionUsers/index";
	
	}
	
	/**
	 * 请求保存页面，跳转到新增页面
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model){

		return "sessionUsers/add";
	}
	
	/**
	 * 保存方法，成功页面重定向
	 * @param customer
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(TenancyUser user,ServletRequest request,RedirectAttributes redirectAttributes,Model model){		
		if(service.isExist(user.getLoginname().trim(),getCurrentUId()))
		{
			model.addAttribute("message", "APP用户登入名重复");
			return "sessionUsers/add";
		}
		user.setUid(getCurrentUId());
		String passWord = PassWordMd5.getPassWord(user.getPassword());
		user.setPassword(passWord);
		service.save(user);
		redirectAttributes.addFlashAttribute("message", "创建APP用户成功");
		return "redirect:/mgr/tenancyUser/index";
	}
	
	
	/**
	 * 请求修改页面，跳转到修改的页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id")long id,Model model){
	   
		TenancyUser user = service.findById(id);
		model.addAttribute("forumUser", user);
		return "sessionUsers/edit";
	}
	/**
	 *进入设置tag的页面 
	 */
	@RequestMapping(value="setTag",method=RequestMethod.GET)
	public String setTag(@RequestParam(value="id")long id,
			   @RequestParam(value="name")String name, Model model){
		List<Tag> lists=tagService.findAllTag();
		for (Tag tag : lists) {
			System.out.println("分类名称是："+tag.getName());
		}
		model.addAttribute("tags", lists);
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		System.out.println("id 是："+id+"，用户名是："+name);
		return "sessionUsers/setTag";
		}
	 
	
	
	/**
	 * 修改方法，成功后重定向到租户客户页面
	 * @param customer
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(TenancyUser user,ServletRequest request,RedirectAttributes redirectAttributes){
	   
	    service.update(user);
		redirectAttributes.addFlashAttribute("message", "修改App用户成功");
		return "redirect:/mgr/tenancyUser/index";
	}
	
	

	
	
	/**
	 * 修改密码，成功后重定向到租户客户页面
	 * @param customer
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "resetPwd", method = RequestMethod.GET)
	public String resetPwd(TenancyUser user,Model model){
	    TenancyUser user1 = service.findById(user.getId());
	    model.addAttribute("forumUser", user1);
		return "sessionUsers/resetPwd";
	}
	
	
	
	/**
	 * 修改密码，成功后重定向到租户客户页面
	 * @param customer
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "savePwd", method = RequestMethod.POST)
	public String savePwd(TenancyUser user,Model model,ServletRequest request,RedirectAttributes redirectAttributes){
		String pwdCipher = request.getParameter("pwdCipher");
		String confirmPwdCipher =request.getParameter("confirmPwdCipher");
	    if(!pwdCipher.trim().equals(confirmPwdCipher.trim()))
	    {
	    	 model.addAttribute("message", "两次输入的密码不匹配！");
	    	
	 		return "sessionUsers/resetPwd";
	    }    
		user.setPassword(confirmPwdCipher);
	    service.savePwd(user);
	    redirectAttributes.addFlashAttribute("message", "修改app用户密码成功");
		return "redirect:/mgr/tenancyUser/index";
	}
	
	
	
	/**
	 * 删除方法 异步
	 * @param id
	 */
	@RequestMapping(value = "del", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void del(@RequestParam(value = "id")long id){
		service.delById(id);
	}
	
	/**
	 * 激活方法 异步
	 * @param id
	 */
	@RequestMapping(value = "activate", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void activate(@RequestParam(value = "id")long id){
		service.activateById(id);
	}
	/**
	 * 详细页面显示
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "show", method = RequestMethod.GET)
	public String show(@RequestParam(value = "id")long id,Model model){
		TenancyUser user = service.findById(id);
		model.addAttribute("forumUser", user);
		return "sessionUsers/show";
	}
	
	
	
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public Long getCurrentUId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.uid;
	}
	
}
