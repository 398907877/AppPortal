package com.huake.saas.product.web.api.v1;

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
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.product.entity.Category;
import com.huake.saas.product.entity.Product;

import com.huake.saas.product.entity.ProductPictures;
import com.huake.saas.product.service.CategoryService;
import com.huake.saas.product.service.ProductService;

@Controller
@RequestMapping(value = "/api/v1/product")
public class ProductApiController extends BaseApiController{
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	

   /**
    * 产品列表
    * @param categoryId
    * @param first
    * @param max
    * @return
    * @throws Exception
    */
	@RequestMapping(value = "/list", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,Object> getProducts(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestParam(value = "categoryId", required = false) String categoryId,
			@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum){
	   List<Category> category = categoryService.getAllCategorys(BaseEntity.STATUS_VALIDE,uid);
       Map<String, Object> searchParams = new HashMap<String, Object>();
       if(null==categoryId||"".equals(categoryId)){
    	   if(null!=category&&category.size()>0){
    		   categoryId = category.get(0).getId().toString();
    	   }
       }
       searchParams.put("EQ_categoryId", categoryId);
       searchParams.put("EQ_status", BaseEntity.STATUS_VALIDE);
       List<Product> products = productService.getUserProductApi(uid,
				searchParams, pageNum, BaseEntity.DATE_MAX, BaseEntity.PAGE_CRTDATE_DESC).getContent();

       
       Map<String,Object> map = new HashMap<String, Object>();
       map.put("newsCategory", category);
       map.put("news", products);
       
       return map;
       }
      
	
	
	/**
	 * 产品详情
	 * @param id(产品Id)
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value=HttpStatus.OK)
	public Product eventDetail(@PathVariable final Long id,@RequestParam(value = "uid", required = true) Long uid){
		Product product = productService.getProduct(id);
		product.setPictures(productService.getPictures(id.intValue()));
		long categoryId = product.getCategoryId();
		Category category = categoryService.getCategory(categoryId);
		product.setCategory(category.getName());
		return product;
	}
}
