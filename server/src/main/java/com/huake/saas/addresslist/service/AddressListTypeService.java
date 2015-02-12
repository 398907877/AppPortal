package com.huake.saas.addresslist.service;


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
import com.huake.saas.addresslist.entity.AddressListType;
import com.huake.saas.addresslist.repository.AddressListTypeDao;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.user.service.TenancyUserService;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class AddressListTypeService {
	@Autowired
	private AddressListTypeDao addressListTypeDao;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TenancyUserService userService;

	public AddressListType getAddressListType(Long id) {
		return addressListTypeDao.findOne(id);
	}
	
	/**
	 * 根据状态查询类型列表
	 * @param status
	 * @return
	 */
	public List<AddressListType> getAddressListTypes(String status){
		return addressListTypeDao.findAddressListTypesByStatus(status);
	}
	
	/**
	 * 根据状态和租户id查询类型列表
	 * @param status
	 * @return
	 */
	public List<AddressListType> getAddressListTypes(String status,String uid){
		return addressListTypeDao.findAddressListTypesByStatusAndUid(status,uid);
	}
	

	public void saveAddressListType(AddressListType entity) {
		addressListTypeDao.save(entity);
	}

	public AddressListType deleteAddressListType(Long id) {
		AddressListType addressListType = getAddressListType(id);
		addressListType.setStatus(BaseEntity.STATUS_INVALIDE);
		addressListTypeDao.save(addressListType);
		return addressListType;
	}

	public List<AddressListType> getAllAddressListType() {
		return (List<AddressListType>) addressListTypeDao.findAll();
	}

	public Page<AddressListType> getUserAddressListType(Long uid, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<AddressListType> spec = buildSpecification(uid, searchParams);

		return addressListTypeDao.findAll(spec, pageRequest);
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
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<AddressListType> buildSpecification(Long uid, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		/*if(userId!=null&&!"".equals(userId)){
			User user = accountService.getUser(userId);
			if(!User.USER_ROLE_ADMIN.equals(user.getRoles())){
				
			}
		}*/
		
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status", new SearchFilter("status", Operator.EQ, BaseEntity.STATUS_VALIDE));
		Specification<AddressListType> spec = DynamicSpecifications.bySearchFilter(filters.values(), AddressListType.class);
		return spec;
	}
	
	/**
	 * 修改组名
	 * @param id
	 * @param title
	 */
	public void changeName(Long id,String title){
		AddressListType addressListType = addressListTypeDao.findOne(id);
		addressListType.setName(title);
		addressListType.setUpDate(new Date());
		addressListTypeDao.save(addressListType);
	}
	
	public void saveAddressListType(String uid,String name) {
		AddressListType entity = new AddressListType();
		entity.setCrtDate(new Date());
		entity.setUpDate(new Date());
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		if(null==uid){
			entity.setUid("");
		}
		entity.setUid(uid);
		entity.setName(name);
		addressListTypeDao.save(entity);
	}

}
