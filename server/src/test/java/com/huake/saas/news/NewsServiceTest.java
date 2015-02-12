package com.huake.saas.news;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.huake.saas.BaseTransactionalTestCase;
import com.huake.saas.news.entity.News;
import com.huake.saas.news.repository.NewsDao;

public class NewsServiceTest extends BaseTransactionalTestCase {

	@Autowired
	private NewsDao newsDao;
	
	@Test
	public void testQueryNews() throws Exception{
		Pageable pageable = new PageRequest(0, 5, new Sort(Direction.DESC, new String[] { "banner","stick","crtDate" }));
		Page<News> result = newsDao.findPublishedNewsByCategory("20140417122551", "1", "1",2, pageable);
		System.out.println(result.getContent());
	}
}
