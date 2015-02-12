package com.huake.saas.membercard.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

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
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.images.Photo;
import com.huake.saas.membercard.entity.CardRegister;
import com.huake.saas.membercard.entity.Mcard;
import com.huake.saas.membercard.service.CardRegisterService;
import com.huake.saas.membercard.service.McardService;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;

/**
 * member card web controller
 * @author chen weirong
 *
 */
@Controller
@RequestMapping(value = "/mgr/membercard")
public class McardController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(McardController.class);
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put(BaseEntity.PAGE_CRTDATE_DESC, "默认(创建时间降序)");
		sortTypes.put(BaseEntity.PAGE_CRTDATE_ASC, "创建时间升序");
	}

	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, "startDate", new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
		binder.registerCustomEditor(Date.class, "expDate", new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
	}	
	
	@Autowired
	private McardService mcardService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CardRegisterService cardRegisterService;
	
	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;
	
	@RequestMapping(value = "/cardadd", method =RequestMethod.GET)
	public String cardAdd(Model model)
	{
		Long uid = getCurrentUID();
		model.addAttribute("tenancy", tenancyService.getTenancy(uid));
		return "membercard/cardadd";
	}
	
	
	
	@RequestMapping(value = "/info", method =RequestMethod.GET)
	public String indexMemberCard(Model model)
	{
		Long uid = getCurrentUID();
		model.addAttribute("tenancy", tenancyService.getTenancy(uid));
		return "membercard/info";
	}
	
	// search and list 
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType,
			Model model, ServletRequest request){
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, Message.SEARCH_CONDITIONS);
		Page<Mcard> mcards = mcardService.getAllMemberCards(getCurrentUID(), searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("count",mcards.getContent().size());
		model.addAttribute(Mcard.PARMS_MEMBER_CARDS, mcards);
		model.addAttribute(Message.SOFT_TYPE, sortType);
		model.addAttribute(Message.SOFT_TYPES, sortTypes);
		model.addAttribute(Message.SEARCH_PARAMS, Servlets.encodeParameterStringWithPrefix(searchParams,Message.SEARCH_CONDITIONS));
		return "membercard/index";
	}
	
	
	//create new member card
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		User user = accountService.getUser(getCurrentUserId());
		if (!User.USER_ROLE_ADMIN.equals(user.getRoles())) {
			model.addAttribute("uid", user.getUid());
		}
		model.addAttribute(Mcard.PARMS_MEMBER_CARD, new Mcard());
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_CREATE_PAGE);
		return "membercard/add";
	}
	
	//save the model attribute
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveMemberCard(
			@ModelAttribute(Mcard.PARMS_MEMBER_CARD) Mcard mcard,
			@RequestParam("fileInput_thum") List<CommonsMultipartFile> fileInputlogo,
			@RequestParam("fileInput_background") List<CommonsMultipartFile> fileInputback,
			@RequestParam("fileInput_model") List<CommonsMultipartFile> fileInputmodel,
			HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws IOException {
		logger.debug("member card save.....");
		User user = accountService.getUser(getCurrentUserId());
		mcard.setUid(String.valueOf(user.getUid()));
		
		// upload the logo thumbnail
		if (null != fileInputlogo) {
			for (CommonsMultipartFile fileInput : fileInputlogo) {
				if (fileInput != null
					&& fileInput.getOriginalFilename() != null
					&& !fileInput.getOriginalFilename().equals("")) {	
					List<Photo> photos = mcardService.uploadMcardPic(fileInput,(MultipartHttpServletRequest) request);
					mcard.setLogo(imgPath+photos.get(1).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		
		//upload the background
		if (null != fileInputback) {
			for (CommonsMultipartFile fileInput : fileInputback) {
				if (fileInput != null
					&& fileInput.getOriginalFilename() != null
					&& !fileInput.getOriginalFilename().equals("")) {	
					List<Photo> photos = mcardService.uploadMcardPic(fileInput,(MultipartHttpServletRequest) request);
					mcard.setBackground(imgPath+photos.get(1).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		//upload the card model
		if (null != fileInputmodel) {
			for (CommonsMultipartFile fileInput : fileInputmodel) {
				if (fileInput != null
					&& fileInput.getOriginalFilename() != null
					&& !fileInput.getOriginalFilename().equals("")) {	
					List<Photo> photos = mcardService.uploadMcardPic(fileInput,(MultipartHttpServletRequest) request);
					mcard.setCardModel(imgPath+photos.get(1).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		mcardService.saveMcard(mcard);
		
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.ADD_DATE_COMPLETE);
		return "redirect:/mgr/membercard/index";
	}	
	
	@ModelAttribute
	public void getMcard(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("mcard", mcardService.findMcard(id));
		}
	}
	/*
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		Mcard mcard = mcardService.findMcard(id);
		List<CardRegister> cardRegisters=cardRegisterService.getCardRegistersByCardId(id);
		logger.debug("cardId is:>>>>>>:"+mcard.getId()+">>>>>"+cardRegisters.size());
		model.addAttribute(Mcard.PARMS_MEMBER_CARD, mcard);
		model.addAttribute("count",cardRegisters.size());
		//User user = accountService.getUser(getCurrentUserId());
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_UPDATE_PAGE);
		return "membercard/edit";
	}	
	
	/**
	 * update the member card information
	 * 
	 * @param product
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateMemberCard(@ModelAttribute("mcard") Mcard mcard,
			@RequestParam("fileInput_thum") List<CommonsMultipartFile> fileInputlogo,
			@RequestParam("fileInput_background") List<CommonsMultipartFile> fileInputback,
			@RequestParam("fileInput_model") List<CommonsMultipartFile> fileInputmodel,
			HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws IOException {
		/*//添加关联图片
		if(null!=fileInputs){
			for (CommonsMultipartFile fileInput : fileInputs) {
			  if (fileInput != null && fileInput.getOriginalFilename() != null&& !fileInput.getOriginalFilename().equals("")) {
				Map<String, String> result = imageUploadService.uploadCompanyImage(fileInput,
				fileInput.getOriginalFilename());
				Long productId = product.getId();
				productService.savaProductPictures(result.get(ImageUploadService.IMAGE_URL),productId);
							      
				}
			 }
		 }*/
		//mcardService.updateMcard(mcard);
		// 上传缩略图
				if (null != fileInputlogo) {
					for (CommonsMultipartFile fileInput : fileInputlogo) {
						if (fileInput != null
							&& fileInput.getOriginalFilename() != null
							&& !fileInput.getOriginalFilename().equals("")) {	
							List<Photo> photos = mcardService.uploadMcardPic(fileInput,(MultipartHttpServletRequest) request);
							mcard.setLogo(imgPath+photos.get(1).getFilePath());// 默认保存200*200图片的url
						}
					}
				}

				//upload the background
				if (null != fileInputback) {
					for (CommonsMultipartFile fileInput : fileInputback) {
						if (fileInput != null
							&& fileInput.getOriginalFilename() != null
							&& !fileInput.getOriginalFilename().equals("")) {	
							List<Photo> photos = mcardService.uploadMcardPic(fileInput,(MultipartHttpServletRequest) request);
							mcard.setBackground(imgPath+photos.get(1).getFilePath());// 默认保存200*200图片的url
						}
					}
				}
				//upload the card model
				if (null != fileInputmodel) {
					for (CommonsMultipartFile fileInput : fileInputmodel) {
						if (fileInput != null
							&& fileInput.getOriginalFilename() != null
							&& !fileInput.getOriginalFilename().equals("")) {	
							List<Photo> photos = mcardService.uploadMcardPic(fileInput,(MultipartHttpServletRequest) request);
							mcard.setCardModel(imgPath+photos.get(1).getFilePath());// 默认保存200*200图片的url
						}
					}
				}
				
				mcardService.updateMcard(mcard);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/membercard/index";
	}	
	
	/*
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detial(@PathVariable("id") Long id, Model model) {
		model.addAttribute(Mcard.PARMS_MEMBER_CARD, mcardService.findMcard(id));
		String uid = mcardService.findMcard(id).getUid();
		Tenancy tenancy = tenancyService.getTenancy(Long.valueOf(uid));
		model.addAttribute(Tenancy.PARMS_TENANCY, tenancy);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "membercard/detail";
	}	
	
	@RequestMapping(value = "/showcard/{id}", method = RequestMethod.GET)
	public String showPage(@PathVariable("id") Long id, Model model) {
		model.addAttribute(Mcard.PARMS_MEMBER_CARD, mcardService.findMcard(id));
		String uid = mcardService.findMcard(id).getUid();
		Tenancy tenancy = tenancyService.getTenancy(Long.valueOf(uid));
		model.addAttribute(Tenancy.PARMS_TENANCY, tenancy);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "membercard/showcard";
	}	
	
	@RequestMapping(value = "/mycard", method = RequestMethod.GET)
	public String myCard(Model model) {
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "membercard/mycard";
	}		
	
	/*
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value ="/delete/{id}")
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		mcardService.deleteMcard(id);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.DELETE_DATE_COMPLETE);
		return "redirect:/mgr/memebercard/index";
	}	
	
	/**
	 * delete card
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value ="/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Mcard deleteMcard(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		return mcardService.deleteMcard(id);
	}
	
	@RequestMapping(value ="{id}/delpic", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePic(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		mcardService.deletePicture(id);
	}
}
