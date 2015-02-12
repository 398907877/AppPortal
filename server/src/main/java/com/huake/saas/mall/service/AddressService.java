package com.huake.saas.mall.service;

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

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.mall.entity.Address;
import com.huake.saas.mall.repositiry.AddressDao;

@Component
@Transactional
public class AddressService {
	@Autowired
	private AddressDao addressDao;
	
	/**
	 * 根据id查找收货地址
	 * @param id
	 * @return
	 */
	public Address findById(Long id){
		return addressDao.findOne(id);
	}
	/**
	 * 根据用户id 查找用户所以收货地址
	 * @param userId
	 * @return
	 */
	public List<Address> findByUserId(Long userId){
		return addressDao.findByMemberId(userId);
	}
	/**
	 * 更新收货地址
	 * @param address
	 */
	public void update(Address address){
		addressDao.save(address);
	}
	/**
	 * 根据id删除收货地址
	 * @param id
	 */
	public void delete(Long id){
		addressDao.delete(id);
	}
	
	/**
	 * 根据api的条件查找用户的所有收货地址
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Address> findByApiCondition(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Address> spec = buildSpecByParams(searchParams);
		return addressDao.findAll(spec, pageRequest);
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
	 * 查询条件组合for Api
	 */
	private Specification<Address> buildSpecByParams( Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Address> spec = DynamicSpecifications.bySearchFilter(filters.values(), Address.class);
		return spec;
	}
}
