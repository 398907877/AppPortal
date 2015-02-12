package com.huake.saas.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.news.entity.NewsContent;


public interface NewsContentDao extends PagingAndSortingRepository<NewsContent, Long>, JpaSpecificationExecutor<NewsContent>{
	@Query("from NewsContent c where c.newsId= :newsid order by c.seq")
    List<NewsContent> findNewsContentByNewsId(@Param("newsid")Long newsid);
}
