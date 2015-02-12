package com.huake.saas.supply.service;

import java.util.ArrayList;
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
import com.huake.saas.supply.entity.SupplyDemandType;
import com.huake.saas.supply.repository.SupplyDemandTypeDao;




/**
 * 供求信息管理类型Service（租户没有权限）
 * @author zhongshuhui
 *
 */
//Spring Bean的标�?
@Component
//类中�?��public函数都纳入事务管理的标识.
@Transactional
//设计不对，销除，需重构 TODO
@Deprecated
public class SupplyDemandTypeService {
	
	private SupplyDemandTypeDao supplyDemandTypeDao;

	
	public SupplyDemandType getSupplyDemandType(Long id) {
		return supplyDemandTypeDao.findOne(id);
	}

	public void saveSupplyDemandType(SupplyDemandType entity) {
		supplyDemandTypeDao.save(entity);
	}
    
	/**
	 * 添加新的entity
	 * @param entity
	 */
	public void saveNewSupplyDemandType(SupplyDemandType entity) {
		entity.setStatus(SupplyDemandType.STATUS_VALIDE);
			
		if(entity.getType()==2){
			entity.setType(SupplyDemandType.TYPE_VALUE_SUPPLY);
			SupplyDemandType type = new SupplyDemandType();
			type.setStatus(SupplyDemandType.STATUS_VALIDE);
			type.setSupplyDemandType(entity.getSupplyDemandType());
			type.setType(SupplyDemandType.TYPE_VALUE_DEMAND);
			supplyDemandTypeDao.save(type);
		}
		supplyDemandTypeDao.save(entity);
		
	}
	
	public void deleteSupplyDemandType(Long id) {
		SupplyDemandType type = getSupplyDemandType(id);
		type.setStatus(SupplyDemandType.STATUS_INVALIDE);
		saveSupplyDemandType(type);
	}
	

	public List<SupplyDemandType> getAllSupplyDemandType() {
		return (List<SupplyDemandType>) supplyDemandTypeDao.findAll();
	}

	public Page<SupplyDemandType> getUserSupplyDemandType( Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<SupplyDemandType> spec = buildSpecification(searchParams);
		return supplyDemandTypeDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("supplyDemandType".equals(sortType)) {
			sort = new Sort(Direction.ASC, "supplyDemandType");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动�?查询条件组合.
	 */
	private Specification<SupplyDemandType> buildSpecification( Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		return DynamicSpecifications.bySearchFilter(filters.values(), SupplyDemandType.class);
	}
	
	/**
	 * 获取有效的供求信息类
	 *  @param status
	 * @return
	 */
	public List<SupplyDemandType> getSupplyDemandTypes(int status){
		return supplyDemandTypeDao.findSupplyDemandTypesByStatus(status);
	}
	
	public List<SupplyDemandType> getSupplyDemandTypesAndType(int status,int type){
		return supplyDemandTypeDao.findSupplyDemandTypesByStatusAndType(status,type);
	}
	
	
	public List<SupplyDemandType> getSupplyDemandTypesByType(int isVialid){
		List<SupplyDemandType> supplys = new ArrayList<SupplyDemandType>();
		int status = Integer.parseInt(BaseEntity.STATUS_VALIDE);
		List<SupplyDemandType> demandTypes = getSupplyDemandTypes(status);
		for(SupplyDemandType type:demandTypes){
			if(type.getType()==isVialid){
				supplys.add(type);
			}
		}
		return supplys;
		
	}
	
	/**
	 * 修改名字
	 * @param id
	 * @param title
	 */
	public void changeName(Long id,String title){
		SupplyDemandType category = supplyDemandTypeDao.findOne(id);
		category.setSupplyDemandType(title);
		supplyDemandTypeDao.save(category);
	}

	@Autowired
	public void setSupplyDemandTypeDao(SupplyDemandTypeDao supplyDemandTypeDao) {
		this.supplyDemandTypeDao = supplyDemandTypeDao;
	}

}
