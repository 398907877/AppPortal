package com.huake.saas.supply.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.category.entity.IndustryCategory;
import com.huake.saas.category.service.IndustryCategoryService;
import com.huake.saas.images.Photo;
import com.huake.saas.supply.entity.SupplyDemand;
import com.huake.saas.supply.entity.SupplyDemandPhoto;
import com.huake.saas.supply.entity.SupplyDemandType;
import com.huake.saas.supply.service.SupplyDemandService;
//import com.huake.saas.supply.service.SupplyDemandTypeService;


@Controller
@RequestMapping(value = "/mgr/supply")
public class SupplyDemandController extends BaseController {

	@Autowired
	private AccountService accountService;
	
	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;
	
	@Autowired
	private SupplyDemandService supplyDemandService;
	
	@Autowired
	private IndustryCategoryService categoryService;

	/*@Autowired
	private SupplyDemandTypeService supplyDemandTypeService;*/

	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class,"startDate",new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(Date.class,"endDate",new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put(BaseEntity.PAGE_CRTDATE_DESC, "默认(创建时间降序)");
		sortTypes.put(BaseEntity.PAGE_CRTDATE_ASC, "创建时间升序");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, Message.SEARCH_CONDITIONS);
		Page<SupplyDemand> supplyDemands = supplyDemandService
				.getUserSupplyDemand(getCurrentUID(), searchParams, pageNumber,
						pageSize, sortType);
		List<IndustryCategory> types = categoryService.getAllCategory();
		model.addAttribute(SupplyDemandType.PARMS_SUPPLY_DEMAND_TYPES, types);
		model.addAttribute(SupplyDemand.PARMS_SUPPLY_DEMANDS, supplyDemands);
		model.addAttribute(Message.SOFT_TYPES, sortTypes);
		model.addAttribute(Message.SOFT_TYPE, sortType);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute(Message.SEARCH_PARAMS, Servlets
				.encodeParameterStringWithPrefix(searchParams,
						Message.SEARCH_CONDITIONS));
		return "supply/index";
	}
	
	/**
	 * 跳入create界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		List<IndustryCategory> types = categoryService.getAllCategory();
		model.addAttribute(SupplyDemandType.PARMS_SUPPLY_DEMAND_TYPES, types);
		model.addAttribute(SupplyDemand.PARMS_SUPPLY_DEMAND, new SupplyDemand());
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_CREATE_PAGE);
		return "supply/add";
	}
	
	/**
	 * 保存产品
	 * 
	 * @param product
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(
			@ModelAttribute("supplyDemand") SupplyDemand supplyDemand,
			@RequestParam(value = "fileInput_thum") List<CommonsMultipartFile> fileInputxs,
			@RequestParam(value = "fileInput") List<CommonsMultipartFile> fileInputs,
			RedirectAttributes redirectAttributes,HttpServletRequest request)
			throws IOException {
		System.out.println("supplyDemand"+supplyDemand.getDetailInfo()+"xxxxxxxxxxxxxxxxxxxxxxxxxxx");
		
		User user = accountService.getUser(getCurrentUserId());
		supplyDemand.setUid(String.valueOf(user.getUid()));
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
					List<Photo> photos = supplyDemandService.uploadPhoto(
							fileInput, (MultipartHttpServletRequest) request);
					supplyDemand.setThumb(imgPath + photos.get(1).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		supplyDemandService.saveSupplyDemand(supplyDemand);
		if (null != fileInputs) {
			for (int i = 0; i < fileInputs.size(); i++) {
				CommonsMultipartFile fileInput = fileInputs.get(i);
				if (fileInputs.get(i) != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
					List<Photo> photos = supplyDemandService.uploadPhoto(
							fileInput, (MultipartHttpServletRequest) request);
					for(Photo photo : photos){
						SupplyDemandPhoto pic = new SupplyDemandPhoto();
						pic.setSupplyDemandId(supplyDemand.getId());
						pic.setFilePath(imgPath +photo.getFilePath());
						pic.setImgType(photo.getImgType());
						supplyDemandService.savePhoto(pic);
					}
				}
			}
		}
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.ADD_DATE_COMPLETE);
		return "redirect:/mgr/supply";
	}
	
	/**
	 * 跳转到update界面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		SupplyDemand demand = supplyDemandService.getSupplyDemand(id);  
		List<IndustryCategory> types =categoryService.getAllCategory();
		model.addAttribute(SupplyDemand.PARMS_SUPPLY_DEMAND, demand);
		model.addAttribute(SupplyDemandType.PARMS_SUPPLY_DEMAND_TYPES, types);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_UPDATE_PAGE);
		List<SupplyDemandPhoto> pictures = supplyDemandService.getPictures(demand.getId());
		model.addAttribute("pictures", pictures);
		return "supply/edit";
	}
	
	/**
	 * 更新信息
	 * 
	 * @param product
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateProduct(@Valid@ModelAttribute("supplyDemand") SupplyDemand supplyDemand,
			@RequestParam(value = "fileInput_thum") List<CommonsMultipartFile> fileInputxs,
			@RequestParam(value = "fileInput") List<CommonsMultipartFile> fileInputs,
			RedirectAttributes redirectAttributes,HttpServletRequest request)
			throws IOException {
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
					List<Photo> photos = supplyDemandService.uploadPhoto(
							fileInput, (MultipartHttpServletRequest) request);
					supplyDemand
							.setThumb(imgPath + photos.get(1).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		supplyDemandService.updateSupplyDemand(supplyDemand);
		if (null != fileInputs) {
			for (int i = 0; i < fileInputs.size(); i++) {
				CommonsMultipartFile fileInput = fileInputs.get(i);
				if (fileInputs.get(i) != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
					List<Photo> photos = supplyDemandService.uploadPhoto(
							fileInput, (MultipartHttpServletRequest) request);
					for (Photo photo : photos) {
						SupplyDemandPhoto pic = new SupplyDemandPhoto();
						pic.setSupplyDemandId(supplyDemand.getId());
						pic.setFilePath(imgPath +photo.getFilePath());
						pic.setImgType(photo.getImgType());
						supplyDemandService.savePhoto(pic);
					}
				}
			}
		}
		
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/supply";
	}
	
	/**
	 * 跳转到detail界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detial(@PathVariable("id") Long id, Model model) {
		SupplyDemand supplyDemand = supplyDemandService.getSupplyDemand(id);
		model.addAttribute(SupplyDemand.PARMS_SUPPLY_DEMAND, supplyDemand);
		String w = supplyDemand.getDetailInfo();
		String what = w.replaceAll("<p>", "").replaceAll("</p>", "");
		model.addAttribute("what", what);
		if(supplyDemand.getTypeId() != null){
			IndustryCategory type = categoryService.findById(supplyDemand.getTypeId());
			model.addAttribute(SupplyDemandType.PARMS_SUPPLY_DEMAND_TYPE, type);
		}
		
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "supply/detail";
	}

	

	/**
	 * ajax获取类型
	 * 
	 * @param id
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "select/type", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<IndustryCategory> deleteProduct(
			@RequestParam(value = "type", defaultValue = "1") int type,
			RedirectAttributes redirectAttributes) {
		List<IndustryCategory> types = categoryService.getAllCategory();
		return types;
	}

	/**
	 * ajax删除（非真正意义上的删除) 
	 * @param id
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public SupplyDemand deleteSupplyDemand(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		return supplyDemandService.deleteSupplyDemand(id);
	}

	/**
	 * 修改名字
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
		supplyDemandService.changeName(id, name);
	}

	/**
	 * �?��RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效�?先根据form的id从数据库查出Product对象,再把Form提交的内容绑定到该对象上�?	 * 因为仅update()方法的form中有id属�?，因此仅在update时实际执�?
	 */
	@ModelAttribute
	public void getSupplyDemand(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("supplyDemand",
					supplyDemandService.getSupplyDemand(id));
		}
	}
	
	/**
	 * ajax删除
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @throws AppBizException
	 */
	@RequestMapping(value = "{id}/delpic", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePic(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		supplyDemandService.deletePhoto(id);
	}
	
	@RequestMapping(value = "showInfo")
	public String showInfo() {
		return "supply/info";
	}
}
