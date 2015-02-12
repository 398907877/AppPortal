package com.huake.saas.membercard.service;

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
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Compress;
import com.huake.saas.images.ImagesUploadService;
import com.huake.saas.images.Photo;
import com.huake.saas.membercard.entity.CardPictures;
import com.huake.saas.membercard.entity.Mcard;
import com.huake.saas.membercard.repository.CardPicturesDao;
import com.huake.saas.membercard.repository.McardDao;


/**
 * for the member card logical handler
 * @author chen weirong
 *
 */

@Component
@Transactional
public class McardService {

	private static Logger logger = LoggerFactory.getLogger(McardService.class);
	
	public static final String IMAGE_MEMBER_CARD_PIC_DIR = "mcard";
	
	@Autowired
	private McardDao mcardDao;
	
	@Autowired
	private ImagesUploadService imagesUploadService;
	
	@Autowired
	private CardPicturesDao cardPicturesDao; 
	
	/**
	 * get object by ID
	 */
	public Mcard findMcard(Long id)
	{
		return mcardDao.findOne(id);
	}
	
	public void saveMcard(Mcard entity) {
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		entity.setCrtDate(new Date());
		entity.setUpDate(new Date());
		mcardDao.save(entity);
	}
	
	public void updateMcard(Mcard entity){
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		entity.setUpDate(new Date());
		mcardDao.save(entity);
	}

	public Mcard deleteMcard(Long id) {
		Mcard mcard = mcardDao.findOne(id);
		mcard.setStatus(BaseEntity.STATUS_INVALIDE);
		mcardDao.save(mcard);
		return mcard;
	}

	public List<Mcard> getAllMcard() {
		return (List<Mcard>) mcardDao.findAll();
	}	
	
	/**
	 * query by page
	 * @param userId
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Mcard> getAllMemberCards(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Mcard> spec = buildSpecificationApi(userId, searchParams);
		Page<Mcard> mcards= mcardDao.findAll(spec, pageRequest);
		return mcards;
	}
	
	
	
	
	//upload for the logo image
	public List<Photo> uploadMcardPic(CommonsMultipartFile file, final MultipartHttpServletRequest request) throws ServiceException{
		if (ServletFileUpload.isMultipartContent(request)) {
			logger.debug("start process image upload...");
			Compress smallCompress = new Compress(300, 300, 1, Constants.PHOTO_SMALL);
			Compress thumbCompress = new Compress(50, 50, 1, Constants.PHOTO_THUMBNAIL);
			List<Photo> photos = imagesUploadService.saveImage(file, IMAGE_MEMBER_CARD_PIC_DIR,smallCompress, thumbCompress);
			return photos;
		}
		return null;
	}	
	
	/**
	 * create the request for page.
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
	private Specification<Mcard> buildSpecificationApi(Long uid, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		return DynamicSpecifications.bySearchFilter(filters.values(), Mcard.class);
	}

	public void savaCardPictures(String filePath, Long cardId) {
		  CardPictures pic = new CardPictures();
		  pic.setCrtDate(new Date());
		  pic.setCardId(cardId.intValue());
		  pic.setUrl(filePath);
		  cardPicturesDao.save(pic);
		
	}

	public CardPictures getPictures(int cardId) {
		// TODO Auto-generated method stub
		return cardPicturesDao.findCardPicturesByCardId(cardId);
	}

	public void deletePicture(Long id) {
		// TODO Auto-generated method stub
		cardPicturesDao.delete(id);
	}	
}
