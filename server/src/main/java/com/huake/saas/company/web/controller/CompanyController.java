package com.huake.saas.company.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.huake.saas.base.service.ImageUploadService;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.company.entity.Company;
import com.huake.saas.company.entity.CompanyCategory;
import com.huake.saas.company.entity.CompanyPictures;
import com.huake.saas.company.service.CompanyCategoryService;
import com.huake.saas.company.service.CompanyService;
import com.huake.saas.images.Photo;
import com.huake.saas.news.entity.NewsPictures;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;

@Controller
@RequestMapping(value = "/mgr/company")
public class CompanyController extends BaseController{
	
	private static Logger logger = LoggerFactory
			.getLogger(CompanyController.class);
	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;
	@Autowired
	private CompanyService companyService;

	@Autowired
	private ImageUploadService imageUploadService;
	
	@Autowired
	private TenancyService tenancyService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private CompanyCategoryService categoryService;
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put(BaseEntity.PAGE_CRTDATE_DESC, "默认(创建时间降序)");
		sortTypes.put(BaseEntity.PAGE_CRTDATE_ASC, "创建时间升序");
	}
	
	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class,"crtDate",new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

	}

	/**
	 * 主界面
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, Message.SEARCH_CONDITIONS);
		Long userId = getCurrentUserId();
		Page<Company> companys = companyService.getUserCompany(userId, searchParams,
				pageNumber, pageSize, sortType);
		for(Company company:companys.getContent()){
			long categoryId = company.getCategoryId();
			company.setCategory(categoryService.getCompanyCategory(categoryId).getName());
		}
		User user = accountService.getUser(userId);
		List<CompanyCategory> categories = null;
		if(user.getRoles().equals(User.USER_ROLE_ADMIN)){
			categories = categoryService
					.getAllCompanyCategory(CompanyCategory.STATUS_VALIDE);
		}else{
			long uid =  user.getUid();
		      categories = categoryService
				.getAllCompanyCategorys(CompanyCategory.STATUS_VALIDE,uid);
		}
		if (!User.USER_ROLE_ADMIN.equals(user.getRoles())) {
			model.addAttribute("uid", user.getUid());
		}
		model.addAttribute("companyCategories", categories);
		model.addAttribute(Company.PARMS_COMPANYS, companys);
		model.addAttribute(Message.SOFT_TYPE, sortType);
		model.addAttribute(Message.SOFT_TYPES, sortTypes);
		//将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute(Message.SEARCH_PARAMS, Servlets
				.encodeParameterStringWithPrefix(searchParams,
						Message.SEARCH_CONDITIONS));
		return "company/index";
	}

	/**
	 * 跳入add页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		User user = accountService.getUser(getCurrentUserId());
		List<CompanyCategory> categories = null;
		if(user.getRoles().equals(User.USER_ROLE_ADMIN)){
			categories = categoryService
					.getAllCompanyCategory(CompanyCategory.STATUS_VALIDE);
		}else{
			long uid =  user.getUid();
		      categories = categoryService
				.getAllCompanyCategorys(CompanyCategory.STATUS_VALIDE,uid);
		}
		model.addAttribute(Company.PARMS_COMPANY, new Company());
		model.addAttribute(CompanyCategory.PARMS_COMPANY_CATEGORYS, categories);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_CREATE_PAGE);
		return "company/add";
	}

	/**
	 * 保存企业信息
	 * 
	 * @param company
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCompany(@ModelAttribute("company") Company company,
			@RequestParam("fileInput") List<CommonsMultipartFile> fileInputs,
			@RequestParam("fileInput_thum") List<CommonsMultipartFile> fileInputxs,
			HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws IOException {
		//User user = accountService.getUser(getCurrentUserId());
		//company.setUid(String.valueOf(user.getUid()));
		
		//添加关联图片
		if (null != fileInputs) {
			for (CommonsMultipartFile fileInput : fileInputs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
					List<Photo> photos = companyService.uploadPhotos(fileInput,
							(MultipartHttpServletRequest) request);
					Long companyId = company.getId();
					companyService.savaCompanyPictures(imgPath
							+ photos.get(1).getFilePath(), companyId);
				}
			}
		}
		// 上传logo
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
					List<Photo> photos = companyService.uploadPic(fileInput,
							(MultipartHttpServletRequest) request);
					company.setPic(imgPath + photos.get(2).getFilePath());
				}
			}
		}
		companyService.saveCompany(company);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.ADD_DATE_COMPLETE);
		return "redirect:/mgr/index";//"redirect:/mgr/company";
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
		Company company = companyService.getCompany(id);
		model.addAttribute(Company.PARMS_COMPANY, company);
		User user = accountService.getUser(getCurrentUserId());
		List<CompanyCategory> categories = null;
		if(user.getRoles().equals(User.USER_ROLE_ADMIN)){
			categories = categoryService
					.getAllCompanyCategory(CompanyCategory.STATUS_VALIDE);
		}else{
			long uid =  user.getUid();
		      categories = categoryService
				.getAllCompanyCategorys(CompanyCategory.STATUS_VALIDE,uid);
		}
		model.addAttribute(CompanyCategory.PARMS_COMPANY_CATEGORYS, categories);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_UPDATE_PAGE);
		List<CompanyPictures> pictures = companyService.getPictures(company.getId().intValue());
		model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);
		return "company/edit";
	}

	
	/**
	 * 企业介绍，详细图片上传
	 * @param file
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestParam("upload") CommonsMultipartFile file, @RequestParam("CKEditorFuncNum") String callback,HttpServletResponse response){
		String js;
		try {
			js = imageUploadService.upload(file, callback);
			PrintWriter pw=response.getWriter();
			pw.write(js);
			pw.close();
		} catch (Exception e) {
			logger.info("发生异常");
		}
		
		
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
		model.addAttribute(Company.PARMS_COMPANY, companyService.getCompany(id));
		CompanyCategory companyCategory = categoryService
				.getCompanyCategoryById(companyService.getCompany(id).getCategoryId());
		String uid = companyService.getCompany(id).getUid();
		Tenancy tenancy = tenancyService.findByUid(Long.valueOf(uid));
		List<CompanyPictures> pictures = companyService.getPictures(id.intValue());
		String w = companyService.getCompany(id).getDetail();
		String what = w.replaceAll("<p>", "").replaceAll("</p>", "");
		model.addAttribute("what", what);
		model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);
		model.addAttribute(Tenancy.PARMS_TENANCY, tenancy);
		model.addAttribute(CompanyCategory.PARMS_COMPANY_CATEGORY, companyCategory);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "company/detail";
	}


	/**
	 * 更新企业信息
	 * 
	 * @param company
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateCompany(@ModelAttribute("company") Company company,
			@RequestParam("fileInput_thum") List<CommonsMultipartFile> fileInputxs,
			@RequestParam("fileInput") List<CommonsMultipartFile> fileInputs,
			HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws IOException {
		//添加关联图片
		if(null!=fileInputs){
			for (CommonsMultipartFile fileInput : fileInputs) {
		        if (fileInput != null && fileInput.getOriginalFilename() != null&& !fileInput.getOriginalFilename().equals("")) {
		        	List<Photo> photos = companyService.uploadPhotos(fileInput,(MultipartHttpServletRequest)request);
			      	Long companyId = company.getId();
			      	companyService.savaCompanyPictures(imgPath+photos.get(1).getFilePath(),companyId);
		        }
	 		}
		}
		// 上传logo
		if (null != fileInputxs) {
				for (CommonsMultipartFile fileInput : fileInputxs) {
					if (fileInput != null
							&& fileInput.getOriginalFilename() != null
							&& !fileInput.getOriginalFilename().equals("")) {
						List<Photo> photos = companyService.uploadPic(fileInput,(MultipartHttpServletRequest) request);
						company.setPic(imgPath+photos.get(2).getFilePath());
					}
			}
		}
		companyService.updateCompany(company);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/index";//"redirect:/mgr/company"
	}

	/**
	 * ajax删除（非真正意义上的删除）
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @throws AppBizException
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Company deleteCompany(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		return companyService.deleteCompany(id);
	}
	
	
	
	/**
	 * ajax删除
	 * @param id
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "{id}/delpic", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePic(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		companyService.deletePicture(id);
	}

	/**
	 * 修改名字
	 * 
	 * @param name
	 * 
	 * @param id
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changeName", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeName(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "id", required = false) Long id) {
		companyService.changeName(id, name);
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Company对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getCompany(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute(Company.PARMS_COMPANY, companyService.getCompany(id));
		}
	}
	
	@RequestMapping(value="info",method = RequestMethod.GET)
	public String info(@RequestParam(value = "uid", required = true) String suid,Model model){
		Company company = companyService.findByUid(suid);
		
		model.addAttribute(Company.PARMS_COMPANY, company);
		User user = accountService.getUser(getCurrentUserId());
		
		List<CompanyCategory> categories = null;
		if(user.getRoles().equals(User.USER_ROLE_ADMIN)){
			categories = categoryService
					.getAllCompanyCategory(CompanyCategory.STATUS_VALIDE);
		}else{
			long uid =  user.getUid();
		      categories = categoryService
				.getAllCompanyCategorys(CompanyCategory.STATUS_VALIDE,uid);
		}
		model.addAttribute(CompanyCategory.PARMS_COMPANY_CATEGORYS, categories);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_UPDATE_PAGE);
		
		if(company == null){
			company = new Company();
			company.setTitle(tenancyService.findByUid(Long.valueOf(suid)).getName());
			company.setUid(suid);
			
			model.addAttribute(Company.PARMS_COMPANY,company );
			return "company/add";
		}else{
			List<CompanyPictures> pictures = companyService.getPictures(company.getId().intValue());
			model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);
			model.addAttribute(Company.PARMS_COMPANY,company );
			return "company/edit";
		}
		
	}
	
}
