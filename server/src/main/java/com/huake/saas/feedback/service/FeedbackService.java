package com.huake.saas.feedback.service;

import java.util.ArrayList;
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

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.feedback.entity.Feedback;
import com.huake.saas.feedback.respository.FeedbackDao;


@Component
@Transactional
public class FeedbackService {
	@Autowired
	private FeedbackDao feedbackDao;
	
	public Feedback findById(Long msgId){
		return feedbackDao.findOne(msgId);
	}
	
	/**
	 * 保存消息
	 * @param mess
	 */
	public boolean save(Feedback feedback){
		boolean flag=false;
		feedback.setCrtDate(new Date());
		feedback.setUpDate(new Date());
		feedback.setStatus(BaseEntity.STATUS_VALIDE);
		feedbackDao.save(feedback);
		flag=true;
		return flag;
	}
	
	/**
	 * 修改信息
	 * @param mess
	 */
	public void update(Feedback feedback){
		Feedback fb =feedbackDao.findOne(feedback.getId());
		fb.setUpDate(new Date());
		feedbackDao.save(fb);
	}
	
	/**
	 * 阅读消息
	 * @param mess
	 */
	public void readMessage(Long id){
		Feedback fb = feedbackDao.findOne(id);
		fb.setUpDate(new Date());
		fb.setStatus(Feedback.STATUS_READ);
		feedbackDao.save(fb);
	}
	
	/**
	 * 真删除
	 * @param id
	 */
	public void delete(Long id){
		feedbackDao.delete(id);
	}
	
	/**
	 * 假删除
	 * @param id
	 */
	public void delMessage(Long id){
		Feedback fb = feedbackDao.findOne(id);
		fb.setUpDate(new Date());
		fb.setStatus(BaseEntity.STATUS_INVALIDE);
		feedbackDao.save(fb);
	}
	
	/**
	 * 查询用户反馈信息
	 * @param memberId
	 * @return
	 */
	public List<Feedback> findByFeedBackMemberId(Long memberId){
		List<Feedback> mess=new ArrayList<Feedback>();
		List<Feedback> fbs= feedbackDao.findByMemberId(memberId);
		for(Feedback fb:fbs){
			if(Feedback.CATEGORY_CLIENT_CALLBACK.equals(fb.getCategory())||
					Feedback.CATEGORY_SERVICE_REPLY.equals(fb.getCategory())){
				mess.add(fb);
			}
		}
		return mess;
	}
	
	/**
	 * 分页查询反馈信息(客户端）
	 * @param memberId
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public List<Feedback> getFeeBacks(Long memberId, String category1,String category2,int pageNumber, int pageSize,
			String sortType) {
		 List<Feedback> mess = new ArrayList<Feedback>();
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Page<Feedback> page = feedbackDao.findByCategoryOrCategoryAndMemberId(category1,category2,memberId,pageRequest);
		mess=page.getContent();
		return mess;
	}
	
	/**
	 * 分页查询数据(后台）
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Feedback> getMessages(Long uid,Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Feedback> spec = buildSpecification(uid,searchParams);
		return feedbackDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		} else if ("dateAsc".equals(sortType)) {
			sort = new Sort(Direction.ASC, "crtDate");
		}else if ("dateDesc".equals(sortType)) {
			sort = new Sort(Direction.DESC, "crtDate");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 信息反馈(服务端查看)
	 * 创建动态查询条件组合.
	 */
	private Specification<Feedback> buildSpecification(Long uid,Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("category", new SearchFilter("category", Operator.EQ, Feedback.CATEGORY_CLIENT_CALLBACK));
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));	
		Specification<Feedback> spec = DynamicSpecifications.bySearchFilter(filters.values(), Feedback.class);
		return spec;
	}

}
