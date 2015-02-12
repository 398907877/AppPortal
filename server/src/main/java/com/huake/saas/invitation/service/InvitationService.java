package com.huake.saas.invitation.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.huake.saas.account.service.AccountService;
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Compress;
import com.huake.saas.base.service.ServiceException;
import com.huake.saas.images.ImagesUploadService;
import com.huake.saas.images.Photo;
import com.huake.saas.invitation.entity.Invitation;
import com.huake.saas.invitation.entity.InvitationReply;
import com.huake.saas.invitation.entity.InvitationResources;
import com.huake.saas.invitation.respository.InvitationDao;
import com.huake.saas.invitation.respository.InvitationResourcesDao;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

//Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class InvitationService {
	
	public static final String IMAGE_INVITATION_PIC_DIR = "invitation";
	@Autowired
	private InvitationDao invitationDao;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TenancyUserService forumUserService;

	@Autowired
	private InvitationReplyService invitationReplyService;

	@Autowired
	private InvitationResourcesDao invitationResourcesDao;
	
	@Autowired
	private ImagesUploadService uploadService;

	public Invitation getInvitation(Long id) {
		return invitationDao.findOne(id);
	}

	/**
	 * 后台添加的帖子
	 * @param entity
	 */
	public void saveInvitation(Invitation entity) {
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		entity.setCrtDate(new Date());
		entity.setIscheck(Invitation.INVITATION_IS_CHECK);
		entity.setUpTime(new Date());
		entity.setReplyNum(0);
		entity.setCrUser("版主");
		invitationDao.save(entity);
	}
	
	/**
	 * 网页添加的帖子
	 * @param entity
	 */
	public void saveInvitations(Invitation entity) {
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		entity.setCrtDate(new Date());
		entity.setIscheck(Invitation.INVITATION_IS_CHECK);
		entity.setUpTime(new Date());
		entity.setReplyNum(0);
		invitationDao.save(entity);
	}
	
	/**
	 * 普通用户在客户端添加的帖子
	 * @param entity
	 */
	public void savaInvitationByApi(Invitation entity,Long uid){
		entity.setUid(String.valueOf(uid));
		if(null!=entity.getUserId()&&!"".equals(entity.getUserId())){
			entity.setUserId(entity.getUserId());
		}
		
		if(null!=entity && null!=entity.getUserId()&& !"".equals(entity.getUserId())){
			TenancyUser user = forumUserService.findById(entity.getUserId());
			entity.setCrUser(user.getName() == null ? user.getLoginname() : user.getName() );
		}else{
			entity.setCrUser("游客");
		}
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		entity.setCrtDate(new Date());
		entity.setIscheck(Invitation.INVITATION_IS_CHECK);
		entity.setUpTime(new Date());
		invitationDao.save(entity);
	}
	

	public void updateInvitation(Invitation entity) {
		Invitation oldInvitation = getInvitation(entity.getId());
		entity.setUpTime(new Date());
		entity.setCrtDate(oldInvitation.getCrtDate());
		entity.setStatus(oldInvitation.getStatus());
		//entity.setCrUser(oldInvitation.getCrUser());
		entity.setIscheck(oldInvitation.getIscheck());
		entity.setReplyNum(oldInvitation.getReplyNum());
		entity.setUid(oldInvitation.getUid());
		invitationDao.save(entity);
	}

	public Invitation deleteInvitation(Long id) {
		Invitation invitation = getInvitation(id);
		invitation.setStatus(BaseEntity.STATUS_INVALIDE);
		invitationDao.save(invitation);
		return invitation;

	}

	public List<Invitation> getAllInvitation() {
		return (List<Invitation>) invitationDao.findAll();
	}

	/**
	 * 同一个uid的帖子数
	 * @param uid
	 * @return
	 */
	public int countInvitation(String uid){
		return invitationDao.count(uid,BaseEntity.STATUS_VALIDE);
	}
	
	/**
	 * 后台列表查询
	 * 
	 * @param uid
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Invitation> getUserInvitation(Long uid,
			Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Specification<Invitation> spec = buildSpecification(uid,
				searchParams);
		return invitationDao.findAll(spec, pageRequest);
	}

	/**
	 * API查询列表
	 * 
	 * @param uid
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Invitation> getUserInvitationApi(Long uid,
			Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Specification<Invitation> spec = buildSpecificationApi(uid,
				searchParams);
		return invitationDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Invitation> buildSpecificationApi(Long uid,
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status",
				new SearchFilter("status", Operator.EQ, BaseEntity.STATUS_VALIDE));
		return DynamicSpecifications.bySearchFilter(filters.values(),
				Invitation.class);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("crtDate".equals(sortType)) {
			sort = new Sort(Direction.ASC, "crtDate");
		}else if(BaseEntity.PAGE_UPDATE_DESC.equals(sortType)){	
			sort = new Sort(Direction.DESC, BaseEntity.PAGE_UPDATE_DESC);
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Invitation> buildSpecification(Long uid,
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		/*if (userId != null && !"".equals(userId)) {
			User user = accountService.getUser(userId);
			if (!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())) {
				filters.put("uid",
						new SearchFilter("uid", Operator.EQ, user.getUid()));
			}
		}*/
		filters.put("uid",
				new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status",
				new SearchFilter("status", Operator.EQ, BaseEntity.STATUS_VALIDE));
		return DynamicSpecifications.bySearchFilter(filters.values(),
				Invitation.class);
	}

	/**
	 * 帖子列表详情
	 * @param invitation
	 * @return
	 */
	public Invitation savaInvitationDetatil(Invitation invitation) {
		if(null!=invitation&&null!=invitation.getUserId()&&!"".equals(invitation.getUserId())){
			TenancyUser user = forumUserService.findById(invitation.getUserId());
			invitation.setCrUser(user.getName());
		}else{
			invitation.setCrUser("游客");
		}
		invitation.setReplyNum(invitation.getReplyNum());
		return invitation;
	}
	

	/**
	 * 帖子详情
	 * @param invitation
	 * @return
	 */
	public Invitation savaInvitationDetatils(Invitation invitation,int replyNum) {
		if(null!=invitation){
			if(invitation.getUserId() != null){
				TenancyUser user = forumUserService.findById(invitation.getUserId());
				invitation.setUser(user);
			}
		}
		invitation.setResources(invitationResourcesDao
				.findInvitationResourcesByInvitationId(invitation.getId()));
		List<InvitationReply> reply = invitationReplyService
				.findInvitationReplyByinvitationId(invitation.getId());
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_invitationId",invitation.getId().toString());
		List<InvitationReply> invitationReplys = invitationReplyService.getUserInvitationReply(
				 searchParams, replyNum, BaseEntity.DATE_MAX, "crtDate")
				.getContent();
		invitation.setReplys(ReplyConvert(invitationReplys));
		if(null==reply||reply.size()<1){
			invitation.setReplyNum(0);
		}else{
			invitation.setReplyNum(reply.size());
		}
		return invitation;
	}
	
	/**
	 * 回复实体类转换成自定义回复实体类  方便客户端接收数据
	 * @param invitationReply
	 * @return
	 */
	public List<InvitationReply> ReplyConvert(List<InvitationReply> invitationReply){
		for(InvitationReply reply:invitationReply){
		if(reply.getUserId()!=null&&!"".equals(reply.getUserId())){
			TenancyUser fuser = forumUserService.findById(reply.getUserId());
			reply.setUser(fuser);
		}
		}
		return invitationReply;
		
	}
	

	/**
	 * 修改名字
	 * 
	 * @param id
	 * @param title
	 */
	public void changeName(Long id, String title) {
		Invitation invitation = invitationDao.findOne(id);
		invitation.setTitle(title);
		invitation.setUpTime(new Date());
		invitationDao.save(invitation);
	}

	/**
	 * 保存图片集
	 * 
	 * @param url
	 * @param invitationId
	 */
	public void savaInvitationResources(String url, Long invitationId) {
		InvitationResources pic = new InvitationResources();
		pic.setCrtDate(new Date());
		pic.setInvitationId(invitationId.intValue());
		pic.setUrl(url);
		invitationResourcesDao.save(pic);
	}

	/**
	 * 查询帖子关联图片
	 * 
	 * @param newsid
	 * @return
	 */
	public List<InvitationResources> getPictures(Long invitationId) {
		return invitationResourcesDao
				.findInvitationResourcesByInvitationId(invitationId);
	}

	/**
	 * 删除图片
	 * 
	 * @param id
	 */
	public void deletePicture(long id) {
		invitationResourcesDao.delete(id);
	}

	/**
	 * 审核帖子
	 * 
	 * @param id
	 * @return
	 */
	public Invitation checkInvitation(Long id) {
		Invitation invitation = invitationDao.findOne(id);
		invitation.setIscheck(Invitation.INVITATION_IS_CHECK);
		invitation.setUpTime(new Date());
		invitationDao.save(invitation);
		return invitation;
	}
	/**
	 * 根据帖子id查找帖子资源 图片 视频等
	 * @return
	 */
	public List<InvitationResources> findByInvitationId(Long invitationId){
		return invitationResourcesDao.findInvitationResourcesByInvitationId(invitationId);
	}
	
	/**
	 * 上传图片
	 * @param file
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<Photo> uploadPic(CommonsMultipartFile file, final MultipartHttpServletRequest request) throws ServiceException{
		if (ServletFileUpload.isMultipartContent(request)) {
			Compress smallCompress = new Compress(300, 150, 1, Constants.PHOTO_SMALL);
			List<Photo> photos = uploadService.saveImage(file,IMAGE_INVITATION_PIC_DIR ,smallCompress);
			return photos;
		}
		return null;
	}
}
