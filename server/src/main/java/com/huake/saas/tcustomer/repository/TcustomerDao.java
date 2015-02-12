package com.huake.saas.tcustomer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.base.repository.GenericRepository;
import com.huake.saas.tcustomer.entity.Tcustomer;


public interface TcustomerDao extends GenericRepository<Tcustomer, Long> ,PagingAndSortingRepository<Tcustomer, Long>,JpaSpecificationExecutor<Tcustomer>{
	
	@Query("select t from Tcustomer t where t.status = :status and t.tenancy.uid = :uid")
	public Page<Tcustomer> findTcustomer(@Param("status") Integer status,@Param("uid")Long uid,Pageable pageable);
	
	@Query("select t from Tcustomer t where t.status = :status and t.tenancy.uid = :uid")
	public List<Tcustomer> findAllTcustomer(@Param("status") Integer status,@Param("uid") Long uid);
	
	@Query("select t from Tcustomer t inner join t.customerTypes type where type.id = :tid and t.status = :status and t.tenancy.uid = :uid")
	public Page<Tcustomer> findTcustomerByType(@Param("tid")Long tid,@Param("status")Integer status,@Param("uid")Long uid,Pageable pageable);
	
	@Query("select t from Tcustomer t where t.status = :status and t.tenancy.uid = :uid and (t.name like :condition or t.linkman like :condition or t.businessScope like :condition)")
	public Page<Tcustomer> findByCondition(@Param("status") Integer status,
										@Param("uid")Long uid,
										@Param("condition")String condition,
										Pageable pageable);
}
