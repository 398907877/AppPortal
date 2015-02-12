package com.huake.saas.supply.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.supply.entity.SupplyDemandPhoto;

public interface SupplyDemandPhotoDao extends PagingAndSortingRepository<SupplyDemandPhoto, Long>, JpaSpecificationExecutor<SupplyDemandPhoto> {
	
	public List<SupplyDemandPhoto> findBySupplyDemandIdAndImgType(Long id,String imgType);
}
