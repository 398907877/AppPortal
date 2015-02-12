package com.huake.saas.news.web.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huake.saas.base.entity.Message;
import com.huake.saas.news.entity.News;
import com.huake.saas.news.entity.NewsCategory;
import com.huake.saas.news.entity.NewsPictures;
import com.huake.saas.news.service.NewsCategoryService;
import com.huake.saas.news.service.NewsService;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;

@Controller
@RequestMapping(value = "/portal/news")
public class portalController {

	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private NewsCategoryService newsCategoryService;
	
	@Autowired
	private TenancyService tenancyService;
	
	/**
	 * 跳转到预览界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String preview(@PathVariable("id") Long id, Model model) {
		model.addAttribute(News.PARMS_NEWS, newsService.getNews(id));
		NewsCategory newsCategory = newsCategoryService
				.getNewsCategoryById(newsService.getNews(id).getCategoryId());
		int newid = newsService.getNews(id).getId().intValue();
		List<NewsPictures> pictures = newsService.getPictures(newid);
		String w = newsService.getNews(id).getDetail();
		String what = w.replaceAll("<p>", "").replaceAll("</p>", "");
		String uid = newsService.getNews(id).getUid();
		Tenancy tenancy = tenancyService.getTenancy(Long.valueOf(uid));
		model.addAttribute("what", what);
		model.addAttribute("tenancy", tenancy);
		model.addAttribute("pictures", pictures);
		model.addAttribute(NewsCategory.PARMS_NEWS_CATEGORY, newsCategory);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "news/preview";
	}
}
