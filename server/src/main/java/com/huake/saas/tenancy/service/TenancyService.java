package com.huake.saas.tenancy.service;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
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
import org.springside.modules.cache.memcached.SpyMemcachedClient;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.google.common.collect.Maps;
import com.huake.base.ServiceException;
import com.huake.saas.account.entity.User;
import com.huake.saas.account.repository.UserDao;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.auth.service.AuthService;
import com.huake.saas.auth.service.KeyPairService;
import com.huake.saas.base.MemcachedObjectType;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.entity.TenancyIntro;
import com.huake.saas.tenancy.repository.TenancyDao;
import com.huake.saas.tenancy.repository.TenancyIntroDao;
import com.huake.saas.util.UtilDate;

/**
 * 租户相关服务
 * @author hyl
 * @author laidingqing
 */
@Component
@Transactional
public class TenancyService {
	
	/**
	 * 到期提醒的天数 可在.property文件中修改
	 */
	@Value("#{envProps.day_limit}")
	private int dayLimit;
	
	@Autowired
	private TenancyIntroDao tenancyIntroDao;
	
	@Autowired
	private TenancyDao tenancyDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private KeyPairService keyPairService;
	
	@Autowired	
	private  AuthService authService;
	
	@Autowired(required = false)
	private SpyMemcachedClient memcachedClient;
	
	private final JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
	
