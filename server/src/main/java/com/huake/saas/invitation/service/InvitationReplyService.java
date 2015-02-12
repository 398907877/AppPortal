package com.huake.saas.invitation.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.huake.saas.account.service.AccountService;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.invitation.entity.Invitation;
import com.huake.saas.invitation.entity.InvitationReply;
import com.huake.saas.invitation.entity.InvitationReplyResources;
import com.huake.saas.invitation.respository.InvitationDao;
import com.huake.saas.invitation.respository.InvitationReplyDao;
import com.huake.saas.invitation.respository.InvitationReplyResourcesDao;

//Spring Bean的标识.
@Component
//类中所有public函数都纳入事务管理的标识.
@Transactional
public class InvitationReplyService {
	
	@Autowired
    private InvitationReplyDao invitationReplyDao;
    @Autowired
    private InvitationDao invitationDao;



	@Autowired
	private AccountService accountService;
	
	@Autowired
	private InvitationService invitationService;
	@Autowired
	private InvitationReplyResourcesDao invitationReplyResourcesDao;
	

	public InvitationReply getInvitationReply(Long id) {
		return invitationReplyDao.findOne(id);
	}
	
	public void saveInvitationReply(InvitationReply entity) {
		Invitation invitation = invitationService.getInvitation(entity.getInvitationId());
		invitation.setUpTime(new Date());
		invitation.setReplyNum(invitation.getReplyNum()+1);
		invitationDao.save(invitation);
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		/*if(null==entity.getCrUser()||"".equals(entity.getCrUser())){
			TenancyUser user = forumUserService.findById(entity.getUserId());
			entity.setCrUser(user.getName() == null ? user.getLoginname() :user.getName());
		}*/
		entity.setCrtDate(new Date());
		invitationReplyDao.save(entity);
	}
	
	public void updateInvitationReply(InvitationReply entity){
		InvitationReply oldInvitation = getInvitationReply(entity.getId());
		entity.setCrtDate(oldInvitation.getCrtDate());
		entity.setStatus(oldInvitation.getStatus());
		invitationReplyDao.save(entity);
	}

	public void deleteInvitationReply(Long id) {
		InvitationReply invitationReply = getInvitationReply(id);
		Invitation invitation = invitationDao.findOne(invitationReply.getInvitationId());
		invitation.setReplyNum(invitation.getReplyNum()-1);
		invitationReply.setStatus(BaseEntity.STATUS_INVALIDE);
		invitationReplyDao.save(invitationReply);
	}

	public List<InvitationReply> getAllInvitationReply() {
		return  (List<InvitationReply>) invitationReplyDao.findAll();
	}
	
	public List<InvitationReply> findInvitationReplyByinvitationId(Long invitationId) {
		return  invitationReplyDao.findInvitationReplyByInvitationIdAndStatus(invitationId,BaseEntity.STATUS_VALIDE);
	}

	public Page<InvitationReply> getUserInvitationReply(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<InvitationReply> spec = buildSpecification( searchParams);
		return invitationReplyDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("crtDate".equals(sortType)) {
			sort = new Sort(Direction.DESC, "crtDate");
		}else if("DcrtDate".equals(sortType)){
			sort = new Sort(Direction.DESC, "crtDate");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<InvitationReply> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("status",
				new SearchFilter("status", Operator.EQ, BaseEntity.STATUS_VALIDE));
		return DynamicSpecifications.bySearchFilter(filters.values(), InvitationReply.class);
	}
	
	/**
	 * 修改名字
	 * @param id
	 * @param title
	 */
	public void changeName(Long id,String title){
		InvitationReply invitation = invitationReplyDao.findOne(id);
		invitation.setIntroduce(title);
		invitationReplyDao.save(invitation);
	}
	
	  /**
	   * 保存图片集
	   * @param url
	   * @param invitationId
	   */
	  public void savaInvitationReplyResources(String url,Long invitationReplyId){
		  InvitationReplyResources pic = new InvitationReplyResources();
		  pic.setCrtDate(new Date());
		  pic.setInvitationReplyId(invitationReplyId.intValue());
		  pic.setUrl(url);
		  invitationReplyResourcesDao.save(pic);
	  }
	  
	  /**
	   * 查询帖子关联图片
	   * @param newsid
	   * @return
	   */
	  public List<InvitationReplyResources> getPictures(Long invitationReplyId){
		return invitationReplyResourcesDao.findInvitationReplyResourcesByInvitationReplyId(invitationReplyId);
	  }
	  
	  /**
	   * 删除图片
	   * @param id
	   */
	  public void deletePicture(long id){
		  invitationReplyResourcesDao.delete(id);
	  }

}
