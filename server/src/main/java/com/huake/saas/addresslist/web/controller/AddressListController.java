package com.huake.saas.addresslist.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.addresslist.entity.AddressList;
import com.huake.saas.addresslist.entity.AddressListType;
import com.huake.saas.addresslist.service.AddressListService;
import com.huake.saas.addresslist.service.AddressListTypeService;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.images.Photo;
import com.huake.saas.place.entity.PlaceRelation;
import com.huake.saas.place.service.PlaceRelationService;
import com.huake.saas.tcustomer.service.TcustomerService;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

@Controller("addressListController")
@RequestMapping("/mgr/addressList**")
public class AddressListController extends BaseController{

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("crtDate", "时间升序");
	}
	public static Map<String, String> getSortTypes() {
		return sortTypes;
	}

	public static void setSortTypes(Map<String, String> sortTypes) {
		AddressListController.sortTypes = sortTypes;
	}
	
	@Autowired
	private TenancyUserService userService;
	
	@Autowired	
	private AddressListService service;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired	
	private AddressListTypeService addressListTypeService;
	
	@Autowired
	private PlaceRelationService placeService;
	
	@Autowired
	private TcustomerService tcustomerService;
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String test(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto")String sortType, Model model,
			ServletRequest request){
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Page<AddressList> list = service.findByCondition(getCurrentUID(), searchParams, pageNumber, pageSize, sortType);
			/*	User user = accountService.getUser(userId);
			List<AddressListType> addressListTypes = null;
			if(user.getRoles().equals(User.USER_ROLE_ADMIN)){
				addressListTypes = addressListTypeService
						.getAddressListTypes(BaseEntity.STATUS_VALIDE);
			}else{
				long uid = user.getUid();
				addressListTypes = addressListTypeService
					.getAddressListTypes(BaseEntity.STATUS_VALIDE,String.valueOf(uid));
			}*/
			List<AddressListType> addressListTypes = addressListTypeService
					.getAddressListTypes(BaseEntity.STATUS_VALIDE,String.valueOf(getCurrentUID()));
			model.addAttribute(AddressListType.PARMS_ADDRESSLIST_TYPES, addressListTypes);
			model.addAttribute("AddressLists", list);
			model.addAttribute("sortType", sortType);
			model.addAttribute("sortTypes", sortTypes);
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			return "addressList/index";
	
	}
	
	
	
	/**
	 * 请求保存页面，跳转到新增页面
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model){
		//User user = accountService.getUser(getCurrentUserId());
		/*List<AddressListType> addressListTypes = null;
		if(user.getRoles().equals(User.USER_ROLE_ADMIN)){
			addressListTypes = addressListTypeService
					.getAddressListTypes(BaseEntity.STATUS_VALIDE);
		}else{
			long uid = user.getUid();
			addressListTypes = addressListTypeService
				.getAddressListTypes(BaseEntity.STATUS_VALIDE,String.valueOf(uid));
		}*/
		List<AddressListType> addressListTypes = addressListTypeService
				.getAddressListTypes(BaseEntity.STATUS_VALIDE,String.valueOf(getCurrentUID()));
		model.addAttribute("users", userService.findTenancyUsersByStatusAndUid(TenancyUser.normal, getCurrentUID()));
		model.addAttribute(AddressListType.PARMS_ADDRESSLIST_TYPES, addressListTypes);
		model.addAttribute("uid", getCurrentUID());
		model.addAttribute("province", placeService.findProvinces(PlaceRelation.LEVEL_1));
		model.addAttribute("tcustomers", tcustomerService.findAllTcustomer(getCurrentUID()));
		return "addressList/add";
	}
	
	/**
	 * 保存方法，成功页面重定向
	 * @param customer
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(AddressList addressList,
			@RequestParam("fileInput_thum") List<CommonsMultipartFile> fileInputxs,
			ServletRequest request,RedirectAttributes redirectAttributes){
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {

					List<Photo> photos = service.uploadThumb(fileInput,
							(MultipartHttpServletRequest) request);
					addressList
							.setAvatar(imgPath + photos.get(1).getFilePath());
				}
			}
		}
		
		service.save(addressList);
		redirectAttributes.addFlashAttribute("message", "创建通讯成功");
		return "redirect:/mgr/addressList/index";
	}
	
	
	/**
	 * 请求修改页面，跳转到修改的页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id")long id,Model model){
		/*User user = accountService.getUser(getCurrentUserId());
		List<AddressListType> addressListTypes = null;
		if(user.getRoles().equals(User.USER_ROLE_ADMIN)){
			addressListTypes = addressListTypeService
					.getAddressListTypes(BaseEntity.STATUS_VALIDE);
		}else{
			long uid = user.getUid();
			addressListTypes = addressListTypeService
				.getAddressListTypes(BaseEntity.STATUS_VALIDE,String.valueOf(uid));
		}*/
		List<AddressListType> addressListTypes = addressListTypeService
				.getAddressListTypes(BaseEntity.STATUS_VALIDE,String.valueOf(getCurrentUID()));
		model.addAttribute("users", userService.findTenancyUsersByStatusAndUid(TenancyUser.normal, getCurrentUID()));
		model.addAttribute(AddressListType.PARMS_ADDRESSLIST_TYPES, addressListTypes);
		AddressList add = service.findById(id);
		model.addAttribute("addressList", add);
		model.addAttribute("province", placeService.findProvinces(PlaceRelation.LEVEL_1));
		model.addAttribute("tcustomers", tcustomerService.findAllTcustomer(getCurrentUID()));
		return "addressList/edit";
	}
	
	/**
	 * 修改方法，成功后重定向到租户客户页面
	 * @param customer
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(AddressList addressList,
			@RequestParam("fileInput_thum") List<CommonsMultipartFile> fileInputxs,
			ServletRequest request,RedirectAttributes redirectAttributes){
		System.out.println(addressList.getAvatar());
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {

					List<Photo> photos = service.uploadThumb(fileInput,
							(MultipartHttpServletRequest) request);
					addressList
							.setAvatar(imgPath + photos.get(1).getFilePath());
				}
			}
		}
	    service.update(addressList);
		redirectAttributes.addFlashAttribute("message", "修改通讯成功");
		return "redirect:/mgr/addressList/index";
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
	 * 详细页面显示
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "show", method = RequestMethod.GET)
	public String show(@RequestParam(value = "id")long id,Model model){
		AddressList add = service.findById(id);
		model.addAttribute("addressList", add);
		return "addressList/show";
	}
	
	@RequestMapping(value = "/type/list",method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, Message.SEARCH_CONDITIONS);
		Page<AddressListType> addressListTypes = addressListTypeService.getUserAddressListType(getCurrentUID(), searchParams, pageNumber, pageSize, sortType);
		model.addAttribute(AddressListType.PARMS_ADDRESSLIST_TYPES, addressListTypes);
		model.addAttribute(Message.SOFT_TYPE, sortType);
		model.addAttribute(Message.SOFT_TYPES, sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute(Message.SEARCH_PARAMS, Servlets.encodeParameterStringWithPrefix(searchParams, Message.SEARCH_CONDITIONS));
		return "addressList/type";
	}
	
	
	/**
	 * ajax删除（非真正意义上的删除）
	 * 
	 * @param id
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "/type/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public AddressListType deleteNewsCategory(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		return addressListTypeService.deleteAddressListType(id);
	}
	
	/**
	 * 修改组名
	 * 
	 * @param name
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/type/changeName", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeName(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "id", required = false) Long id) {
		addressListTypeService.changeName(id, name);
	}
	
	/**
	 * 添加分组
	 * 
	 * @param name
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/type/create", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void create(
			@RequestParam(value = "name", required = false) String name) {
		User user = accountService.getUser(getCurrentUserId());
		String uid = String.valueOf(user.getUid());
		addressListTypeService.saveAddressListType(uid,name);
	}
	
	/**
	 * 根据省份查找城市
	 * @param provinceId
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/findCitys",method=RequestMethod.GET)	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<PlaceRelation> findCitys(@RequestParam(value="provinceId",required=true) Integer provinceId){
		return placeService.findCitys(provinceId);
	}
	
	/**
	 * 格式化省份
	 * @param cityCode
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/formatProvince",method=RequestMethod.GET)	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public PlaceRelation formatProvince(@RequestParam(value="cityCode",required=true) String cityCode) {
		PlaceRelation city = placeService.findCitysName(cityCode);
		return placeService.findProvinceById(city.getParentLevel());
	}
	
	/**
	 * 格式化城市
	 * @param cityCode
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/formatCity",method=RequestMethod.GET)	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<PlaceRelation> formatCity(@RequestParam(value="cityCode",required=true) String cityCode) {
		PlaceRelation city = this.placeService.findCitysName(cityCode);
		return this.placeService.findCitys(city.getParentLevel());
	}
	
	@RequestMapping(value = "showInfo")
	public String showInfo() {
		return "addressList/info";
	}
}
