package com.huake.saas.news.web.api.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.web.controller.BaseApiController;
import com.huake.saas.news.entity.News;
import com.huake.saas.news.entity.NewsCategory;
import com.huake.saas.news.entity.NewsDetail;
import com.huake.saas.news.service.NewsCategoryService;
import com.huake.saas.news.service.NewsService;
import com.huake.saas.tenancy.service.TenancyService;

@Controller
@RequestMapping(value = "/api/v1/news")
public class NewsApiController extends BaseApiController{
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private NewsCategoryService newsCategoryService;
	
	@Autowired
	private TenancyService tenancyService;

   /**
    * 新闻列表
    * @param categoryId
    * @param first
    * @param max
    * @return
    * @throws Exception
    */
	@RequestMapping(value = "/list", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public NewsDetail getNewss(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestParam(value = "categoryId", required = false) String categoryId,
			@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum){
		System.out.println(categoryId+"::::::::::::::::::::::");
	   List<NewsCategory> newsCategory = newsCategoryService.getAllNewsCategory(BaseEntity.STATUS_VALIDE,uid);
       Map<String, Object> searchParams = new HashMap<String, Object>();
       if(null!=categoryId){
    	   searchParams.put("EQ_categoryId", categoryId);
    	  /* if(null!=newsCategory&&newsCategory.size()>0){
    		   categoryId = newsCategory.get(0).getId().toString();
    	   }*/
       }
       searchParams.put("EQ_banner", News.NOT_BANNER);
       searchParams.put("EQ_status", BaseEntity.STATUS_VALIDE);
       searchParams.put("EQ_publish", "1");
       List<News> newss = newsService.getUserNewsApi(uid,
				searchParams, pageNum, BaseEntity.DATE_MAX, BaseEntity.PAGE_CRTDATE_DESC).getContent();
       //获取新闻内容
       for(int i = 0;i < newss.size();i++){
    	   newss.get(i).setContents(newsService.findNewsContentByNewsId(newss.get(i).getId()));
       }
       NewsDetail detail = new NewsDetail();
       detail.setNewsCategory(newsCategory);
       detail.setNews(newss);
       return detail;
	}
	
	/**
	 * 获取新闻详细
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public News getNews(@PathVariable final Long id){
		News news = newsService.getNews(id);
		news.setContents(newsService.findNewsContentByNewsId(id));
		return news;
	}
	/**
	 * 获取banner新闻
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/getBanner", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<News> getBannerNews(@RequestParam(value="uid",required=true)Long uid){
		List<News> bannerNews = newsService.findBannerNews(uid.toString());
		for (int i = 0; i < bannerNews.size(); i++) {
			bannerNews.get(i).setContents(
					newsService.findNewsContentByNewsId(bannerNews.get(i)
							.getId()));
		}
		return bannerNews;
	}
}
