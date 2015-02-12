package com.huake.saas.product.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.huake.saas.images.Photo;
import com.huake.saas.news.entity.NewsPictures;
import com.huake.saas.product.entity.Category;
import com.huake.saas.product.entity.Product;
import com.huake.saas.product.entity.ProductFields;
import com.huake.saas.product.entity.ProductPictures;
import com.huake.saas.product.service.CategoryService;
import com.huake.saas.product.service.ProductService;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;

@Controller
@RequestMapping(value = "/mgr/product")
public class ProductController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TenancyService tenancyService;

	@Autowired
	private AccountService accountService;
	
	@Autowired 
	private ImageUploadService imageUploadService;
	
	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put(BaseEntity.PAGE_CRTDATE_DESC, "默认(创建时间降序)");
		sortTypes.put(BaseEntity.PAGE_CRTDATE_ASC, "创建时间升序");
	}

	/**
	 * 主界面
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
		Page<Product> products = productService.getUserProduct(getCurrentUID(),
				searchParams, pageNumber, pageSize, sortType);
		
		List<Category> categories = categoryService.getAllCategorys(BaseEntity.STATUS_VALIDE,getCurrentUID());
		model.addAttribute("uid", getCurrentUID());
		model.addAttribute(Category.PARMS_CATEGORYS, categories);
		model.addAttribute(Product.PARMS_PRODUCTS, products);
		model.addAttribute(Message.SOFT_TYPE, sortType);
		model.addAttribute(Message.SOFT_TYPES, sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute(Message.SEARCH_PARAMS, Servlets
				.encodeParameterStringWithPrefix(searchParams,
						Message.SEARCH_CONDITIONS));
		return "product/index";
	}

	/**
	 * 跳入create界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		/*User user = accountService.getUser(getCurrentUserId());
		List<Category> categories = null;
		if(user.getRoles().equals(User.USER_ROLE_ADMIN)){
			categories = categoryService
					.getAllCategory(BaseEntity.STATUS_VALIDE);
		}else{
			long uid =  user.getUid();
		      categories = categoryService
				.getAllCategorys(BaseEntity.STATUS_VALIDE,uid);
		}*/
		List<Category> categories = categoryService.getAllCategorys(BaseEntity.STATUS_VALIDE,getCurrentUID());
		model.addAttribute(Product.PARMS_PRODUCT, new Product());
		model.addAttribute(Category.PARMS_CATEGORYS, categories);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_CREATE_PAGE);
		return "product/add";
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
			@ModelAttribute("product") Product product,
			@RequestParam("fileInput_thum") List<CommonsMultipartFile> fileInputxs,
			@RequestParam(value = "fileInput") List<CommonsMultipartFile> fileInputs,
			HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws IOException {
		User user = accountService.getUser(getCurrentUserId());
		product.setUid(String.valueOf(user.getUid()));
		product.setSales(0);
	

		/**
		 * 拓展字段保存
		 */
		String[] fieldNames = request.getParameterValues("fieldName");
		String[] fieldValues = request.getParameterValues("fieldValue");
		if (fieldNames != null && fieldValues != null) {

			if (fieldNames != null && fieldNames.length > 0) {

				if (fieldNames.length != fieldValues.length) {
					return "redirect:/manage/product/add";
				}
				List<ProductFields> fields = new ArrayList<ProductFields>();
				for (int i = 0; i < fieldNames.length; i++) {
					ProductFields field = new ProductFields();
					field.setName(fieldNames[i]);
					field.setValue(fieldValues[i]);
					fields.add(field);
				}
				product.setProductFields(fields);
			}
		}
		productService.saveProduct(product);
		
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
					&& fileInput.getOriginalFilename() != null
					&& !fileInput.getOriginalFilename().equals("")) {	
					List<Photo> photos = productService.uploadProductPic(fileInput,(MultipartHttpServletRequest) request);
					product.setThumb(imgPath+photos.get(1).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		
		if (null != fileInputs) {
			for (int i = 0; i < fileInputs.size(); i++) {
				CommonsMultipartFile fileInput = fileInputs.get(i);
				if (fileInputs.get(i) != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
					List<Photo> photos = productService.uploadProductPic(fileInput,
							(MultipartHttpServletRequest) request);
					productService.savaProductPictures(imgPath+photos.get(1).getFilePath(), product.getId());// 默认保存200*200图片的url
				} 

			}
		}
		
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.ADD_DATE_COMPLETE);
		return "redirect:/mgr/product";
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
		Product product = productService.getProduct(id);
		model.addAttribute(Product.PARMS_PRODUCT, product);
		/*User user = accountService.getUser(getCurrentUserId());
		List<Category> categories = null;
		if(user.getRoles().equals(User.USER_ROLE_ADMIN)){
			categories = categoryService
					.getAllCategory(BaseEntity.STATUS_VALIDE);
		}else{
			long uid =  user.getUid();
		      categories = categoryService
				.getAllCategorys(BaseEntity.STATUS_VALIDE,uid);
		}*/
		List<Category> categories = categoryService.getAllCategorys(BaseEntity.STATUS_VALIDE,getCurrentUID());
		List<ProductPictures> pictures = productService.getPictures(product.getId().intValue());
		model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);
		model.addAttribute(Category.PARMS_CATEGORYS, categories);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_UPDATE_PAGE);
		return "product/edit";
	}

	/**
	 * 更新产品信息
	 * 
	 * @param product
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product,
			@RequestParam("fileInput") List<CommonsMultipartFile> fileInputs,
			@RequestParam("fileInput_thum") List<CommonsMultipartFile> fileInputxs,
			HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws IOException {
		
				if (null != fileInputxs) {
					for (CommonsMultipartFile fileInput : fileInputxs) {
						if (fileInput != null
							&& fileInput.getOriginalFilename() != null
							&& !fileInput.getOriginalFilename().equals("")) {	
							List<Photo> photos = productService.uploadProductPic(fileInput,(MultipartHttpServletRequest) request);
							product.setThumb(imgPath+photos.get(1).getFilePath());// 默认保存200*200图片的url
						}
					}
				}
			
				/**
				 * 拓展字段保存
				 */
				String[] fieldNames = request.getParameterValues("fieldName");
				String[] fieldValues = request.getParameterValues("fieldValue");
				if (fieldNames != null && fieldValues != null) {

					if (fieldNames != null && fieldNames.length > 0) {

						if (fieldNames.length != fieldValues.length) {
							return "redirect:/manage/product/add";
						}
						List<ProductFields> fields = product.getProductFields();
						for (int i = 0; i < fieldNames.length; i++) {
							ProductFields field = new ProductFields();
							field.setName(fieldNames[i]);
							field.setValue(fieldValues[i]);
							field.setProductId(product.getId());;
							fields.add(field);
						}
						product.setProductFields(fields);
					}
				}
				productService.updateProduct(product);
				if (null != fileInputs) {
					for (int i = 0; i < fileInputs.size(); i++) {
						CommonsMultipartFile fileInput = fileInputs.get(i);
						if (fileInputs.get(i) != null
								&& fileInput.getOriginalFilename() != null
								&& !fileInput.getOriginalFilename().equals("")) {
							List<Photo> photos = productService.uploadProductPic(fileInput,
									(MultipartHttpServletRequest) request);
							productService.savaProductPictures(imgPath+photos.get(1).getFilePath(), product.getId());// 默认保存200*200图片的url
						} 

					}
				}
				
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/product";
	}
	
	
	/**
	 * 产品 简介，详细图片上传
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
		model.addAttribute(Product.PARMS_PRODUCT, productService.getProduct(id));
		Category category = categoryService.getCategoryById(productService
				.getProduct(id).getCategoryId());
		String uid = productService.getProduct(id).getUid();
		Tenancy tenancy = tenancyService.getTenancy(Long.valueOf(uid));
		List<ProductPictures> pictures = productService.getPictures(id.intValue());
		model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);
		model.addAttribute(Tenancy.PARMS_TENANCY, tenancy);
		model.addAttribute(Category.PARMS_CATEGORY, category);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "product/detail";
	}
	
	
	/**
	 * 删除操作 删除后返回主界面
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		productService.deleteProduct(id);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.DELETE_DATE_COMPLETE);
		return "redirect:/mgr/product/";
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
	@ResponseStatus(HttpStatus.OK)
	public Product deleteProduct(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		return productService.deleteProduct(id);
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
		productService.deletePicture(id);
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
		productService.changeName(id, name);
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Product对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getProduct(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("product", productService.getProduct(id));
		}
	}
	
	/**
	 * ajax删除  根据id删除商品扩展属性
	 * @param id
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "delfield/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteField(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		productService.deleteField(id);
	}
	
	
	@RequestMapping(value = "showInfo")
	public String showInfo() {
		return "product/info";
	}
	
}
