package com.huake.saas.supply.service;

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
import com.huake.saas.company.entity.CompanyCategory;
import com.huake.saas.company.service.CompanyCategoryService;
import com.huake.saas.images.ImagesUploadService;
import com.huake.saas.images.Photo;
import com.huake.saas.supply.entity.SupplyDemand;
import com.huake.saas.supply.entity.SupplyDemandPhoto;
import com.huake.saas.supply.repository.SupplyDemandDao;
import com.huake.saas.supply.repository.SupplyDemandPhotoDao;
/**
 * 供求信息管理Service
 * @author zhongshuhui
 *
 */
//Spring Bean的标识
@Component
//类中public函数都纳入事务管理的标识.
@Transactional
public class SupplyDemandService {
	private static Logger logger = LoggerFactory.getLogger(EventService.class);
	
	public static final String IMAGE_SUPPLYDEMAND_PIC_DIR = "supplyDemand";
	
	@Autowired
	private ImagesUploadService imagesUploadService;
	
	@Autowired
	private AccountService accountService;
	
	private SupplyDemandDao supplyDemandDao;

	@Autowired
	private SupplyDemandPhotoDao supplyDemandPhotoDao;
	@Autowired
	private CompanyCategoryService categoryService;
	

	public SupplyDemand getSupplyDemand(Long id) {
		return supplyDemandDao.findOne(id);
	}

	public void saveSupplyDemand(SupplyDemand entity) {
		entity.setCrtDate(new Date());
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		entity.setUpDate(new Date());
		supplyDemandDao.save(entity);
	}
	
	public void updateSupplyDemand(SupplyDemand supplyDemand){
		supplyDemand.setStatus(BaseEntity.STATUS_VALIDE);
		supplyDemand.setUpDate(new Date());
		supplyDemandDao.save(supplyDemand);
	}

	public SupplyDemand deleteSupplyDemand(Long id) {
		SupplyDemand supplyDemand = supplyDemandDao.findOne(id);
		supplyDemand.setStatus(BaseEntity.STATUS_INVALIDE);
		supplyDemandDao.save(supplyDemand);
		return supplyDemand;
	}

	public List<SupplyDemand> getAllSupplyDemand() {
		return (List<SupplyDemand>) supplyDemandDao.findAll();
	}

	/**
	 * 后台
	 * @param uid
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<SupplyDemand> getUserSupplyDemand(Long uid, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<SupplyDemand> spec = buildSpecification(uid, searchParams);
		return supplyDemandDao.findAll(spec, pageRequest);
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
	public Page<SupplyDemand> getUserSupplyDemandApi(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<SupplyDemand> spec = buildSpecificationApi(userId, searchParams);
		return supplyDemandDao.findAll(spec, pageRequest);
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<SupplyDemand> buildSpecificationApi(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, userId));
		return DynamicSpecifications.bySearchFilter(filters.values(), SupplyDemand.class);
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
	private Specification<SupplyDemand> buildSpecification(Long uid, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		/*if(userId!=null&&!"".equals(userId)){
			User user = accountService.getUser(userId);
			if(!User.USER_ROLE_ADMIN.equals(user.getRoles())){
				filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));	
			}
		}*/
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));	
		return DynamicSpecifications.bySearchFilter(filters.values(), SupplyDemand.class);
	}
		
	  
	  
	    /**
		 * 保存供求信息详情
		 * @param product
		 * @return
		 */
	   public SupplyDemand savaSupplyDemandDetail(SupplyDemand supplyDemand){
	   	   long typeId = supplyDemand.getTypeId();
	   	   CompanyCategory category = categoryService.getCompanyCategory(typeId);
	   	   if(null!=category){
	   		   /*supplyDemand.setCategory(category);*/
	   	    }
		   return supplyDemand;
	   } 
	  
	/**
	 * 修改名字
	 * @param id
	 * @param title
	 */
	public void changeName(Long id,String title){
		SupplyDemand demand = supplyDemandDao.findOne(id);
		demand.setTitle(title);
		demand.setUpDate(new Date());
		supplyDemandDao.save(demand);
	}
	

	@Autowired
	public void setSupplyDemandDao(SupplyDemandDao supplyDemandDao) {
		this.supplyDemandDao = supplyDemandDao;
	}
	
	/**
	 * 上传图片
	 * @param file
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<Photo> uploadPhoto(CommonsMultipartFile file, final MultipartHttpServletRequest request) throws ServiceException{
		if (ServletFileUpload.isMultipartContent(request)) {
			logger.debug("start process image upload...");
			Compress smallCompress = new Compress(300, 150, 1, Constants.PHOTO_SMALL);
			Compress thumbCompress = new Compress(60, 60, 1, Constants.PHOTO_THUMBNAIL);
			List<Photo> photos = imagesUploadService.saveImage(file, IMAGE_SUPPLYDEMAND_PIC_DIR,smallCompress, thumbCompress);
			return photos;
		}
		return null;
	}
	/**
	 * 根据供求信息id查找供求信息图片
	 * @param sid
	 * @return
	 */
	public List<SupplyDemandPhoto> getPictures(Long sid){
		return supplyDemandPhotoDao.findBySupplyDemandIdAndImgType(sid,Constants.PHOTO_SMALL);
	}
	
	/**
	 * 保存图片
	 * 
	 */
	public void savePhoto(SupplyDemandPhoto photo){
		supplyDemandPhotoDao.save(photo);
	}
	
	/**
	 * 删除图片
	 */
	public void deletePhoto(Long id){
		supplyDemandPhotoDao.delete(id);
	}
}
