package com.huake.saas.news.service;

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
import com.huake.saas.news.entity.NewsCategory;
import com.huake.saas.news.repository.NewsCategoryDao;

//Spring Bean的标识.
@Component
//类中所有public函数都纳入事务管理的标识.
@Transactional
public class NewsCategoryService{
	
	private NewsCategoryDao newsCategoryDao;
	
	@Autowired
	private AccountService accountService;
	
	public NewsCategory getNewsCategory(Long id) {
		return newsCategoryDao.findOne(id);
	}

	public void saveNewsCategory(NewsCategory entity) {
		newsCategoryDao.save(entity);
	}

	public NewsCategory deleteNewsCategory(Long id) {
		NewsCategory category = getNewsCategory(id);
		category.setStatus(BaseEntity.STATUS_INVALIDE);
		newsCategoryDao.save(category);
		return category;
	}
	
	public void saveNewsCategory(String uid,String name) {
		NewsCategory entity = new NewsCategory();
		entity.setCrtDate(new Date());
		entity.setUpDate(new Date());
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		if(null==uid){
			entity.setUid("");
		}
		entity.setUid(uid);
		entity.setName(name);
		newsCategoryDao.save(entity);
	}
	
	public List<NewsCategory> getAllNewsCategory(String status,long uid) {
	    return  newsCategoryDao.findNewsCategroysByStatusAndUid(status,String.valueOf(uid));
	}
	
	public List<NewsCategory> getAllNewsCategorys(String status) {
	  return newsCategoryDao.findNewsCategroysByStatus(status);
	}
	
	public NewsCategory getNewsCategoryById(long id) {
		return newsCategoryDao.findOne(id);
	}
	
	public Page<NewsCategory> getUserNewsCategory(Long uid, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<NewsCategory> spec = buildSpecification(uid, searchParams);

		return newsCategoryDao.findAll(spec, pageRequest);
	}
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if (BaseEntity.PAGE_CRTDATE_DESC.equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if (BaseEntity.PAGE_CRTDATE_ASC.equals(sortType)) {
			sort = new Sort(Direction.ASC, BaseEntity.PAGE_CRTDATE_ASC);
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<NewsCategory> buildSpecification(Long uid, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		/*if(userId!=null&&!"".equals(userId)){
			User user = accountService.getUser(userId);
			if(!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())){
				
			}
		}*/
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status", new SearchFilter("status", Operator.EQ, BaseEntity.STATUS_VALIDE));
		return DynamicSpecifications.bySearchFilter(filters.values(), NewsCategory.class);
	}
	
	/**
	 * 修改名字
	 * @param id
	 * @param title
	 */
	public void changeName(Long id,String title){
		NewsCategory category = newsCategoryDao.findOne(id);
		category.setName(title);
		category.setUpDate(new Date());
		newsCategoryDao.save(category);
	}
	
	@Autowired
	public void setNewsCategoryDaoDao(NewsCategoryDao newsCategoryDao) {
		this.newsCategoryDao = newsCategoryDao;
	}

}
