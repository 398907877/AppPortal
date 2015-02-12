package com.huake.saas.invitation.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.huake.saas.account.service.AccountService;
import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.service.ImageUploadService;
import com.huake.saas.base.web.controller.BaseMobileController;
import com.huake.saas.invitation.entity.Invitation;
import com.huake.saas.invitation.entity.InvitationReply;
import com.huake.saas.invitation.entity.InvitationResources;
import com.huake.saas.invitation.service.InvitationReplyService;
import com.huake.saas.invitation.service.InvitationService;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

@Controller
@RequestMapping(value = "/m/invitation")
public class InvitationMobileController extends BaseMobileController{
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private InvitationReplyService invitationReplyService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TenancyUserService tenancyUserService;
	
	@Autowired
	private ImageUploadService imageUploadService;
	

	/**
	 * 论坛首页
	 * @param pageNum
	 * @param UID
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(@RequestParam("uid")  final String UID, 
			@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum)throws AppBizException{
		ModelAndView mav = new ModelAndView();
		if(UID == null){
			throw new AppBizException("无效的访问");
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<Invitation> invitations = invitationService.getUserInvitation(Long.parseLong(UID), searchParams,
				pageNum, 3, "auto");
		for (Invitation invitation : invitations) {
			if(invitation.getUserId()!=null){
				TenancyUser user = tenancyUserService.findById(invitation.getUserId());
				invitation.setUser(user);
			}
			List<InvitationResources> resources = invitationService.getPictures(invitation.getId());
			List<InvitationReply> replys = invitationReplyService.findInvitationReplyByinvitationId(invitation.getId());
			invitation.setResources(resources);
			invitation.setReplys(replys);
		}
		int countInvs = invitationService.countInvitation(UID);
		Long uid = Long.parseLong(UID); 
		int countTus = tenancyUserService.countTenancyUser(uid);
		mav.addObject("UID", UID);
		mav.addObject("countInvs", String.valueOf(countInvs));
		mav.addObject("countTus", String.valueOf(countTus));
		mav.addObject("invitations", invitations);
		mav.setViewName("mobile/invitation/index");
		return mav;
	}
	
	/**
	 * 分页获取帖子列表。
	 * @param UID
	 * @param pageNum
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<Invitation> list(@RequestParam("UID")  final String UID, 
	@RequestParam(value = "pageNum",defaultValue="2", required = false) Integer pageNum)throws AppBizException{
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<Invitation> invitations = invitationService.getUserInvitation(Long.parseLong(UID), searchParams,
				pageNum, 3, "auto").getContent();
		for (Invitation invitation : invitations) {
			if(invitation.getUserId()!=null){
				TenancyUser user = tenancyUserService.findById(invitation.getUserId());
				invitation.setUser(user);
			}
			List<InvitationResources> resources = invitationService.getPictures(invitation.getId());
			List<InvitationReply> replys = invitationReplyService.findInvitationReplyByinvitationId(invitation.getId());
			invitation.setResources(resources);
			invitation.setReplys(replys);
		}
		return invitations;
	}
	
	/**
	 * 分页获取帖子回复列表。
	 * @param UID
	 * @param pageNum
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/replys", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<InvitationReply> Replys(@RequestParam("id")  final String id, 
	@RequestParam(value = "pageNum",defaultValue="2", required = false) Integer pageNum)throws AppBizException{
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_invitationId", id.toString());
		String status = 1+"";
		searchParams.put("EQ_status", status.toString());
		List<InvitationReply> replys = invitationReplyService.getUserInvitationReply( searchParams,
				pageNum, 3, "auto").getContent();
		return replys;
	}
	
	/**
	 * 跳入回复页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}/create", method = RequestMethod.GET)
	public ModelAndView createForm(@RequestParam("uid")  final String uid,
			@PathVariable("id")  final String id,
			HttpServletRequest request,HttpServletResponse response) throws AppBizException{
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		if(session.getAttribute("login")==null||session.getAttribute("login")=="false"){
			String url = request.getRequestURI();
			String queryString = request.getQueryString();
			return new ModelAndView("redirect:/m/login?uid="+uid+"&url="+url+"?"+queryString);
		}else{
			if(session.getAttribute("login").equals("true")){
				String userId =session.getAttribute("memberId")+"";
				InvitationReply inReply = new InvitationReply();
				if(userId!=null&userId!=""){
					Long userId1 = Long.parseLong(userId);
					inReply.setUserId(userId1);
				}
				mav.addObject("invitationReply", inReply);
				mav.addObject("invitationId", id);
				mav.setViewName("mobile/invitation/reply");
			}
		}
		return mav;
	}
	
	/**
	 * 保存回复内容
	 * 
	 * @param product
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(
			@ModelAttribute("invitationReply") InvitationReply invitationReply)throws IOException {
		Long userId = invitationReply.getUserId();
		if(userId!=null){
			TenancyUser user = tenancyUserService.findById(userId);
			invitationReply.setCrUser(user.getName());
		}else{
			invitationReply.setCrUser("游客");
		}
		Invitation invitation = invitationService.getInvitation(invitationReply.getInvitationId());
		String UID = invitation.getUid()+"";
		invitationReplyService.saveInvitationReply(invitationReply);
		return "redirect:/m/invitation?uid="+UID;
	}
	
	/**
	 * 跳入发话题页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addForm(@RequestParam("uid")  final String uid,
			HttpServletRequest request,HttpServletResponse response) throws AppBizException{
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		System.out.println(uid);
		if(session.getAttribute("login")==null||session.getAttribute("login")=="false"){
			String url = request.getRequestURI();
			String queryString = request.getQueryString();
			return new ModelAndView("redirect:/m/login?uid="+uid+"&url="+url+"?"+queryString);
		}else{
			Invitation in = new Invitation();
			Long userId1 = Long.valueOf(session.getAttribute("memberId")+"");
			in.setUserId(userId1);
			mav.addObject("invitation", in);
			mav.addObject("UID", uid);
			mav.setViewName("mobile/invitation/add");
		}
		
		return mav;
	}
	
	/**
	 * 保存话题内容
	 * 
	 * @param product
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveInv", method = RequestMethod.POST)
	public String save(@ModelAttribute("invitation") Invitation invitation,
			@RequestParam(value = "fileInput", required = true) List<CommonsMultipartFile> fileInputs)throws IOException {
		Long userId = invitation.getUserId();
		String UID = invitation.getUid();
		TenancyUser users = tenancyUserService.findById(userId);
		invitation.setCrUser(users.getName());
		System.out.println("-------------------------------"+users.getName());
		invitationService.saveInvitations(invitation);
		//添加关联图片
		if(null!=fileInputs){
			for (CommonsMultipartFile fileInput : fileInputs) {
				 if (fileInput != null && fileInput.getOriginalFilename() != null&& !fileInput.getOriginalFilename().equals("")) {
					 Map<String, String> result = imageUploadService.uploadInvitationImage(fileInput.getInputStream(),
					 fileInput.getOriginalFilename());
					 invitationService.savaInvitationResources(result.get(ImageUploadService.IMAGE_URL), invitation.getId());
				  }
			  }
		  }
		return "redirect:/m/invitation?uid="+UID;
	}
}
