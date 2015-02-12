package com.huake.saas.company.web.controller;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.company.entity.CompanyCategory;
import com.huake.saas.company.service.CompanyCategoryService;

@Controller
@RequestMapping(value = "/mgr/company/category")
public class CompanyCategoryController extends BaseController{
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	
	
	static {
		sortTypes.put(BaseEntity.PAGE_CRTDATE_DESC, "默认(创建时间降序)");
		sortTypes.put(BaseEntity.PAGE_CRTDATE_ASC, "创建时间升序");
	}
	
	@Autowired
	private CompanyCategoryService companyCategoryService;
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, Message.SEARCH_CONDITIONS);
		Long userId = getCurrentUserId();
		Page<CompanyCategory> companyCategorys = companyCategoryService.getUserCompanyCategory(userId, searchParams, pageNumber, pageSize, sortType);
		model.addAttribute(CompanyCategory.PARMS_COMPANY_CATEGORYS, companyCategorys);
		model.addAttribute(Message.SOFT_TYPE, sortType);
		model.addAttribute(Message.SOFT_TYPES, sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute(Message.SEARCH_PARAMS, Servlets.encodeParameterStringWithPrefix(searchParams, Message.SEARCH_CONDITIONS));
		return "company/category";
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
	public CompanyCategory deleteCompanyCategory(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		return companyCategoryService.deleteCompanyCategory(id);
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
		companyCategoryService.changeName(id, name);
	}
	
	/**
	 * 添加产品类型
	 * 
	 * @param name
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void create(
			@RequestParam(value = "name", required = false) String name) {
		User user = accountService.getUser(getCurrentUserId());
		String uid = String.valueOf(user.getUid());
		companyCategoryService.saveCompanyCategory(uid,name);
	}
}
