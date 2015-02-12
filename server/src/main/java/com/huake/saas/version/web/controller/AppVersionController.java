package com.huake.saas.version.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;
import com.huake.saas.version.entity.AppVersionInfo;
import com.huake.saas.version.service.AppVersionInfoService;




/**
 * 应用版本管理，根据不同的应用客户端
 * @author laidingqing
 *
 */
@Controller
@RequestMapping("/mgr/apps/version/*")
public class AppVersionController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AppVersionController.class);

	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();

	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("createDate", "时间");
	}
	
	public static Map<String, String> getSortTypes() {
		return sortTypes;
	}

	public static void setSortTypes(Map<String, String> sortTypes) {
		AppVersionController.sortTypes = sortTypes;
	}


	@Autowired
	private AppVersionInfoService versionService;
	
	@Autowired
	private TenancyService tService;

	


	
	/**
	 *  app版本管理首页
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto")String sortType, Model model,
			ServletRequest request){
		Long memberId = getCurrentUserId();
		logger.info("memberId"+memberId+"用户ID===================");
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<AppVersionInfo> avis = versionService.findByCondition(memberId,searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("versions", avis);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "/version/index";
	}
	

	/**
	 * 删除version
	 */
    @RequestMapping(value="/{id}/delete",method= RequestMethod.GET)
    public String versiondelete(@PathVariable final Integer id){
    	versionService.deleteVersion(id);
	    return "redirect:/mgr/apps/version/index";
    }
	
	/**
	 * 修改页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id")Integer id,Model model){
		Map<String,Object> searchMap = new HashMap<String, Object>();
		searchMap.put("EQ_status", Tenancy.NODEL);
		List<Tenancy> list = tService.findAll(getCurrentUserId(), searchMap);
		model.addAttribute("tenancys", list);
		AppVersionInfo avi = versionService.findVersionById(id);
		model.addAttribute("versionInfo", avi);
		model.addAttribute("id", id);
		return "/version/edit";
	}
	
	/**
	 * 版本管理修改更新	 
	 * 
	 * @param member 用户
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateAppVersion(AppVersionInfo version,RedirectAttributes redirectAttributes){
		versionService.updateversion(version);
		redirectAttributes.addFlashAttribute("message", "修改版本成功");
	    return "redirect:/mgr/apps/version/index";
	}
	

	/**
	 * 版本管理添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addAppVersion(Model model){
		Map<String,Object> searchMap = new HashMap<String, Object>();
		searchMap.put("EQ_status", Tenancy.NODEL);
		List<Tenancy> list = tService.findAll(getCurrentUserId(), searchMap);
		model.addAttribute("tenancys", list);
		
		return "/version/add";
	}

	/**
	 * 版本管理保存
	 * @param version
	 * @param model
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String saveAppVersion(AppVersionInfo version,RedirectAttributes redirectAttributes){

		   logger.info("进行版本的保存！");
			this.versionService.addNewAppVersion(version);
			redirectAttributes.addFlashAttribute("message", "新加版本成功");
			return "redirect:/mgr/apps/version/index";
		
	}




}
