package com.huake.saas.news.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.news.entity.News;

public interface NewsDao extends PagingAndSortingRepository<News, Long>, JpaSpecificationExecutor<News>{

	@Query("select a from News a where a.uid = :uid and a.status = :status and a.publish = :publish and a.categoryId = :categoryId order by a.banner,a.stick,a.crtDate desc") 
	public Page<News> findPublishedNewsByCategory(@Param("uid") String uid, 
													@Param("status") String status, 
													@Param("publish")String publish,
													@Param("categoryId") Integer categoryId, 
													Pageable pageable); 
	
	@Query("select a from News a where a.uid = :uid and a.status = :status and a.publish = :publish order by a.banner,a.stick,a.crtDate desc") 
	public Page<News> findPublishedNews(@Param("uid") String uid, 
													@Param("status") String status, 
													@Param("publish")String publish,
													Pageable pageable);
	
	@Query("select a from News a where a.uid = :uid and a.status = :status and a.banner = :banner order by a.stick desc") 
	public List<News> findBannerNews(@Param("uid") String uid, 
													@Param("status") String status, 
													@Param("banner")String banner);
	
	@Query("select a from News a where a.uid = :uid and a.status = :status and a.banner <> :banner and a.publish = :publish and a.categoryId = :categoryId order by a.banner,a.stick,a.crtDate desc") 
	public Page<News> findPublishedNewsByCategory(@Param("uid") String uid, 
													@Param("status") String status, 
													@Param("publish")String publish,
													@Param("categoryId") Integer categoryId, 
													@Param("banner")String banner,
													Pageable pageable); 
	
	@Query("select a from News a where a.uid = :uid and a.status = :status and a.banner <> :banner and a.publish = :publish order by a.banner,a.stick,a.crtDate desc") 
	public Page<News> findPublishedNews(@Param("uid") String uid, 
													@Param("status") String status, 
													@Param("publish")String publish,
													@Param("banner")String banner,
													Pageable pageable);
	
	@Query("select max(n.stick) from News n where n.uid = :uid ")
	public Long findTopnum(@Param("uid") String uid);
}
