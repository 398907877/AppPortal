package com.huake.saas.invitation.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

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
import com.huake.saas.base.service.ImageUploadService;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.invitation.entity.Invitation;
import com.huake.saas.invitation.entity.InvitationReply;
import com.huake.saas.invitation.entity.InvitationReplyResources;
import com.huake.saas.invitation.service.InvitationReplyService;
import com.huake.saas.invitation.service.InvitationService;
import com.huake.saas.news.entity.NewsPictures;
/**
 * InvitationReply管理的Controller, 使用Restful风格的Urls:
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
@RequestMapping(value = "/mgr/invitation/reply")
public class InvitationReplyController extends BaseController{
	
	@Autowired
	private InvitationReplyService invitationReplyService;
	
	@Autowired
	private InvitationService invitationService;
	
	/*@Autowired
	private AccountService accountService;*/
	
	/*@Autowired
	private TenancyUserService forumUserService;*/
	
	@Autowired
	private ImageUploadService imageUploadService;
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put(BaseEntity.PAGE_CRTDATE_DESC, "默认(创建时间降序)");
		sortTypes.put(BaseEntity.PAGE_CRTDATE_ASC, "创建时间升序");
	}
	

	@RequestMapping(value = "/{id}/list", method = RequestMethod.GET)
	public String list(@PathVariable("id") Long id,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, Message.SEARCH_CONDITIONS);
		Invitation invitation = invitationService.getInvitation(id);
		String invitationId = Long.toString(id);
		searchParams.put("EQ_invitationId", invitationId);
		Page<InvitationReply> invitationReplys = invitationReplyService.getUserInvitationReply( searchParams,
				pageNumber, pageSize, sortType);
		model.addAttribute(Invitation.PARMS_INVITATION, invitation);
		model.addAttribute(InvitationReply.PARMS_INVITATION_REPLYS, invitationReplys);
		model.addAttribute(Message.SOFT_TYPE, sortType);
		model.addAttribute(Message.SOFT_TYPES, sortTypes);
		//将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute(Message.SEARCH_PARAMS, Servlets
				.encodeParameterStringWithPrefix(searchParams,
						Message.SEARCH_CONDITIONS));
		return "invitation/reply";
	}
	
	/**
	 * 跳入add页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}/create", method = RequestMethod.GET)
	public String createForm(@PathVariable("id") Long id,Model model) {
		/*List<TenancyUser> forumUsers = null;*/
	/*	User user = accountService.getUser(getCurrentUserId());*/
		/*if (!User.USER_ROLE_ADMIN.equals(user.getRoles())) {
			forumUsers = forumUserService.findTenancyUsersByStatusAndUid( Integer.parseInt(BaseEntity.STATUS_VALIDE),user.getUid());
		}else{
			forumUsers = forumUserService.findTenancyUsersByStatus( Integer.parseInt(BaseEntity.STATUS_VALIDE));
		} 
		model.addAttribute("forumUsers",forumUsers);*/
		model.addAttribute(Invitation.PARMS_INVITATION,invitationService.getInvitation(id));
		InvitationReply inReply = new InvitationReply();
		inReply.setCrUser("版主");
		model.addAttribute(InvitationReply.PARMS_INVITATION_REPLY, inReply);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_CREATE_PAGE);
		return "invitation/reply_add";
	}
	
	/**
	 * 保存评论
	 * 
	 * @param product
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(
			@ModelAttribute("invitationReply") InvitationReply invitationReply,
			/*@RequestParam(value = "fileInput", required = true) List<CommonsMultipartFile> fileInputs,
			HttpServletRequest request,*/ RedirectAttributes redirectAttributes)
			throws IOException {
		/*// TODU   
		if(null!=invitationReply.getUserId()){
		   TenancyUser fuser = forumUserService.findById(invitationReply.getUserId());
		   invitationReply.setCrUser(fuser.getName());
		}*/
		invitationReplyService.saveInvitationReply(invitationReply);
		//添加评论关联图片
		/*if(null!=fileInputs){
			for (CommonsMultipartFile fileInput : fileInputs) {
				 if (fileInput != null && fileInput.getOriginalFilename() != null&& !fileInput.getOriginalFilename().equals("")) {
					 Map<String, String> result = imageUploadService.uploadInvitationImage(fileInput.getInputStream(),
					 fileInput.getOriginalFilename());
					 invitationReplyService.savaInvitationReplyResources(result.get(ImageUploadService.IMAGE_URL), invitationReply.getId());
				  }
			  }
	     }*/
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,Message.ADD_DATE_COMPLETE);
		return "redirect:/mgr/invitation/reply/"+invitationReply.getInvitationId()+"/list";
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
		InvitationReply invitationReply = invitationReplyService.getInvitationReply(id);
		model.addAttribute(InvitationReply.PARMS_INVITATION_REPLY, invitationReply);
		/*List<InvitationReplyResources> pictures = invitationReplyService.getPictures(invitationReply.getId());*/
		/*model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);*/
		model.addAttribute(Invitation.PARMS_INVITATION,invitationService.getInvitation(invitationReply.getInvitationId()));
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_UPDATE_PAGE);
		return "invitation/reply_edit";
	}
	
	
	/**
	 * 更新帖子信息
	 * @param product
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateProduct(@ModelAttribute("invitationReply") InvitationReply invitationReply,
			/*@RequestParam("fileInput") List<CommonsMultipartFile> fileInputs,
			HttpServletRequest request,*/ RedirectAttributes redirectAttributes)
			throws IOException {
		//添加关联图片
		/*if(null!=fileInputs){
			for (CommonsMultipartFile fileInput : fileInputs) {
			  if (fileInput != null && fileInput.getOriginalFilename() != null&& !fileInput.getOriginalFilename().equals("")) {
				Map<String, String> result = imageUploadService.uploadInvitationImage(fileInput.getInputStream(),
				fileInput.getOriginalFilename());
				 invitationReplyService.savaInvitationReplyResources(result.get(ImageUploadService.IMAGE_URL),invitationReply.getId());
				}
			 }
		 }*/
		invitationReplyService.updateInvitationReply(invitationReply);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/invitation/reply/"+invitationReply.getInvitationId()+"/list";
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
		InvitationReply invitationReply = invitationReplyService.getInvitationReply(id);
		Invitation invitation = invitationService.getInvitation(invitationReply.getInvitationId());
		List<InvitationReplyResources> pictures = invitationReplyService.getPictures(invitation.getId());
		model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);
		String intro = invitationReply.getIntroduce();
		String introduce = intro.replaceAll("<p>", "").replaceAll("</p>", "");
		invitationReply.setIntroduce(introduce);
		model.addAttribute(Invitation.PARMS_INVITATION,invitation);
		model.addAttribute(InvitationReply.PARMS_INVITATION_REPLY,invitationReply);
		model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "invitation/reply_detail";
	}

	
	/**
	 * 修改评论内容
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
		invitationReplyService.changeName(id, name);
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
	public void deleteInvitation(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		invitationReplyService.deleteInvitationReply(id);
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
		invitationReplyService.deletePicture(id);
	}
	
	
}
