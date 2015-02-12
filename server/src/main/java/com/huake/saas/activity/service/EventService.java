package com.huake.saas.activity.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.huake.base.ServiceException;
import com.huake.saas.activity.entity.Event;
import com.huake.saas.activity.entity.EventJoin;
import com.huake.saas.activity.entity.EventPhoto;
import com.huake.saas.activity.repository.EventDao;
import com.huake.saas.activity.repository.EventJoinDao;
import com.huake.saas.activity.repository.EventPhotoDao;
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.Compress;
import com.huake.saas.images.ImagesUploadService;
import com.huake.saas.images.Photo;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

/**
 * 租户发起活动服务逻辑
 * @author laidingqing
 *
 */
@Component
@Transactional
public class EventService {
	
	
	public final static String IMAGES_EVENT_POSTER_DIR = "events";

	private static Logger logger = LoggerFactory.getLogger(EventService.class);
	
	@Autowired
	private EventDao eventDao;
	
	@Autowired
	private EventJoinDao eventJoinDao;
	
	@Autowired
	private EventPhotoDao eventPhotoDao;
	
	@Autowired
	private TenancyUserService userService; 

	@Autowired
	private ImagesUploadService imagesUploadService;
	
	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;
	
	/**
	 * 通过ID查询对象
	 * @param id
	 * @return
	 */
	public Event findById(Long id){
		return eventDao.findOne(id);
	}
	
	/**
	 * 分页查询活动
	 * @param uid 租户编号
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Event> getAllEventsByUid(Long uid, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Event> spec = buildSpecification(uid, searchParams);
		return eventDao.findAll(spec, pageRequest);
	}
	
	/**
	 * 分页查询活动报名信息列表
	 * @param eventId 活动编号
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<EventJoin> getAllEventJoins(Long eventId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType){
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("eventId", new SearchFilter("eventId", Operator.EQ, eventId));
		Specification<EventJoin> spec = DynamicSpecifications.bySearchFilter(filters.values(), EventJoin.class);
		return eventJoinDao.findAll(spec, pageRequest);
	}
	
	
	public void partake(EventJoin join){
		EventJoin joinOld = eventJoinDao.findByEventIdAndMid(join.getEventId(), join.getMid());
		if(joinOld != null){
			return;
		}
		
		eventJoinDao.save(join);
		Event event = eventDao.findOne(join.getEventId());
		if(join.getType().equals(EventJoin.TYPE_JOIN))
		{
			event.setDoCount(event.getDoCount()+1);
		}
		event.setWishCount(event.getWishCount()+1);	
		eventDao.save(event);
	}
	
	/**
	 * 新增活动
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	public Event createEvent(Event event) throws ServiceException{
		event.setCrtDate(new Date());
		event.setStatus(Constants.STATUS_VALID);
		eventDao.save(event);
		return event;
	}
	/**
	 * 修改活动
	 * @param event
	 */
	public void update(Event event) {
		event.setUptDate(new Date());
		eventDao.save(event);
	}
	
	/**
	 * 删除活动
	 * @param id
	 */
	public void del(Long id){
		Event event = eventDao.findOne(id);
		event.setStatus(Constants.STATUS_INVALID);
		eventDao.save(event);
	}
	
	
	/**
	 * 保存活动图片
	 * @param photos
	 * @param event
	 */
	public void saveEventImages(List<Photo> photos, Event event){
		Assert.notNull(event);
		Assert.notNull(event.getId());
		for(Photo photo : photos){
			EventPhoto eventPhoto = new EventPhoto();
			eventPhoto.setFilePath(imgPath+photo.getFilePath());
			eventPhoto.setImgType(photo.getImgType());
			eventPhoto.setEventId(event.getId());
			eventPhotoDao.save(eventPhoto);
			if(photo.getImgType().equals("thumb"))
			{
				event.setPoster(imgPath+photo.getFilePath());
				eventDao.save(event);
			}
		}
	}
	
