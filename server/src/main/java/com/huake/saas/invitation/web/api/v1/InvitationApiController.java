package com.huake.saas.invitation.web.api.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.service.ImageUploadService;
import com.huake.saas.base.web.controller.BaseApiController;
import com.huake.saas.images.Photo;
import com.huake.saas.invitation.entity.Invitation;
import com.huake.saas.invitation.entity.InvitationReply;
import com.huake.saas.invitation.entity.InvitationResources;
import com.huake.saas.invitation.service.InvitationReplyService;
import com.huake.saas.invitation.service.InvitationService;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;
import com.huake.saas.user.service.TenancyUserService;

@Controller
@RequestMapping(value = "/api/v1/invitation")
public class InvitationApiController extends BaseApiController{


	@Autowired
	private InvitationService invitationService;

	@Autowired
	private InvitationReplyService invitationReplyService;
	
	@Autowired
	private TenancyUserService userService;
	
	@Resource(name = "bizMessageSource")
	private MessageSource messageSource;
	
	@Autowired
	private ImageUploadService imageService;
	
	@Autowired
	private TenancyService tenancyService;
	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;
	/**
	 * 帖子列表
	 * 
	 * @param first
	 * @param max
	 * @return List<Invitation>
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Invitation> getInvitations(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_ischeck",
				String.valueOf(Invitation.INVITATION_IS_CHECK));
		searchParams.put("LIKE_title", title);
		searchParams.put("EQ_status", BaseEntity.STATUS_VALIDE);
		
		List<Invitation> invitations = invitationService.getUserInvitationApi(
				uid, searchParams, pageNum, BaseEntity.DATE_MAX, BaseEntity.PAGE_UPDATE_DESC)
				.getContent();
		
		for(int i=0;i<invitations.size();i++){
			Invitation invitation = invitations.get(i);
			if(invitation.getUserId() != null ){
				invitation.setUser(userService.findById(invitation.getUserId()));
			}
			invitation.setResources(invitationService.findByInvitationId(invitation.getId()));
		}
		return invitations;
	
	}

	/**
	 * 帖子详情
	 * 
	 * @param id
	 *            (帖子Id)
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Invitation eventDetail(@PathVariable final Long id,
			@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum) {
		Invitation invitation = invitationService.getInvitation(id);
		return invitationService.savaInvitationDetatils(invitation,pageNum);
	}

	/**
	 * 发表帖子
	 * 
	 * @param id      (帖子Id)
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String, String> addInvitation(@RequestParam(value = "uid", required = true) Long uid,
			@RequestBody Invitation invitation)
			throws Exception {
		if(null==invitation.getUserId()||"".equals(invitation.getUserId())){
			throw new AppBizException("用户未登入！");
		}
		invitationService.savaInvitationByApi(invitation,uid);
		List<InvitationResources> resources = invitation.getResources();
		if(resources != null){
			for(int i=0;i<resources.size();i++){
				InvitationResources resource = resources.get(i);
				invitationService.savaInvitationResources(resource.getUrl(), invitation.getId());
			}
		}
		return SUCCESS_RESULT;
	}

	/**
	 * 帖子评论
	 * 
	 * @param id
	 *            (帖子Id)
	 * @return
	 */
	@RequestMapping(value = "/reply/{id}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String, String> addInvitationReply(@PathVariable final Long id,
			@RequestBody InvitationReply invitationReply)
			throws Exception {
		invitationReply.setInvitationId(id);
		invitationReplyService.saveInvitationReply(invitationReply);
		return SUCCESS_RESULT;
	}

	
	/**
	 * 帖子评论列表
	 * 
	 * @param id
	 *            (帖子Id)
	 * @return
	 */
	@RequestMapping(value = "/replyList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<InvitationReply> replyList(@RequestParam  Long id
			)throws Exception {
		return invitationReplyService.findInvitationReplyByinvitationId(id);
	}
	
	/**
	 * 上传
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
    @ResponseStatus(value=HttpStatus.CREATED)
	public Map<String,String> uploadImage(@RequestParam(value = "uid", required = true) Long uid,
			MultipartHttpServletRequest request, HttpServletResponse response) throws Exception{
		Tenancy tenancy = tenancyService.findByUid(uid);
		if(tenancy == null){
			 throw new AppBizException(messageSource.getMessage("member_not_found",
						new String[] {}, Locale.SIMPLIFIED_CHINESE));
		}
		List<MultipartFile> files = request.getFiles("source");
		if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your post.");
        }
		List<Photo> photos = null;
		for(MultipartFile file : files){
			/*这里只传送一个文件*/
			photos = invitationService.uploadPic((CommonsMultipartFile)file, request);
		}
		Map<String,String> result = new HashMap<String, String>();
		result.put("url", imgPath+photos.get(1).getFilePath());
		return result;
	}
}
