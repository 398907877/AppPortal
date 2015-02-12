package com.huake.saas.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.news.entity.NewsPictures;

public interface NewsPicturesDao extends PagingAndSortingRepository<NewsPictures, Long>,JpaSpecificationExecutor<NewsPictures>{
	@Query("from NewsPictures c where c.newsId= :newsid")
    List<NewsPictures> findNewsPicturesByNewsId(@Param("newsid")Integer newsid);
}
