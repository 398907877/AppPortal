package com.huake.saas.tcustomer.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.category.entity.IndustryCategory;
import com.huake.saas.category.service.IndustryCategoryService;
import com.huake.saas.images.Photo;
import com.huake.saas.tcustomer.entity.CustomerType;
import com.huake.saas.tcustomer.entity.Tcustomer;
import com.huake.saas.tcustomer.service.TcustomerService;

@Controller("customerController")
@RequestMapping("/mgr/customer**")
public class CustomerController extends BaseController{
	
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
		CustomerController.sortTypes = sortTypes;
	}
	

	@Autowired	
	private TcustomerService TCservice;

	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;

	@Autowired
	private IndustryCategoryService categoryService;
	
	
	/**
	 * 合同管理首页
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String test(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto")String sortType, Model model,
			ServletRequest request){
			logger.info("租户客户管理首页");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Page<Tcustomer> customers = TCservice.findByCondition2(getCurrentUID(),searchParams, pageNumber, pageSize, sortType);
			List<IndustryCategory> category = categoryService.getAllCategory();
			model.addAttribute("category", category);
			model.addAttribute("customers", customers);
			model.addAttribute("sortType", sortType);
			model.addAttribute("sortTypes", sortTypes);
			model.addAttribute("uid", getCurrentUID());
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

			return "customer/index";
	
	}
	
//	/**
//	 * 租户客户首页
//	 * @param pageNumber
//	 * @param pageSize
//	 * @param sortType
//	 * @param model
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "index", method = RequestMethod.GET)
//	public String index(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
//			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
//			@RequestParam(value = "sortType", defaultValue = "auto")String sortType, Model model,
//			ServletRequest request){
//		
//        Long userId = getCurrentUserId();
//		logger.info("userId"+userId+"租户客户管理首页");
//		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
//		Page<Tcustomer> customers = TCservice.findByCondition(userId,searchParams, pageNumber, pageSize, sortType);
//		List<CustomerType> typeList = TCservice.findCustomerTypesByUserid(userId);
//		model.addAttribute("typeList", typeList);
//		model.addAttribute("customers", customers);
//		model.addAttribute("sortType", sortType);
//		model.addAttribute("sortTypes", sortTypes);
//		model.addAttribute("uid", getCurrentUId());
//		// 将搜索条件编码成字符串，用于排序，分页的URL
//		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
//
//		return "customer/index";
//	}
	
	
	
	/**
	 * 请求保存页面，跳转到新增页面
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model,@RequestParam(value = "uid")long uid){
        model.addAttribute("uid",uid);
        model.addAttribute("category", categoryService.getAllCategory());
        return "customer/add";
	}
	
	/**
	 * 保存方法，成功页面重定向
	 * @param customer
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Tcustomer customer,
			@RequestParam("fileInput_thum") List<CommonsMultipartFile> fileInputxs,
			ServletRequest request,RedirectAttributes redirectAttributes,
			@RequestParam(value = "uid")String uid){
	
		String [] typeIds = request.getParameterValues("types");

		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
				
					List<Photo> photos = TCservice.uploadPic(fileInput,
							(MultipartHttpServletRequest) request);
					customer.setPic(imgPath+photos.get(1).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		TCservice.save(customer, typeIds, String.valueOf(uid));
		redirectAttributes.addFlashAttribute("message", "创建客户成功");
		return "redirect:/mgr/customer/index";
	}
	
	
	/**
	 * 请求修改页面，跳转到修改的页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id")long id,Model model){
	   
		Tcustomer customer = TCservice.get(id);
		model.addAttribute("customer", customer);
		model.addAttribute("category", categoryService.getAllCategory());
		return "customer/edit";
	}
	
	/**
	 * 修改方法，成功后重定向到租户客户页面
	 * @param customer
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(Tcustomer customer,
			@RequestParam("fileInput_thum") List<CommonsMultipartFile> fileInputxs,
			ServletRequest request,RedirectAttributes redirectAttributes,@RequestParam(value = "uid")String uid){
	   
		String [] typeIds = request.getParameterValues("types");
		
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
				
					List<Photo> photos = TCservice.uploadPic(fileInput,
							(MultipartHttpServletRequest) request);
					
					customer.setPic(imgPath+photos.get(1).getFilePath());
				}
			}
		}
		TCservice.update(customer, typeIds);
		redirectAttributes.addFlashAttribute("message", "修改客户成功");
		return "redirect:/mgr/customer/index";
	}
	
	
	/**
	 * 删除方法 异步
	 * @param id
	 */
	@RequestMapping(value = "del", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void del(@RequestParam(value = "id")long id){
		Tcustomer customer = TCservice.get(id);
		TCservice.del(customer);
	}
	
	
	/**
	 * 详细页面显示
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "show", method = RequestMethod.GET)
	public String show(@RequestParam(value = "id")long id,Model model){
		Tcustomer customer = TCservice.get(id);
		model.addAttribute("customer", customer);
		return "customer/show";
	}
	
   //==============================================================================================	
	
	/**
	 * 
	 * 
	 * 
	 * 以下 是租户客户分类的操作
	 * 此操作由租户用户自行管理
	 * 
	 * 
	 */
	
   //================================================================================================
	/**
	 * 租户客户类型首页
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "type/index", method = RequestMethod.GET)
	public String tindex(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto")String sortType, Model model,
			ServletRequest request){
		
	        Long userId = getCurrentUserId();
			logger.info("userId"+userId+"租户客户类型管理首页");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Page<CustomerType> types = TCservice.findByTypeCondition(userId,searchParams, pageNumber, pageSize, sortType);
			model.addAttribute("typeList", types);
			model.addAttribute("sortType", sortType);
			model.addAttribute("sortTypes", sortTypes);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "customer/type/index";
	}
	
	
	/**
	 * 请求保存页面，跳转到新增页面
	 * @return
	 */
	@RequestMapping(value = "type/add", method = RequestMethod.GET)
	public String tadd(Model model){
	
		return "customer/type/add";
	}
	
	/**
	 * 保存方法，成功页面重定向
	 * @param customer
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "type/save", method = RequestMethod.POST)
	public String tsave(CustomerType type,ServletRequest request,RedirectAttributes redirectAttributes){
		long uid = getCurrentUID();
		type.setUid(uid);
		TCservice.saveType(type);
		redirectAttributes.addFlashAttribute("message", "创建客户类型成功");
		return "redirect:/mgr/customer/type/index"; 
		
	}
	
	
	/**
	 * 请求修改页面，跳转到修改的页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "type/edit", method = RequestMethod.GET)
	public String tedit(@RequestParam(value = "id")long id,Model model){
	   
		CustomerType type = TCservice.getType(id);
		model.addAttribute("type", type);
		return "customer/type/edit";
	}
	
	/**
	 * 修改方法，成功后重定向到租户客户页面
	 * @param customer
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "type/update", method = RequestMethod.POST)
	public String tupdate(CustomerType type,ServletRequest request,RedirectAttributes redirectAttributes){
	
		
		TCservice.updateType(type);
		redirectAttributes.addFlashAttribute("message", "修改客户类型成功");
		return "redirect:/mgr/customer/type/index"; 
	}
	
	
	/**
	 * 删除方法 异步
	 * @param id
	 */
	@RequestMapping(value = "type/del", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void tdel(@RequestParam(value = "id")long id){
		CustomerType type = TCservice.getType(id);
		TCservice.delType(type);
	}
	
	@RequestMapping(value = "zshowInfo")
	public String showInfo() {
		return "customer/info";
	}
}
