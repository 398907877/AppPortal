package com.huake.saas.feedback.respository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.feedback.entity.Feedback;


public interface FeedbackDao extends PagingAndSortingRepository<Feedback, Long>,JpaSpecificationExecutor<Feedback>{

	@Query("select fb from Feedback fb where memberId= :memberId order by crtDate asc")
	public List<Feedback> findByMemberId(@Param("memberId")Long memberId);
	
	@Query("from Feedback fb where memberId= :memberId and (category = :category or category = :category2 ) ")
	Page<Feedback> findByCategoryOrCategoryAndMemberId(@Param("category")String category, @Param("category2")String category1,@Param("memberId")Long memberId,Pageable page);
}
