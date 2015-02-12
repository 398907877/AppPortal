package com.huake.saas.ernie.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.huake.dict.entity.Dictionary;
import com.huake.dict.service.DictViewService;
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.ernie.entity.Ernie;
import com.huake.saas.ernie.entity.ErnieItem;
import com.huake.saas.ernie.entity.ErnieLog;
import com.huake.saas.ernie.service.ErnieItemService;
import com.huake.saas.ernie.service.ErnieLogService;
import com.huake.saas.ernie.service.ErnieService;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;


@Controller("ernieAdminLogController")
@RequestMapping("/mgr/ernieLog")
public class ErnieLogAdminController extends BaseController {

	@Autowired
	private ErnieLogService ernieLogService;

	@Autowired
	private DictViewService dictViewService;

	@Autowired
	private ErnieItemService ernieItemService;
	
	@Autowired
	private ErnieService ernieService;
	
	@Autowired
	private TenancyUserService tUserService;

	
	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize)
			throws AppBizException {
		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, new Sort(
				Direction.DESC, new String[] { "id" }));
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, Message.SEARCH_CONDITIONS);
		if(searchParams.get("EQ_erineId") == null){
			searchParams.put("EQ_erineId", 0);
		}
		Page<ErnieLog> ernieLog = ernieLogService.getAllErnieLog(getCurrentUID(), searchParams,
				pageable);
		List<Ernie> listErnie = ernieService.findByUidAndStatus(getCurrentUID(),BaseEntity.STATUS_VALIDE);
		ModelAndView mav = new ModelAndView();
		mav.addObject("uid", getCurrentUID());
		mav.addObject("ernieLogs", ernieLog);
		mav.addObject("ernies", listErnie);
		mav.addObject(Message.SEARCH_PARAMS, Servlets.encodeParameterStringWithPrefix(searchParams,
						Message.SEARCH_CONDITIONS));
		mav.setViewName("ernie/ernieLog/list");
		return mav;
	}
	
	/**
	 * 跳转到detail界面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detial(@PathVariable("id") Long id, Model model) {
		ErnieLog ernie = ernieLogService.findById(id);
		model.addAttribute("ernieLog", ernie);
		return "ernie/ernieLog/detail";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newErnie() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("ernieLog", new ErnieLog());
		mav.setViewName("ernie/ernieLog/new");
		List<TenancyUser> list = tUserService.findTenancyUsersByStatusAndUid(TenancyUser.normal, getCurrentUID());
		mav.addObject("TenancyUsers", list);
		List<Ernie> listErnie = ernieService.findByUidAndStatus(getCurrentUID(),BaseEntity.STATUS_VALIDE);
		mav.addObject("ernies", listErnie);
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute("ernieLog") ErnieLog ernie,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		ernieLogService.save(ernie);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.ADD_DATE_COMPLETE);
		return "redirect:/mgr/ernieLog/list";
	}

	@RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Long id, Model model) {
		ErnieLog ernie = ernieLogService.findById(id);
		model.addAttribute("ernieLog", ernie);
		List<TenancyUser> list = tUserService.findTenancyUsersByStatusAndUid(TenancyUser.normal, getCurrentUID());
		model.addAttribute("TenancyUsers", list);
		List<Ernie> listErnie = ernieService.findByUidAndStatus(getCurrentUID(),BaseEntity.STATUS_VALIDE);
		model.addAttribute("ernies", listErnie);
		List<ErnieItem> items = ernieItemService.findByErineId(id);
		model.addAttribute("items", items);
		return "ernie/ernieLog/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("ernieLog") ErnieLog ernie,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {	
		ernieLogService.update(ernie);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/ernieLog/list";
	}

	@RequestMapping(value = "{id}/delete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("id") Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		ernieLogService.deleteById(id);
		map.put("success", true);
		return map;
	}

	
	@RequestMapping(value = "{id}/selectItems", method = RequestMethod.GET)
	@ResponseBody
	public List<ErnieItem> selectItems(@PathVariable("id") Long id) {
	    List<ErnieItem> list = ernieItemService.findByErineId(id);
		return list;
	}

	@ModelAttribute("ernieCates")
	public Collection<Dictionary> getErniesCates() {
		return dictViewService.getSelectModelCollection(Constants.ERNIE_CATE);
	}
}
