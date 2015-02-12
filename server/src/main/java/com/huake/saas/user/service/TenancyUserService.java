package com.huake.saas.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import com.huake.saas.addresslist.entity.AddressList;
import com.huake.saas.addresslist.repository.AddressListDao;
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.Compress;
import com.huake.saas.images.ImagesUploadService;
import com.huake.saas.images.Photo;
import com.huake.saas.invitation.entity.NoPwdUser;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.repository.TenancyUserDao;
import com.huake.saas.util.PassWordMd5;

/**
 * 租户会员服务接口
 * @author laidingqing
 *
 */
@Component
@Transactional
public class TenancyUserService {

	public static final String IMAGE_AVATAR_PIC_DIR = "avatar";
	@Autowired
	private ImagesUploadService uploadService;
	@Autowired
	private TenancyUserDao tenancyUserDao;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AddressListDao addDao;
	
	/**
	 * 注册会员登录,TODO
	 * @return
	 * @throws ServiceException
	 */
	public TenancyUser signin(String loginName, String password, String uid) throws ServiceException{
		
		return null;
	}
	
	public NoPwdUser convertNoPwdUser(TenancyUser fuser){
		NoPwdUser user = new NoPwdUser();
		user.setId(fuser.getId());
		user.setCreateDate(fuser.getCreateDate());
		user.setLoginname(fuser.getLoginname());
		user.setName(fuser.getName());
		user.setUid(fuser.getId());
		return user;
	}
	
	
	/**
	 * 根据条件分页查询注册会员用户
	 * @param userId
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<TenancyUser> findByCondition(Long userId,
			Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Specification<TenancyUser> spec = buildSpecification(userId, searchParams);
		return tenancyUserDao.findAll(spec, pageRequest);
	}
	
	/**
	 * 根据状态和租户编号查询注册会员列表
	 * @param status
	 * @param uid
	 * @return
	 */
	public List<TenancyUser> findTenancyUsersByStatusAndUid(int status,long uid){
		return tenancyUserDao.findTenancyUsersByStatusAndUid(status,uid);
	}
	
	/**
	 * 根据状态查询租户会员注册列表，无意义，可消毁
	 * @param status
	 * @return
	 */
	@Deprecated
	public List<TenancyUser> findTenancyUsersByStatus(int status){
		return tenancyUserDao.findTenancyUsersByStatus(status);
	}
	
	/**
	 * 查询全部租户会员
	 * @return
	 */
	public List<TenancyUser> findAllUsers(){
		return (List<TenancyUser>)tenancyUserDao.findAll();
	}
	/**
	 * 根据编号查询注册会员信息
	 * @param id
	 * @return
	 */
	public TenancyUser findById(long id){
		return tenancyUserDao.findOne(id);
	}
	
	
	public void delById(long id){
		TenancyUser add = this.findById(id);
		add.setStatus(TenancyUser.del);
		tenancyUserDao.save(add);
	}
	
	
	public void activateById(long id){
		TenancyUser add = this.findById(id);
		add.setStatus(TenancyUser.normal);
		tenancyUserDao.save(add);
	}
	
	/**
	 * 修改用户 修改昵称状态等
	 * @param user
	 */
	public void update(TenancyUser user){
		TenancyUser user1 = this.findById(user.getId());
		user1.setName(user.getName());
		user1.setNickname(user.getNickname());
		user1.setSex(user.getSex());
		user1.setMobile(user.getMobile());
		user1.setBirth(user.getBirth());
		user1.setStatus(user.getStatus());
		tenancyUserDao.save(user1);
	}
	
	public void savePwd(TenancyUser user){
		TenancyUser user1 = this.findById(user.getId());
		String passWord = PassWordMd5.getPassWord(user.getPassword());
		user1.setPassword(passWord);
		tenancyUserDao.save(user1);
	}
	
	/**
	 * 更新注册会员信息
	 * @param user
	 */
	public void updateTenancyUser(TenancyUser user){
		user.setUptDate(new Date());
		tenancyUserDao.save(user);
	}
	
	/**
	 * 通qqID查询用户(状态有效)
	 * @param trim
	 * @return
	 */
	public TenancyUser findByQqId(String trim) {
		// TODO Auto-generated method stub
		return tenancyUserDao.findByQqIdAndStatus(trim,TenancyUser.normal);
	}

	/**
	 * 通过新浪ID查询用户(状态有效)
	 * @param trim
	 * @return
	 */
	public TenancyUser findBySinaId(String trim) {
		// TODO Auto-generated method stub
		return tenancyUserDao.findBySinaIdAndStatus(trim,TenancyUser.normal);
	}
	
	/**
	 * 同一个uid的用户数
	 * @param uid
	 * @return
	 */
	public int countTenancyUser(long uid){
		return tenancyUserDao.count(uid);
	}
	
