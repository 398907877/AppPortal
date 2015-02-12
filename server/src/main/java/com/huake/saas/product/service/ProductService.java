package com.huake.saas.product.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.huake.saas.account.service.AccountService;
import com.huake.saas.activity.service.EventService;
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Compress;
import com.huake.saas.base.service.ServiceException;
import com.huake.saas.images.ImagesUploadService;
import com.huake.saas.images.Photo;
import com.huake.saas.product.entity.Product;
import com.huake.saas.product.entity.ProductFields;
import com.huake.saas.product.entity.ProductPictures;
import com.huake.saas.product.repository.ProductDao;
import com.huake.saas.product.repository.ProductFieldsDao;
import com.huake.saas.product.repository.ProductPicturesDao;

//Spring Bean的标识.
@Component
//类中所有public函数都纳入事务管理的标识.
@Transactional
public class ProductService {
	private static Logger logger = LoggerFactory.getLogger(EventService.class);
	
	public static final String IMAGE_PRODUCT_PIC_DIR = "product";
	
	@Autowired
	private ImagesUploadService imagesUploadService;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductPicturesDao productPicturesDao;
	
	@Autowired
	private ProductFieldsDao productFieldsDao;
	
    @Autowired
	private AccountService accountService;
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 通过ID查询产品
     * @param id
     * @return
     */
	public Product getProduct(Long id) {
		return productDao.findOne(id);
	}

	/**
	 * 保存产品
	 * @param entity
	 */
	public void saveProduct(Product entity) {
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		entity.setCrtDate(new Date());
		entity.setUpDate(new Date());
		productDao.save(entity);
	}
	/**
	 * 修改产品 
	 * @param entity
	 */
	public void updateProduct(Product entity){
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		entity.setUpDate(new Date());
		productDao.save(entity);
	}

	/**
	 * 删除（伪）
	 * @param id
	 * @return
	 */
	public Product deleteProduct(Long id) {
		Product product = productDao.findOne(id);
		product.setStatus(BaseEntity.STATUS_INVALIDE);
		productDao.save(product);
		return product;
	}

	/**
	 * 查询出所有产品（无限制）
	 * @return
	 */
	public List<Product> getAllProduct() {
		return (List<Product>) productDao.findAll();
	}

	/**
	 * 后台查询列表
	 * @param uid 租户id 过滤
	 * @param searchParams 过滤参数
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Product> getUserProduct(Long uid, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Product> spec = buildSpecification(uid, searchParams);
		return productDao.findAll(spec, pageRequest);
	}

	
	/**
	 * API查询列表
	 * @param userId
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Product> getUserProductApi(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Product> spec = buildSpecificationApi(userId, searchParams);
		return productDao.findAll(spec, pageRequest);
	}
	
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Product> buildSpecificationApi(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, userId));
		return DynamicSpecifications.bySearchFilter(filters.values(), Product.class);
	}
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if (BaseEntity.PAGE_CRTDATE_DESC.equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if (BaseEntity.PAGE_CRTDATE_ASC.equals(sortType)) {
			sort = new Sort(Direction.ASC, BaseEntity.PAGE_CRTDATE_ASC);
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}	
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Product> buildSpecification(Long uid, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		/*if(userId!=null&&!"".equals(userId)){
			User user = accountService.getUser(userId);
			if(!User.USER_ROLE_ADMIN.equals(user.getRoles())){
				filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));
			}
		}*/
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status", new SearchFilter("status", Operator.EQ, BaseEntity.STATUS_VALIDE));
		return DynamicSpecifications.bySearchFilter(filters.values(), Product.class);
	}
	
	
	/**
	 * 修改名字
	 * @param id
	 * @param title
	 */
	public void changeName(Long id,String title){
		Product product = productDao.findOne(id);
		product.setTitle(title);
		product.setUpDate(new Date());
		productDao.save(product);
	}
	
	

  

  
  /**
   * 保存图片集
   * @param category
   * @param url
   * @param newsid
   * @param uid
   */
  public void savaProductPictures(String url,Long productId){
	  ProductPictures pic = new ProductPictures();
	  pic.setCrtDate(new Date());
	  pic.setProductId(productId.intValue());
	  pic.setUrl(url);
	  productPicturesDao.save(pic);
  }
  
  /**
   * 查询企业关联图片
   * @param newsid
   * @return
   */
  public List<ProductPictures> getPictures(int newsid){
	return productPicturesDao.findProductPicturesByProductId(newsid);
  }
  
  /**
   * 删除图片
   * @param id
   */
  public void deletePicture(long id){
	  productPicturesDao.delete(id);
  }

	/**
	 * 上传图片
	 * @param file
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<Photo> uploadProductPic(CommonsMultipartFile file, final MultipartHttpServletRequest request) throws ServiceException{
		if (ServletFileUpload.isMultipartContent(request)) {
			logger.debug("start process image upload...");
			Compress smallCompress = new Compress(300, 150, 1, Constants.PHOTO_SMALL);
			Compress thumbCompress = new Compress(60, 60, 1, Constants.PHOTO_THUMBNAIL);
			List<Photo> photos = imagesUploadService.saveImage(file, IMAGE_PRODUCT_PIC_DIR,smallCompress, thumbCompress);
			return photos;
		}
		return null;
	}
	
	/**
	 * 根据产品id查找产品拓展属性
	 * @param productId
	 * @return
	 */
	public List<ProductFields> findByProductId(Long productId){
		return productFieldsDao.findByProductId(productId);
	}
	/**
	 * 保存产品拓展属性
	 * @param fields
	 */
	public void save(List<ProductFields> fields){
		for(ProductFields field:fields){
			productFieldsDao.save(field);
		}
	}
	/**
	 * 根据id删除产品拓展属性
	 * @param id
	 */
	public void deleteField(Long id){
		productFieldsDao.delete(id);
	}
}
