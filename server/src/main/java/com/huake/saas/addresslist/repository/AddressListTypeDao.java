package com.huake.saas.addresslist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.addresslist.entity.AddressListType;

public interface AddressListTypeDao extends PagingAndSortingRepository<AddressListType, Long>, JpaSpecificationExecutor<AddressListType>{

	@Query("from AddressListType c where c.status=:status")
    List<AddressListType> findAddressListTypesByStatus(@Param("status") String status);
	
	@Query("from AddressListType c where c.status= :status and c.uid= :uid")
    List<AddressListType> findAddressListTypesByStatusAndUid(@Param("status") String status,@Param("uid") String uid);
}
