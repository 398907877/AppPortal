package com.huake.saas.supply.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.supply.entity.SupplyDemandType;
import com.huake.saas.supply.service.SupplyDemandTypeService;


@Controller
@RequestMapping(value = "/mgr/supply/type")
public class SupplyDemandTypeController extends BaseController{
	
	private static Logger logger = LoggerFactory
			.getLogger(SupplyDemandController.class);
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put(BaseEntity.PAGE_CRTDATE_DESC, "默认(创建时间降序)");
	}
	
	@Autowired
	private SupplyDemandTypeService demandTypeService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, Message.SEARCH_CONDITIONS);
		Page<SupplyDemandType> supplyDemandTypes = demandTypeService.getUserSupplyDemandType(searchParams, pageNumber, pageSize, sortType);
		model.addAttribute(SupplyDemandType.PARMS_SUPPLY_DEMAND_TYPES, supplyDemandTypes);
		model.addAttribute(Message.SOFT_TYPE, sortType);
		model.addAttribute(Message.SOFT_TYPES, sortTypes);
		logger.info("*****************************"+supplyDemandTypes.getContent().size());
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute(Message.SEARCH_PARAMS, Servlets.encodeParameterStringWithPrefix(searchParams, Message.SEARCH_CONDITIONS));
		return "supply/category";
	}
	
	
	/**
	 * 跳入create界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute(SupplyDemandType.PARMS_SUPPLY_DEMAND_TYPE, new SupplyDemandType());
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_CREATE_PAGE);
		return "supply/addType";
	}
	
	
	/**
	 * 保存类型信息
	 * @param product
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveSupplyDemandType(
			@ModelAttribute("supplyDemandType") SupplyDemandType supplyDemandType,
			RedirectAttributes redirectAttributes)
			throws IOException {
		demandTypeService.saveNewSupplyDemandType(supplyDemandType);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.ADD_DATE_COMPLETE);
		return "redirect:/mgr/supply/type";
	}
	
	
	/**
	 * ajax删除（非真正意义上的删除）
	 * 
	 * @param id
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSupplyDemandType(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		demandTypeService.deleteSupplyDemandType(id);
	}
	
	
	/**
	 * 修改名字
	 * 
	 * @param name
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changeName", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeName(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "id", required = false) Long id) {
		demandTypeService.changeName(id, name);
	}
}
