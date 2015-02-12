package com.huake.saas.addresslist.service;

import java.util.ArrayList;
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
import com.huake.saas.addresslist.entity.AddressList;
import com.huake.saas.addresslist.entity.AddressListApi;
import com.huake.saas.addresslist.entity.AddressListType;
import com.huake.saas.addresslist.repository.AddressListDao;
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.Compress;
import com.huake.saas.images.ImagesUploadService;
import com.huake.saas.images.Photo;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

@Component
@Transactional
public class AddressListService {
	private static Logger logger = LoggerFactory.getLogger(EventService.class);
	public static final String IMAGES_ADDRESS_PIC_DIR = "addressList";
	@Autowired
	private AddressListDao addListDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AddressListTypeService addressListTypeService;
	
	@Autowired
	private TenancyUserService userService;
	
	@Autowired
	private ImagesUploadService imageUploadService;
	
	public Page<AddressList> findByCondition(Long userId,
			Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Specification<AddressList> spec = buildSpecification(userId, searchParams);
		return addListDao.findAll(spec, pageRequest);
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
	public Page<AddressList> getUserAddressList(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<AddressList> spec = buildSpecificationApi(userId, searchParams);
		return addListDao.findAll(spec, pageRequest);
	}
	
	
	public AddressList findById(long id){
		return addListDao.findOne(id);
	}
	
	public List<AddressList> findByDeptId(Long id,Long uid)
	{
		return addListDao.findByDeptIdAndStatusAndUid(id,AddressList.normal,uid);
	}
	/**
	 * 查询所有通讯录
	 * @return
	 */
	public List<AddressList> findAll(Long uid){
		return addListDao.findByStatusAndUid(AddressList.normal,uid);
	}
	public void delById(long id){
		AddressList add = this.findById(id);
		add.setStatus(AddressList.del);
		addListDao.save(add);
	}
	
	public void update(AddressList add){
		AddressList oldAdd = this.findById(add.getId());
		add.setStatus(oldAdd.getStatus());
		if(add.getUserId() != null){
			AddressList userAdd = addListDao.findByUserId(add.getUserId());
			if(userAdd != null){
				userAdd.setUserId(null);
				addListDao.save(userAdd);//保证一条通讯录关联一个app用户
			}
			TenancyUser user = userService.findById(add.getUserId());
			if(user != null){
				user.setEmail(add.getEmail());
				user.setMobile(add.getTel());
				userService.updateTenancyUser(user);
			}
		}
	    addListDao.save(add);
	}
	
	
	public void save(AddressList add){
		add.setCreateDate(new Date());
		add.setStatus(AddressList.normal);
		
		if(add.getUserId() != null){
			AddressList userAdd = addListDao.findByUserId(add.getUserId());
			if(userAdd != null){
				userAdd.setUserId(null);
				addListDao.save(userAdd);//保证一条通讯录关联一个app用户
			}
			TenancyUser user = userService.findById(add.getUserId());
			if(user != null){
				user.setEmail(add.getEmail());
				user.setMobile(add.getTel());
				userService.updateTenancyUser(user);
			}
		}
		addListDao.save(add);
	}
	
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("createDate".equals(sortType)) {
			sort = new Sort(Direction.DESC, "createDate");
		}else if ("crtDate".equals(sortType)) {
			sort = new Sort(Direction.ASC, "createDate");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<AddressList> buildSpecificationApi(Long uid, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		return DynamicSpecifications.bySearchFilter(filters.values(), AddressList.class);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * @param userId
	 */
	private Specification<AddressList> buildSpecification(Long uid,
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
	
		/*if(userId!=null&&!"".equals(userId)){
			User user = accountService.getUser(userId);
			if(!User.USER_ROLE_ADMIN.equals(user.getRoles())){
				filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));
			}
		}*/
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status", new SearchFilter("status", Operator.EQ,
				AddressList.normal));
		Specification<AddressList> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), AddressList.class);
		return spec;
	}
	
	
	public List<AddressListApi> convertAddressListApi(List<AddressList> addressLists){
		List<AddressListApi> addressListApis = new ArrayList<AddressListApi>();
		for(AddressList addressList:addressLists){
			AddressListApi api = new AddressListApi();
			api.setId(addressList.getId());
			api.setName(addressList.getName());
			api.setContent(addressList.getContent());
			api.setCreateDate(addressList.getCreateDate());
			api.setDeptId(addressList.getDeptId());
			api.setPosition(addressList.getPosition());
			api.setOfficePhone(addressList.getOfficePhone());
			api.setTel(addressList.getTel());
			AddressListType addressListType = addressListTypeService.getAddressListType(addressList.getDeptId());
			api.setDeptName(addressListType.getName());
			addressListApis.add(api);
		}
		return addressListApis;
	}
	
	
	public AddressListApi getAddressListApi(AddressList addressList){
			AddressListApi api = new AddressListApi();
			api.setId(addressList.getId());
			api.setName(addressList.getName());
			api.setContent(addressList.getContent());
			api.setCreateDate(addressList.getCreateDate());
			api.setDeptId(addressList.getDeptId());
			api.setPosition(addressList.getPosition());
			api.setAvatar(addressList.getAvatar());
			api.setCompanyName(addressList.getCompanyName());
			api.setAddress(addressList.getAddress());
			api.setDeptName(addressList.getDept());
			api.setTel(addressList.getTel());
			api.setOfficePhone(addressList.getOfficePhone());
			api.setEmail(addressList.getEmail());
			if(addressList.getDeptId() != null){
				AddressListType addressListType = addressListTypeService.getAddressListType(addressList.getDeptId());
				api.setDeptName(addressListType.getName());
			}
		return api;
	}
	
	/**
	 * 上传缩略图
	 * @param file
	 * @param request
	 * @return
	 */
	public List<Photo> uploadThumb(CommonsMultipartFile file, final MultipartHttpServletRequest request){
		if (ServletFileUpload.isMultipartContent(request)) {
			logger.debug("start process image upload...");
			Compress thumbCompress = new Compress(60, 60, 1, Constants.PHOTO_THUMBNAIL);
			Compress smallCompress = new Compress(300, 150, 1, Constants.PHOTO_SMALL);
			List<Photo> photos = imageUploadService.saveImage(file, IMAGES_ADDRESS_PIC_DIR, thumbCompress,smallCompress);
			return photos;
		}
		return null;
	}
}
