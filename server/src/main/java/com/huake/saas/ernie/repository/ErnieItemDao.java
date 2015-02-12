package com.huake.saas.ernie.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.ernie.entity.ErnieItem;

public interface ErnieItemDao extends PagingAndSortingRepository<ErnieItem, Long>,JpaSpecificationExecutor<ErnieItem>{
	@Query("select a from ErnieItem a where a.uid = :uid and a.status= :status")
	public  Page<ErnieItem> findByUid(@Param("uid") Long uid, Pageable pageable,@Param("status")String status);
	
	@Query("select a from ErnieItem a where a.uid = :uid and a.ernie.id = :ernieId and a.status = :status")
	public  Page<ErnieItem> findByUid(@Param("uid") Long uid,@Param("ernieId") Long ernieId, Pageable pageable,@Param("status")String status);
	
	@Query("select a from ErnieItem a where a.ernie.id = :id and a.status = :status order by a.probability desc")
	public  List<ErnieItem> findByErnieId(@Param("id") Long ernieId,@Param("status")String status);
	
	/**
	 * 根据博饼结果 等级和 博饼活动id  获取  博饼 所获得的的奖品
	 * @param bobing  博饼 奖品等级
	 * @param ernieId 活动id
	 * @return
	 */
	@Query("select e from ErnieItem e where e.ernie.id = :ernieId and e.bobing = :bobing and e.status= :status")
	public ErnieItem findByBobingAndErnieId(@Param("bobing")String bobing,@Param("ernieId") Long ernieId,@Param("status")String status);
}
