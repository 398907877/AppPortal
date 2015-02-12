package com.huake.saas.news.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.web.controller.BaseMobileController;
import com.huake.saas.news.entity.News;
import com.huake.saas.news.entity.NewsCategory;
import com.huake.saas.news.service.NewsCategoryService;
import com.huake.saas.news.service.NewsService;

/**
 * 新闻资讯手机端页面展示
 */
@Controller
@RequestMapping(value = "/m/news")
public class NewsMobileController extends BaseMobileController {

	private final Logger log = LoggerFactory.getLogger(NewsMobileController.class);
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private NewsCategoryService newsCategoryService;
	

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(@RequestParam("uid")  final String UID,@RequestParam(value = "categoryId", required = false) final Integer categoryId, 
	@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum)throws AppBizException{
		ModelAndView mav = new ModelAndView();
		if(UID == null){
			throw new AppBizException("无效的访问");
		}
		List<NewsCategory> categories = newsCategoryService.getAllNewsCategory(NewsCategory.CATEGORY_STATUS_VALID, Long.valueOf(UID));
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Map<String, Object> searchParams1 = new HashMap<String, Object>();
		if(categoryId != null){
			searchParams.put("EQ_categoryId", categoryId.toString());
			searchParams1.put("EQ_categoryId", categoryId.toString());
			NewsCategory newsCategory = newsCategoryService.getNewsCategoryById(categoryId);
			mav.addObject("category", newsCategory);
		}
		searchParams.put("EQ_banner", "0");
		searchParams1.put("EQ_banner", "1");
		Page<News> news = newsService.getUserNews(Long.valueOf(UID), searchParams, pageNum, 5, "auto");
		Page<News> banners = newsService.getUserNews(Long.valueOf(UID), searchParams1, pageNum, 5, "auto");
		mav.addObject("UID", UID);
		mav.addObject("categoryId", categoryId);
		mav.addObject("news", news);
		mav.addObject("banners", banners);
		mav.addObject("categories", categories);
		mav.setViewName("mobile/news/index");
		return mav;
	}
	
	/**
	 * 分页获取新闻列表。
	 * @param categoryId
	 * @param pageNum
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<News> listNews(@RequestParam("UID")  final String UID,@RequestParam(value = "categoryId", required = false) final Integer categoryId, 
	@RequestParam(value = "pageNum",defaultValue="2", required = false) Integer pageNum)throws AppBizException{
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(categoryId != null){
		searchParams.put("EQ_categoryId", categoryId.toString());
		}
		searchParams.put("EQ_banner", "0");
		List<News> news = newsService.getUserNews(Long.valueOf(UID), searchParams, pageNum, 5, "auto").getContent();
		for (News news2 : news) {
			String str = news2.getIntro();
			if(news2.getIntro().length()>10){
				str = str.substring(0,10)+"...";
			}
			news2.setIntro(str);
		}
		return news;
	}
	
}