	/**
	 * 保存海报，进行必要尺寸裁减
	 * @param file
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<Photo> uploadEventPoster(CommonsMultipartFile file, final MultipartHttpServletRequest request) throws ServiceException{
		if (ServletFileUpload.isMultipartContent(request)) {
			logger.debug("start process image upload...");
			Compress thumbCompress = new Compress(300, 150, 1, Constants.PHOTO_THUMBNAIL);
			Compress smallCompress = new Compress(60, 60, 1, Constants.PHOTO_SMALL);
			List<Photo> photos = imagesUploadService.saveImage(file, IMAGES_EVENT_POSTER_DIR, thumbCompress, smallCompress);
			return photos;
		}
		return null;
	}
	
	
	
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("crtDate".equals(sortType)) {
			sort = new Sort(Direction.ASC, "crtDate");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Event> buildSpecification(Long uid, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status", new SearchFilter("status", Operator.EQ, Constants.STATUS_VALID));
		Specification<Event> spec = DynamicSpecifications.bySearchFilter(filters.values(), Event.class);
		return spec;
	}

	/**
	 * 根据活动id和会员id查找 活动参与信息
	 * @param eventId 活动id
	 * @param mid 会员id (App用户id)
	 * @return
	 */
	public EventJoin findByEventIdAndMid(Long eventId,Long mid){
		return eventJoinDao.findByEventIdAndMid(eventId, mid);
	}
	
	/**
	 * 取消报名
	 * @param eventJoinId
	 */
	public void cancel(Long eventId,Long userId){
		EventJoin eventJoin = eventJoinDao.findByEventIdAndMid(eventId, userId);
		
		if(eventJoin != null){
			Event event = eventDao.findOne(eventJoin.getEventId());
			event.setDoCount(event.getDoCount() - 1);
			eventDao.save(event);
			eventJoinDao.delete(eventJoin);
		}
	}
	/**
	 * 根据租户id和用户id查找活动用户的活动
	 * @param uid
	 * @param mid
	 * @return
	 */
	public List<Event> findByUidAndMid(Long uid,Long mid){
		List<EventJoin> eventJoins = eventJoinDao.findByUidAndMid(uid, mid);
		List<Event> events = new ArrayList<Event>();
		for(EventJoin eventJoin : eventJoins){
			events.add(eventDao.findOne(eventJoin.getEventId()));
		}
		return events;
	}
	/**
	 * 查询活动报名人员
	 * @return
	 */
	public List<TenancyUser> findEntered(Long eventId){
		List<EventJoin> eventJoins = eventJoinDao.findByEventId(eventId);
		List<TenancyUser> users = new ArrayList<TenancyUser>();
		if(eventJoins != null){
			for(EventJoin join : eventJoins){
				TenancyUser user = userService.findById(join.getMid());
				user.setAddressList(userService.findUserAddressList(user.getId()));
				users.add(user);
			}
		}
		return users;
	}

	/**
	 * 查询用户是否报名
	 * @param memberId
	 * @param eventId
	 * @return
	 */
	public String findSingUp(Long memberId,Long eventId) {
		TenancyUser user = userService.findById(memberId);
		if(user == null)
			return "loginFalse";
		
		EventJoin eventJoin = eventJoinDao.findByEventIdAndMid(eventId, memberId);
		if(eventJoin == null )
		{
			return "joinFalse";
		}	
		else
		{
			if(eventJoin.getType().equals(EventJoin.TYPE_JOIN))
				return "joinTrue";
			if(eventJoin.getType().equals(EventJoin.TYPE_INTEREST))
				return "joinFalse";				
		}
		
		return null;
}
	/**
	 * 查询活动报名信息
	 * @param eventId
	 * @return
	 */
	public List<EventJoin> findEventJoins(Long eventId){
		return eventJoinDao.findByEventId(eventId);
	}
	

}
