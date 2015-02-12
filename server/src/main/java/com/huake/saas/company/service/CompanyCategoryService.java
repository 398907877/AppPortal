package com.huake.saas.company.service;

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
import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.company.entity.CompanyCategory;
import com.huake.saas.company.repository.CompanyCategoryDao;

//Spring Bean的标识.
@Component
//类中所有public函数都纳入事务管理的标识.
@Transactional
public class CompanyCategoryService{
	
	private CompanyCategoryDao companyCategoryDao;
	
	@Autowired
	private AccountService accountService;
	
	public CompanyCategory getCompanyCategory(Long id) {
		return companyCategoryDao.findOne(id);
	}

	public void saveCompanyCategory(CompanyCategory entity) {
		companyCategoryDao.save(entity);
	}

	public CompanyCategory deleteCompanyCategory(Long id) {
		CompanyCategory category = getCompanyCategory(id);
		category.setStatus(BaseEntity.STATUS_INVALIDE);
		companyCategoryDao.save(category);
		return category;
	}
	
	public void saveCompanyCategory(String uid,String name) {
		CompanyCategory entity = new CompanyCategory();
		entity.setCrtDate(new Date());
		entity.setUpDate(new Date());
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		if(null==uid){
			entity.setUid("");
		}
		entity.setUid(uid);
		entity.setName(name);
		companyCategoryDao.save(entity);
	}
	/**
	 * admin
	 * @param status
	 * @return
	 */
	public List<CompanyCategory> getAllCompanyCategory(String status) {
	 return companyCategoryDao.findCompanyCategroysByStatus(status);
	}
	
	/**
	 * 普通租户
	 * @param status
	 * @param uid
	 * @return
	 */
	public List<CompanyCategory> getAllCompanyCategorys(String status,long uid) {
	    return companyCategoryDao.findCompanyCategroysByStatusAndUid(status,String.valueOf(uid));
	}
	
	
	public CompanyCategory getCompanyCategoryById(long id) {
		return companyCategoryDao.findOne(id);
	}
	
	public Page<CompanyCategory> getUserCompanyCategory(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<CompanyCategory> spec = buildSpecification(userId, searchParams);

		return companyCategoryDao.findAll(spec, pageRequest);
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
	private Specification<CompanyCategory> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		if(userId!=null&&!"".equals(userId)){
			User user = accountService.getUser(userId);
			if(!User.USER_ROLE_ADMIN.equals(user.getRoles())){
				filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));
			}
		}
		return DynamicSpecifications.bySearchFilter(filters.values(), CompanyCategory.class);
	}
	
	/**
	 * 修改名字
	 * @param id
	 * @param title
	 */
	public void changeName(Long id,String title){
		CompanyCategory category = companyCategoryDao.findOne(id);
		category.setName(title);
		category.setUpDate(new Date());
		companyCategoryDao.save(category);
	}
	
	@Autowired
	public void setCompanyCategoryDaoDao(CompanyCategoryDao companyCategoryDao) {
		this.companyCategoryDao = companyCategoryDao;
	}

}
