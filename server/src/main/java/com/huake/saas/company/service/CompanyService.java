package com.huake.saas.company.service;

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

import com.huake.base.ServiceException;
import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Compress;
import com.huake.saas.company.entity.Company;
import com.huake.saas.company.entity.CompanyCategory;
import com.huake.saas.company.entity.CompanyPictures;
import com.huake.saas.company.repository.CompanyDao;
import com.huake.saas.company.repository.CompanyPicturesDao;
import com.huake.saas.images.ImagesUploadService;
import com.huake.saas.images.Photo;
//Spring Bean的标识.
@Component
//类中所有public函数都纳入事务管理的标识.
@Transactional
public class CompanyService {
	public final static String IMAGES_COMPANY_DIR = "company";

	private static Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private CompanyPicturesDao companyPicturesDao;
	
	@Autowired
	private ImagesUploadService imagesUploadService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CompanyCategoryService companyCategoryService;
	
	public Company getCompany(Long id) {
		return companyDao.findOne(id);
	}
	
	public Company findByUid(String uid){
		return companyDao.findByUid(uid);
	}
	
	public void saveCompany(Company entity) {
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		entity.setCrtDate(new Date());
		companyDao.save(entity);
	}
	
	public void updateCompany(Company entity) {
		long id = entity.getId();
		Company oldCompany = getCompany(id);
		entity.setCrtDate(oldCompany.getCrtDate());
		entity.setUpDate(new Date());
		entity.setStatus(oldCompany.getStatus());
		companyDao.save(entity);
	}
	

	public Company deleteCompany(Long id) {
		Company company = companyDao.findOne(id);
		company.setStatus(BaseEntity.STATUS_INVALIDE);
		companyDao.save(company);
		return company;
	}

	public List<Company> getAllCompany() {
		return (List<Company>) companyDao.findAll();
	}

	/**
	 * 后台
	 * @param userId
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Company> getUserCompany(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Company> spec = buildSpecification(userId, searchParams);

		return companyDao.findAll(spec, pageRequest);
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
	public Page<Company> getUserProductApi(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Company> spec = buildSpecificationApi(userId, searchParams);
		return companyDao.findAll(spec, pageRequest);
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Company> buildSpecificationApi(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, userId));
		return DynamicSpecifications.bySearchFilter(filters.values(), Company.class);
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
	private Specification<Company> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		if(userId!=null&&!"".equals(userId)){
			User user = accountService.getUser(userId);
			if(!User.USER_ROLE_ADMIN.equals(user.getRoles())){
				filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));	
			}
		}
		return DynamicSpecifications.bySearchFilter(filters.values(), Company.class);
	}
	

   
	/**
	 * 企业详情
	 * @param product
	 * @return
	 */
   public Company savaCompanyDetail(Company company){
	   List<CompanyPictures> pictures = getPictures(company.getId().intValue());
	   if(null!=pictures){
		   company.setPictures(pictures);
	   }
		long categoryId = company.getCategoryId();
		CompanyCategory category = companyCategoryService
				.getCompanyCategory(categoryId);
		company.setCategory(category.getName());
	   return company;
   } 
	
	
	/**
	   * 列表详情
	   * @param products
	   * @return
	   */
	 public List<Company> getCompanyDetailsByCompanys(List<Company> companys){
	     for(Company company:companys){
	  	   List<CompanyPictures> pictures = getPictures(company.getId().intValue());
		   if(null!=pictures){
			   company.setPictures(pictures);
		   }
			long categoryId = company.getCategoryId();
			CompanyCategory category = companyCategoryService
					.getCompanyCategory(categoryId);
			company.setCategory(category.getName());
	     }
		  return companys;
	 }
	 
  
  /**
   * 保存图片集
   * @param category
   * @param url
   * @param newsid
   * @param uid
   */
  public void savaCompanyPictures(String url,Long companyId){
	  CompanyPictures pic = new CompanyPictures();
	  pic.setCrtDate(new Date());
	  pic.setCompanyId(companyId.intValue());
	  pic.setUrl(url);
	  companyPicturesDao.save(pic);
  }
  
  /**
   * 查询企业关联图片
   * @param newsid
   * @return
   */
  public List<CompanyPictures> getPictures(int newsid){
	return companyPicturesDao.findCompanyPicturesByCompanyId(newsid);
  }
  
  /**
   * 删除图片
   * @param id
   */
  public void deletePicture(long id){
	  companyPicturesDao.delete(id);
  }

	/**
	 * 修改名字
	 * @param id
	 * @param title
	 */
	public void changeName(Long id,String title){
		Company company = companyDao.findOne(id);
		company.setTitle(title);
		company.setUpDate(new Date());
		companyDao.save(company);
	}
	
	/**
	 * 保存图片，进行必要尺寸裁减
	 * @param file
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<Photo> uploadPic(CommonsMultipartFile file, final MultipartHttpServletRequest request) throws ServiceException{
		if (ServletFileUpload.isMultipartContent(request)) {
			logger.debug("start process image upload...");
			Compress smallCompress  = new Compress(300, 150, 1, Constants.PHOTO_SMALL);
			Compress thumbCompress = new Compress(60, 60, 1, Constants.PHOTO_THUMBNAIL);
			List<Photo> photos = imagesUploadService.saveImage(file, IMAGES_COMPANY_DIR, thumbCompress, smallCompress);
			return photos;
		}
		return null;
	}
	
	/**
	 * 保存图片，进行必要尺寸裁减
	 * @param file
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<Photo> uploadPhotos(CommonsMultipartFile file, final MultipartHttpServletRequest request) throws ServiceException{
		if (ServletFileUpload.isMultipartContent(request)) {
			logger.debug("start process image upload...");
			Compress smallCompress  = new Compress(300, 150, 1, Constants.PHOTO_SMALL);
			List<Photo> photos = imagesUploadService.saveImage(file, IMAGES_COMPANY_DIR, smallCompress);
			return photos;
		}
		return null;
	}
}
