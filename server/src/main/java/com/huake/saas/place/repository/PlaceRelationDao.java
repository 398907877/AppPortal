package com.huake.saas.place.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.place.entity.PlaceRelation;


public interface PlaceRelationDao extends PagingAndSortingRepository<PlaceRelation, Integer>,JpaSpecificationExecutor<PlaceRelation>{
	List<PlaceRelation> findByParentLevel(Integer parentLevel);
	List<PlaceRelation> findByLevel(Integer level);
}


