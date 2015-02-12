package com.huake.saas.invitation.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

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
import com.huake.saas.invitation.entity.Invitation;
import com.huake.saas.invitation.entity.InvitationReply;
import com.huake.saas.invitation.entity.InvitationResources;
import com.huake.saas.invitation.service.InvitationReplyService;
import com.huake.saas.invitation.service.InvitationService;
import com.huake.saas.news.entity.NewsPictures;

/**
 * Invitation管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /invitation/
 * Create page : GET /invitation/create
 * Create action : POST /invitation/save
 * Update page : GET /invitation/update/{id}
 * Update action : POST /invitation/update
 * Delete action : GET /invitation/delete/{id}
 * 
 * @author zhongshuhui
 */
@Controller
@RequestMapping(value = "/mgr/invitation")
public class InvitationController extends BaseController{
	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private InvitationReplyService invitationReplyService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ImageUploadService imageUploadService;
	
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put(BaseEntity.PAGE_CRTDATE_DESC, "默认(创建时间降序)");
		sortTypes.put(BaseEntity.PAGE_CRTDATE_ASC, "创建时间升序");
		sortTypes.put(BaseEntity.PAGE_UPDATE_DESC, "更新时间降序");
	}
	

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, Message.SEARCH_CONDITIONS);
		Page<Invitation> invitations = invitationService.getUserInvitation(getCurrentUID(), searchParams,
				pageNumber, pageSize, sortType);
		User user = accountService.getUser(getCurrentUserId());
		if (!User.USER_ROLE_ADMIN.equals(user.getRoles())) {
			model.addAttribute("uid", user.getUid());
		}
		/*for(Invitation invitation:invitations.getContent()){
			invitationService.savaInvitationDetatil(invitation);
		}*/
		model.addAttribute(Invitation.PARMS_INVITATIONS, invitations);
		model.addAttribute(Message.SOFT_TYPE, sortType);
		model.addAttribute(Message.SOFT_TYPES, sortTypes);
		//将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute(Message.SEARCH_PARAMS, Servlets
				.encodeParameterStringWithPrefix(searchParams,
						Message.SEARCH_CONDITIONS));
		return "invitation/index";
	}
	
	
	/**
	 * 跳入add页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		/*List<TenancyUser> forumUsers = null;
		User user = accountService.getUser(getCurrentUserId());
		if (!User.USER_ROLE_ADMIN.equals(user.getRoles())) {
			forumUsers = forumUserService.findTenancyUsersByStatusAndUid( Integer.parseInt(BaseEntity.STATUS_VALIDE),user.getUid());
		}else{
			forumUsers = forumUserService.findTenancyUsersByStatus( Integer.parseInt(BaseEntity.STATUS_VALIDE));
		}*/
		model.addAttribute(Invitation.PARMS_INVITATION, new Invitation());
		//model.addAttribute("forumUsers",forumUsers);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_CREATE_PAGE);
		return "invitation/add";
	}
	
	/**
	 * 保存帖子
	 * 
	 * @param invitation
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveInvitation(
			@ModelAttribute("invitation") Invitation invitation,
			@RequestParam(value = "fileInput", required = true) List<CommonsMultipartFile> fileInputs,
			HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws IOException {
		User user = accountService.getUser(getCurrentUserId());
		invitation.setUid(String.valueOf(user.getUid()));
		invitationService.saveInvitation(invitation);
		//添加关联图片
		if(null!=fileInputs){
			for (CommonsMultipartFile fileInput : fileInputs) {
				 if (fileInput != null && fileInput.getOriginalFilename() != null&& !fileInput.getOriginalFilename().equals("")) {
					 List<Photo> photos = invitationService.uploadPic(fileInput, (MultipartHttpServletRequest)request);
					 invitationService.savaInvitationResources(imgPath+photos.get(1).getFilePath(), invitation.getId());
				  }
			  }
	     }
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,Message.ADD_DATE_COMPLETE);
		return "redirect:/mgr/invitation";
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
		Invitation invitation = invitationService.getInvitation(id);
		/*TenancyUser user = forumUserService.findById(invitation.getUserId());
		model.addAttribute("user", user);*/
		model.addAttribute(Invitation.PARMS_INVITATION, invitation);
		List<InvitationResources> pictures = invitationService.getPictures(invitation.getId());
		model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_UPDATE_PAGE);
		return "invitation/edit";
	}
	
	/**
	 * 更新帖子信息
	 * 
	 * @param product
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateProduct(@ModelAttribute("invitation") Invitation invitation,
			@RequestParam("fileInput") List<CommonsMultipartFile> fileInputs,
			HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws IOException {
		//添加关联图片
		if(null!=fileInputs){
			for (CommonsMultipartFile fileInput : fileInputs) {
			  if (fileInput != null && fileInput.getOriginalFilename() != null&& !fileInput.getOriginalFilename().equals("")) {
				  List<Photo> photos = invitationService.uploadPic(fileInput, (MultipartHttpServletRequest)request);
					 invitationService.savaInvitationResources(imgPath+photos.get(1).getFilePath(), invitation.getId());
				}
			 }
		 }
		invitationService.updateInvitation(invitation);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/invitation";
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
		Invitation invitation = invitationService.getInvitation(id);
		model.addAttribute(Invitation.PARMS_INVITATION,invitation);
		List<InvitationResources> pictures = invitationService.getPictures(invitation.getId());
		model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);
	
		List<InvitationReply> invitationReplies = invitationReplyService.findInvitationReplyByinvitationId(invitation.getId());
		
		/*for(InvitationReply invitationReply:invitationReplies){
			String intro = invitationReply.getIntroduce();
			String introduce = intro.replaceAll("<p>", "").replaceAll("</p>", "");
			invitationReply.setIntroduce(introduce);
		}*/
		model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);
		model.addAttribute(InvitationReply.PARMS_INVITATION_REPLYS, invitationReplies);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "invitation/detail";
	}

	
	/**
	 * 修改帖子标题
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
		invitationService.changeName(id, name);
	}
	
	
	/**
	 * ajax删除（非真正意义上的删除）
	 * 
	 * @param id
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Invitation deleteInvitation(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		return invitationService.deleteInvitation(id);
	}
	
	/**
	 * ajax审核帖子
	 * 
	 * @param id
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "check/{id}", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Invitation checkInvitation(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		return invitationService.checkInvitation(id);
	}
	
	/**
	 * ajax删除图片
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "{id}/delpic", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePic(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		invitationService.deletePicture(id);
	}

	@RequestMapping(value = "showInfo")
	public String showInfo() {
		return "invitation/info";
	}
}
