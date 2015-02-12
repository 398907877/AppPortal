package com.huake.saas.product.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.web.controller.BaseMobileController;
import com.huake.saas.product.entity.Category;
import com.huake.saas.product.entity.Product;
import com.huake.saas.product.entity.ProductPictures;
import com.huake.saas.product.service.CategoryService;
import com.huake.saas.product.service.ProductService;

@Controller
@RequestMapping(value = "/m/product")
public class ProductMobileController extends BaseMobileController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	/**
	 * 产品首页
	 * @param pageNum
	 * @param UID
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(@RequestParam("uid")  final String UID, 
			@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum)throws AppBizException{
		ModelAndView mav = new ModelAndView();
		if(UID == null){
			throw new AppBizException("无效的访问");
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Long uid = Long.parseLong(UID); 
		Page<Product> products = productService.getUserProduct(uid,
				searchParams, pageNum, 5, "auto");
		for (Product product : products) {
			String str = product.getTitle();
			if(product.getTitle().length()>15){
				str = str.substring(0,15)+"...";
			}
			product.setTitle(str);
			String str1 = product.getIntro();
			if(product.getIntro().length()>25){
				str1 = str1.substring(0,25)+"...";
			}
			product.setIntro(str1);
		}
		mav.addObject("UID", UID);
		mav.addObject("products", products);
		mav.setViewName("mobile/product/index");
		return mav;
	}

	
	/**
	 * 分页获取产品列表。
	 * @param UID
	 * @param pageNum
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<Product> list(@RequestParam("UID")  final String UID, 
	@RequestParam(value = "pageNum",defaultValue="2", required = false) Integer pageNum)throws AppBizException{
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Long uid = Long.parseLong(UID); 
		List<Product> products = productService.getUserProduct(uid,
				searchParams, pageNum, 5, "auto").getContent();
		for (Product product : products) {
			String str = product.getTitle();
			if(product.getTitle().length()>15){
				str = str.substring(0,15)+"...";
			}
			product.setTitle(str);
			String str1 = product.getIntro();
			if(product.getIntro().length()>25){
				str1 = str1.substring(0,25)+"...";
			}
			product.setIntro(str1);
		}
		return products;
	}
	
	/**
	 * 产品详细页面
	 * @param productId
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/{productId}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable final Long productId) throws AppBizException{
		Product product = productService.getProduct(productId);
		if(null == product){
			throw new AppBizException("出现错误了", "Object No found.");
		}
		long categoryId = product.getCategoryId();
		Category category = categoryService.getCategory(categoryId);
		List<ProductPictures> pictures = productService.getPictures(product.getId().intValue());
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("product", product);
		mav.addObject("pictures", pictures);
		mav.addObject("category", category);
		mav.setViewName("mobile/product/show");
		return mav;
	}
}
