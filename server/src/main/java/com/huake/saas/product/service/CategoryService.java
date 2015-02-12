package com.huake.saas.product.service;

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
import com.huake.saas.product.entity.Category;
import com.huake.saas.product.repository.CategoryDao;

//Spring Bean的标识.
@Component
//类中所有public函数都纳入事务管理的标识.
@Transactional
public class CategoryService {
	
	@Autowired
	private CategoryDao categorytDao;
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * admin
	 * @param status
	 * @return
	 */
	public List<Category> getAllCategory(String status) {
		return categorytDao.findCategroysByStatus(status);
	}
	
	/**
	 * 普通租户
	 * @param status
	 * @param uid
	 * @return
	 */
	public List<Category> getAllCategorys(String status,long uid) {
	    return categorytDao.findCategroysByStatusAndUid(status,String.valueOf(uid));
	}
	
	
	
	public Category getCategoryById(long id) {
		return categorytDao.findOne(id);
	}
	
	public Category getCategory(Long id) {
		return categorytDao.findOne(id);
	}

	public void saveCategory(Category entity) {
		categorytDao.save(entity);
	}
	
	public void saveCategory(String uid,String name) {
		Category entity = new Category();
		entity.setCrtDate(new Date());
		entity.setUpDate(new Date());
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		if(null==uid){
			entity.setUid("");
		}
		entity.setUid(uid);
		entity.setName(name);
		categorytDao.save(entity);
	}

	public Category deleteCategory(Long id) {
		Category category = getCategory(id);
		category.setStatus(BaseEntity.STATUS_INVALIDE);
		categorytDao.save(category);
		return category;
	}

	public List<Category> getAllCategory() {
		return (List<Category>) categorytDao.findAll();
	}

	public Page<Category> getUserCategory(Long uid, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Category> spec = buildSpecification(uid, searchParams);

		return categorytDao.findAll(spec, pageRequest);
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
	private Specification<Category> buildSpecification(Long uid, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		/*if(userId!=null&&!"".equals(userId)){
			User user = accountService.getUser(userId);
			if(!User.USER_ROLE_ADMIN.equals(user.getRoles())){
				
			}
		}*/
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status", new SearchFilter("status", Operator.EQ, BaseEntity.STATUS_VALIDE));
		return DynamicSpecifications.bySearchFilter(filters.values(), Category.class);
	}
	
	/**
	 * 修改名字
	 * @param id
	 * @param title
	 */
	public void changeName(Long id,String title){
		Category category = categorytDao.findOne(id);
		category.setName(title);
		category.setUpDate(new Date());
		categorytDao.save(category);
	}


}
