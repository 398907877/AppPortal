package com.huake.saas.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.mobileResource.entity.MobileResource;
import com.huake.saas.mobileResource.service.MobileResourceService;
import com.huake.saas.product.entity.Product;
import com.huake.saas.product.repository.ProductDao;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.ToWeixinArticle;
import com.huake.saas.weixin.model.ToWeixinArticles;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;



/**
 * 
 * @author hxl
 *产品展示的业务
 */

@Service("productWeixinTarget")
public class ProductWeixinTargetImpl implements ProductWeixinTarget{
	
	@Autowired
	private  ProductDao productDao;
	
	@Autowired
	private MobileResourceService mobileService;
	
	@Value("#{envProps.mobileUrl}")
	private String mobileUrl;

	@Override
	public ToWeixinNewsMessage getProductByCategory(String uid, String category,String resultCount) {
		Pageable pageable = new PageRequest(0, new Integer(resultCount), new Sort(Direction.DESC, new String[] { "crtDate" }));
		Page<Product> result = productDao.findByCategory(Integer.valueOf(category), BaseEntity.STATUS_VALIDE, uid, pageable);
		

		WebContext context = WebContext.getInstance();
		ToWeixinNewsMessage msg = new ToWeixinNewsMessage();
		msg.setToUserName(context.getFromUserName());
		msg.setFromUserName(context.getToUserName());
		msg.setCreateTime(System.currentTimeMillis());

		List<ToWeixinArticle> articles = new ArrayList<ToWeixinArticle>();

		for (Product product : result.getContent()) {

			ToWeixinArticle toWa = new ToWeixinArticle();
			toWa.setTitle(product.getTitle());
			toWa.setThumbnail(product.getThumb());
			toWa.setDescription(product.getIntro());
			toWa.setUrl(""); //TODO 产品链接待定
			articles.add(toWa);
		}

		//默认加入 查看更多的记录
		//String readpath= context.getServletContext().getContextPath();
		String url = createIndexUrl(uid);			
		ToWeixinArticle toWa = new ToWeixinArticle();
		toWa.setTitle("显示更多");
		toWa.setUrl(url);
		articles.add(toWa);
		
		ToWeixinArticles tas = new ToWeixinArticles();
		tas.setArticles(articles);
		msg.setArticleCount(articles.size());
		msg.setArticles(tas);
		return msg;
	}


	@Override
	public ToWeixinNewsMessage getProduct(String uid, String resultCount) {
		Pageable pageable = new PageRequest(0, new Integer(resultCount), new Sort(Direction.DESC, new String[] { "crtDate" }));
		Page<Product> result = productDao.findProducts(BaseEntity.STATUS_VALIDE, uid,pageable );
		
		
		WebContext context = WebContext.getInstance();
		ToWeixinNewsMessage msg = new ToWeixinNewsMessage();
		msg.setToUserName(context.getFromUserName());
		msg.setFromUserName(context.getToUserName());
		msg.setCreateTime(System.currentTimeMillis());

		List<ToWeixinArticle> articles = new ArrayList<ToWeixinArticle>();

		for (Product product : result.getContent()) {

			ToWeixinArticle toWa = new ToWeixinArticle();
			toWa.setTitle(product.getTitle());
			toWa.setThumbnail(product.getThumb());
			toWa.setDescription(product.getIntro());
			toWa.setUrl(""); //TODO 产品链接待定
			articles.add(toWa);
		}

		//默认加入 查看更多的记录
		//String readpath= context.getServletContext().getContextPath();
		String url = createIndexUrl(uid);			
		ToWeixinArticle toWa = new ToWeixinArticle();
		toWa.setTitle("显示更多");
		toWa.setUrl(url);
		articles.add(toWa);
		
		ToWeixinArticles tas = new ToWeixinArticles();
		tas.setArticles(articles);
		msg.setArticleCount(articles.size());
		msg.setArticles(tas);
		return msg;
	}

	/**
	 * 生成产品首页连接
	 * @param uid
	 * @return
	 */
	private String createIndexUrl(String uid) {
		MobileResource mResource = mobileService.findByBizCodeAndRole(MobileResource.BIZ_product, MobileResource.ROLE_NONE);
	    String target = mResource.getTarget();
		return mobileUrl+target+"?uid="+uid;
	}

}
