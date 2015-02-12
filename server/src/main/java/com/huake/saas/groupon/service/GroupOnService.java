package com.huake.saas.groupon.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.groupon.entity.GroupOn;
import com.huake.saas.groupon.repository.GroupOnDao;
import com.huake.saas.groupon.repository.GroupOnFieldsDao;

@Service
@Transactional
public class GroupOnService {

	@Autowired
	private GroupOnDao groupOnDao;
	
	@Autowired
	private GroupOnFieldsDao fieldsDao;
	
	/**
	 * 根据条件 分页查询 团购信息
	 * @param uid 租户id 过滤
	 * @param searchParams 过滤参数
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<GroupOn> findByCondition(Long uid, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<GroupOn> spec = buildSpecification(uid, searchParams);
		return groupOnDao.findAll(spec, pageRequest);
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
	private Specification<GroupOn> buildSpecification(Long uid, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status", new SearchFilter("status", Operator.EQ, BaseEntity.STATUS_VALIDE));
		return DynamicSpecifications.bySearchFilter(filters.values(), GroupOn.class);
	}
	
	/**
	 * 通过ID查询对象
	 * @param id
	 * @return
	 */
	public GroupOn findById(Long id){
		return groupOnDao.findOne(id);
	}
}