	public TenancyUser save(TenancyUser user){
		user.setCreateDate(new Date());
		user.setStatus(TenancyUser.normal);
	   //String passWord = PassWordMd5.getPassWord(user.getPassword());
		return tenancyUserDao.save(user);
	}
	
	
	public TenancyUser findByLoginnameAndUid(String loginName, Long uid){
         return tenancyUserDao.findByLoginnameAndUid(loginName,uid);
	}
	
	public boolean isExist(String loginName, Long uid){
		TenancyUser user = tenancyUserDao.findByLoginnameAndUid(loginName,uid);
		return user == null ? false : true;
		
	}
	
	public void updatePwd(TenancyUser user) {
            TenancyUser user1 = tenancyUserDao.findOne(user.getId());
            user1.setPassword(user.getPassword());
            tenancyUserDao.save(user1);
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
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * @param userId
	 */
	private Specification<TenancyUser> buildSpecification(Long userId,
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
	
		if (userId != null && !"".equals(userId)) {
			User user = accountService.getUser(userId);
			if (!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())) {
				filters.put("uid", new SearchFilter(
						"uid", Operator.EQ, user.getUid()));
			}
		}
		Specification<TenancyUser> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), TenancyUser.class);
		return spec;
	}

	/**
	 * 客户端更新个人信息
	 * @param user
	 */
	public TenancyUser updateUserByClient(TenancyUser userNew){
		TenancyUser user = findById(userNew.getId());
		AddressList userAdd = userNew.getAddressList();
		if(null!=userNew.getName()&&!"".equals(userNew.getName())){
			user.setName(userNew.getName());
		}
		if(null!=userNew.getMobile()&&!"".equals(userNew.getMobile())){
			userAdd.setTel(userNew.getMobile());
			user.setMobile(userNew.getMobile());
		}
		if(null!=userNew.getBirth()&&!"".equals(userNew.getBirth())){
			user.setBirth(userNew.getBirth());
		}
		if(null!=userNew.getNickname()&&!"".equals(userNew.getNickname())){
			user.setNickname(userNew.getNickname());
		}
		user.setLastLoginTime(new Date());
		AddressList addressList = addDao.findByUserId(user.getId());
		if(addressList == null){
			userAdd.setName(user.getName());
			userAdd.setAvatar(user.getAvatar());
			userAdd.setPublicEmail(AddressList.NO_PUBLIC);
			userAdd.setPublicPhone(AddressList.PUBLIC);
			userAdd.setPublicTel(AddressList.NO_PUBLIC);
			userAdd.setPublicEmail(AddressList.NO_PUBLIC);
			userAdd.setStatus(AddressList.normal);
			userAdd.setCreateDate(new Date());
			userAdd.setUid(user.getUid());
			userAdd.setUserId(user.getId());
			addDao.save(userAdd);
		}else{
			if(null!=userNew.getMobile()&&!"".equals(userNew.getMobile())){
				addressList.setTel(userNew.getMobile());
			}
			addressList.setName(user.getName());
			addressList.setCompanyName(userAdd.getCompanyName());
			addressList.setOfficePhone(userAdd.getOfficePhone());
			addressList.setTel(userAdd.getTel());
			addressList.setPosition(userAdd.getPosition());
			addressList.setDept(userAdd.getDept());
			addDao.save(addressList);
		}
		updateTenancyUser(user);
		
		return user;
	}
	/**
	 * 查询用户对应通讯录
	 * @param userId
	 * @return
	 */
	public AddressList findUserAddressList(Long userId){
		return addDao.findByUserId(userId);
	}
	public void saveAddressList(AddressList addressList){
		addDao.save(addressList);
	}
	/**
	 * 根据真实姓名 手机号  租户id查找用户
	 * @param name
	 * @param uid
	 * @param mobile
	 */
	public TenancyUser findUserByCodition(String name,Long uid,String mobile,String loginname){
		return tenancyUserDao.findByCondition(uid, name, TenancyUser.normal, mobile,loginname);
	}
	
	/**
	 * 上传头像图片  对图片 进行裁剪
	 * @param file
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<Photo> uploadPic(CommonsMultipartFile file, final MultipartHttpServletRequest request) throws ServiceException{
		if (ServletFileUpload.isMultipartContent(request)) {
			Compress smallCompress = new Compress(300, 150, 1, Constants.PHOTO_SMALL);
			Compress thumbCompress = new Compress(60, 60, 1, Constants.PHOTO_THUMBNAIL);
			List<Photo> photos = uploadService.saveImage(file, IMAGE_AVATAR_PIC_DIR, smallCompress,thumbCompress);
			return photos;
		}
		return null;
	}
	/**
	 * 根据openid查找会员用户
	 * @param openid
	 * @return
	 */
	public TenancyUser findByOpenid(String openid){
		if(openid == null){
			return null;
		}
		return tenancyUserDao.findByOpenid(openid);
	}
}
