package com.huake.saas.place.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.huake.saas.place.entity.PlaceRelation;
import com.huake.saas.place.repository.PlaceRelationDao;


@Component
//类中所有public函数都纳入事务管理的标识.
@Transactional
public class PlaceRelationService {
	
	@Autowired
	private  PlaceRelationDao placeRelationDao;


	public PlaceRelation findById(Integer id){
		return placeRelationDao.findOne(id);
	}
	
	public  PlaceRelation findCitysName(String city) {
		Map<String, Object> searchParams=new HashMap<String, Object>();
		searchParams.put("EQ_areaCode", city);
		searchParams.put("EQ_level", PlaceRelation.LEVEL_2.toString());
		Specification<PlaceRelation> spec = buildSpecByParams(searchParams);
		return placeRelationDao.findOne(spec);
	}

	private static Specification<PlaceRelation> buildSpecByParams( Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<PlaceRelation> spec = DynamicSpecifications.bySearchFilter(filters.values(), PlaceRelation.class);
		return spec;
	}
	
	/**
	 * 根据级别查找省市
	 * @param level
	 * @return
	 * @throws AppBizException
	 */
	public List<PlaceRelation> findProvinces(Integer level) {
		List<PlaceRelation> prs=placeRelationDao.findByLevel(level);
		return prs;
	}

	/**
	 * 根据省份查找城市
	 * @param parentLevel
	 * @return
	 * @throws AppBizException
	 */
	public List<PlaceRelation> findCitys(Integer parentLevel) {
		return placeRelationDao.findByParentLevel(parentLevel);
	}
	
	public PlaceRelation findProvinceById(Integer parentLevel){
		return placeRelationDao.findOne(parentLevel);
	}

	
}