	public AccountService getAccountService() {
		return accountService;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	public TenancyDao getTenancyDao() {
		return tenancyDao;
	}
	public void setTenancyDao(TenancyDao tenancyDao) {
		this.tenancyDao = tenancyDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
   public KeyPairService getKeyPairService() {
		return keyPairService;
	}
	public void setKeyPairService(KeyPairService keyPairService) {
		this.keyPairService = keyPairService;
	}
    /**
    * 查询 租户分页合集
    * @param userId 
    * @param searchParams 自定义查询参数，LINK_name
    * @param pageNumber  页数
    * @param pageSize  每页显示条数
    * @param sortType  排序字段
    * @return
    */
	public Page<Tenancy> findTenanciesByCondition( Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType,String deadline) {
		//searchParams.put("EQ_status", Tenancy.NODEL);
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Tenancy> spec = buildSpecification(userId,searchParams,deadline);
		return tenancyDao.findAll(spec, pageRequest);	
	}
	
	
	public List<Tenancy> findAll(Long userId, Map<String, Object> searchParams){
		Specification<Tenancy> spec = buildSpecification(userId,searchParams,"0");
		return tenancyDao.findAll(spec);	
	}
	
	
	/**
	 * 创建租户
	 * TODO 重复检查未做
	 * @param tenancy 租户
	 * @return
	 */
	public Tenancy createTenancy(Tenancy tenancy) {
		String uid = UtilDate.getOrderNum();
		tenancy.setUid(Long.valueOf(uid));
		tenancy.setCrtDate(new Date());
		tenancy.setStatus(Tenancy.NODEL);
		tenancy.setUptDate(new Date());
		tenancy.setAppId(keyPairService.generatePublicKey());
		tenancy.setAppSecret(keyPairService.generatePrivateKey());
		return tenancyDao.save(tenancy);
	}
	
	/**
	 * 创建租户内容介绍
	 * @param intro
	 * @return
	 * @throws ServiceException
	 */
	public TenancyIntro createTenancyIntro(TenancyIntro intro) throws ServiceException{
		Assert.notNull(intro);
		Assert.notNull(intro.getUid());
		Tenancy tenancy = tenancyDao.findOne(intro.getUid());
		if( tenancy == null){
			throw new ServiceException("未发现租户信息");
		}
		tenancyIntroDao.save(intro);
		return intro;
	}
	
	public Tenancy findByUid(long uid){
		return tenancyDao.findByUid(uid);
	}
	/**
	 * 通过 uid 进行查询 租户
	 * @param uid
	 * @return
	 */
	public Tenancy getTenancy(long uid) {
		
		return tenancyDao.findByUid(uid);
	//	if (memcachedClient != null) {
	//		return getTenancyWithMemcached(uid);
	//	} else {
	//		return tenancyDao.findByUid(uid);
	//	}
	}
	
	/**
	 * 通过 id 进行查询 租户
	 * @param uid
	 * @return
	 */
	public Tenancy findById(long id) {
		return tenancyDao.findOne(id);
	
	}
	
	/**
	 * 先访问Memcached, 使用JSON字符串存放对象以节约空间.
	 */
	private Tenancy getTenancyWithMemcached(Long uid) {
		String key = MemcachedObjectType.TENANCY.getPrefix() + uid.toString();
		
		String jsonString = memcachedClient.get(key);
		
		if (jsonString != null) {
			return jsonMapper.fromJson(jsonString, Tenancy.class);
		} else {
			System.out.println("uid"+uid);
			Tenancy tenancy = tenancyDao.findByUid(uid);
			if (tenancy != null) {
				jsonString = jsonMapper.toJson(tenancy);
				memcachedClient.set(key, MemcachedObjectType.TENANCY.getExpiredTime(), jsonString);
			}
			return tenancy;
		}
	}
   /**
    * 更新租户
    * @param tenancy
    * @return
    */
	public Tenancy updateTenancy(Tenancy tenancy) {
		
		
		tenancy.setUptDate(new Date());
		tenancy = tenancyDao.save(tenancy);
		
		return tenancy;
	}
	
	   /**
	    * w租户
	    * @param tenancy
	    * @return
	    */
		public Tenancy modifyTenancy(Tenancy tenancy) {
			Tenancy t1 = tenancyDao.findOne(tenancy.getId());
			t1.setName(tenancy.getName());
			t1.setTel(tenancy.getTel());
			t1.setAddress(tenancy.getAddress());
			t1.setStartDate(tenancy.getStartDate());
			t1.setEndDate(tenancy.getEndDate());
			t1.setLinkName(tenancy.getLinkName());
			t1.setLinkTel(tenancy.getLinkTel());
		    t1.setStatus(tenancy.getStatus());
			
			//用户无效
			if(tenancy.getStatus().equals(Tenancy.DEL))
			{
			   List<User> users = userDao.findByUid(tenancy.getUid());
			   for (User u :users) 
			   {
				u.setStatus(User.STATUS_INVALIDE);
				userDao.save(u);
			   }
			}
			
			t1.setUptDate(new Date());
			tenancyDao.save(t1);
			return t1;
		}
	
	/**
	 * 查询出全部租户
	 * @return
	 */
	public List<Tenancy> getAllTenancies() {
		return tenancyDao.findAllBystatus(Tenancy.NODEL);
	}

     /**
      * 删除租户伪
      * @param tenancy
      */
	public void deleteTenancy(Tenancy tenancy) {
		List<User> users = this.queryUserByUid(tenancy.getUid());
		for(User user : users)
		{
			this.delTenancyUser(String.valueOf(user.getId()));
		}
		//作废功能授权
		authService.delByTeancyId(String.valueOf(tenancy.getUid()));
		tenancy.setStatus(Tenancy.DEL);   
	    tenancyDao.save(tenancy);
	}

	/**
	 * 查询租户下所属的管理用户
	 * @param uid
	 * @return
	 */
	public List<User> queryUserByUid(long uid) {
		return userDao.findByUid(uid);
	}
     /**
      * 查询用户（租户下）
      * @param oid
      * @return
      */
	public User getUserById(String oid) {
		return userDao.findOne(Long.valueOf(oid));
	}
	  /**
     * 修改用户（租户下）
     * @param oid
     * @return
     */
	public void updateUser(User user) {
		userDao.save(user);	
	}
	/**
	 * 创建用户（租户下）
	 * @param user
	 */
	public void createUser(User user) {
		userDao.save(user);	
	}
	
	
	/**
	 * 保存的用户是否唯一
	 * @param user
	 * @return
	 */
	public boolean isOnly(User user){
		User user1 = userDao.findByLoginNameAndUid(user.getLoginName(), user.getUid());
		if(user1 == null)
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 * 解锁用户（租户下）
	 * @param user
	 */
	public void unlockUser(User user) {
		user.setStatus(User.STATUS_VALIDE);
		userDao.save(user);		
	}
	/**
	 * 锁定用户（租户下）
	 * @param user
	 */
	public void lockUser(User user) {
		user.setStatus(User.STATUS_FREE);
		userDao.save(user);	
	}
	/**
	 * 删除租户用户，设置用户状态为无效，无法登录进行管理
	 * @param id
	 */
	public void delTenancyUser(String id){
		User user = userDao.findOne(Long.valueOf(id));
		user.setStatus(User.STATUS_INVALIDE);
		userDao.save(user);
	}
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "uid");
		} else if ("createDate".equals(sortType)) {
			sort = new Sort(Direction.DESC, "crtDate");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	
	/**
	 * 创建动态查询条件组合.
	 * @param userId 
	 */
	private Specification<Tenancy> buildSpecification( Long userId, Map<String, Object> searchParams,String deadline) {
		Map<String, SearchFilter> filters = Maps.newHashMap();
		if(searchParams != null){
			filters.putAll(SearchFilter.parse(searchParams));
		}
		filters.put("status", new SearchFilter("status", Operator.EQ,Tenancy.NODEL));
		if(userId!=null&&!"".equals(userId)){
			User user = accountService.getUser(userId);
			if(!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())){
				filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));
			}
		}
		if(deadline!=null && !"".equals(deadline)){
			Date now =  DateUtils.truncate(new Date(), Calendar.DATE);
			if("1".equals(deadline)){
				filters.put("endDate", new SearchFilter("endDate",Operator.GTE,now));
				filters.put("endDate2", new SearchFilter("endDate",Operator.LT,DateUtils.addDays(now, dayLimit)));
			}else if("2".equals(deadline)){
				filters.put("endDate", new SearchFilter("endDate",Operator.LT,now));
			}
		}
		Specification<Tenancy> spec = DynamicSpecifications.bySearchFilter(filters.values(), Tenancy.class);
		return spec;
	}

	public User findByloginNameAndUid(String name, long uid) {
		
		return userDao.findByLoginNameAndUid(name,uid);
	}

	
	
	
	/**
	 * 根据期限条件查询租户
	 * @param uid
	 * @param deadline
	 * @return
	 */
	public List<Tenancy> findByDeadline(Long uid,String deadline){
		return tenancyDao.findAll(buildSpecification(uid, null,deadline));
	}
	
	public TenancyIntroDao getTenancyIntroDao() {
		return tenancyIntroDao;
	}
	public void setTenancyIntroDao(TenancyIntroDao tenancyIntroDao) {
		this.tenancyIntroDao = tenancyIntroDao;
	}
	public AuthService getAuthService() {
		return authService;
	}
	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}
	
}
