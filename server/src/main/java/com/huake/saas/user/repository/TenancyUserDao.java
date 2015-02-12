package com.huake.saas.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.user.entity.TenancyUser;

public interface TenancyUserDao extends PagingAndSortingRepository<TenancyUser, Long>, JpaSpecificationExecutor<TenancyUser>{

	@Query("select  u from TenancyUser u where u.status=1 and loginname = :loginName and u.uid= :uid")
	TenancyUser findByLoginnameAndUid(@Param("loginName")String loginName,@Param("uid")Long uid);
	
	@Query("select count(*) from TenancyUser u where u.status=1 and u.uid= :uid")
	int count(@Param("uid")Long uid);
	
	List<TenancyUser> findByUid(long uid);
	
	TenancyUser findByOpenid(String openid);
	
	TenancyUser findByQqIdAndStatus(String trim, int normal);

	TenancyUser findBySinaIdAndStatus(String trim, int normal);
	
	@Query("from TenancyUser c where c.status= :status and c.uid= :uid")
    List<TenancyUser> findTenancyUsersByStatusAndUid(@Param("status")int status,@Param("uid")long uid);
	
	@Query("from TenancyUser c where c.status= :status")
    List<TenancyUser> findTenancyUsersByStatus(@Param("status")int status);
	
	@Query("select u from TenancyUser u where u.uid =:uid and u.name=:name and u.status=:status and u.mobile=:mobile and u.loginname=:loginname")
	TenancyUser findByCondition(@Param("uid")Long uid,
								@Param("name")String name,
								@Param("status")Integer status,
								@Param("mobile")String mobile,
								@Param("loginname")String loginname);
}
