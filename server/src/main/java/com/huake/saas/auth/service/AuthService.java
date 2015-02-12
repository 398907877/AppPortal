package com.huake.saas.auth.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.huake.dict.entity.Dictionary;
import com.huake.dict.service.DictViewService;
import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.auth.entity.Auth;
import com.huake.saas.auth.entity.AuthCfg;
import com.huake.saas.auth.repository.AuthCfgDao;
import com.huake.saas.auth.repository.AuthDao;
import com.huake.saas.base.Constants;

/**
 * 
 * @author hyl
 * 授权service 接口实现
 *
 */
@Component
@Transactional
public class AuthService {
	
	
	@Autowired
	private AuthCfgDao authCfgDao;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AuthDao authDao;
	
	@Autowired
	private DictViewService dictViewService;

   /**
    * 查询 授权分页合集
    *@param userId 
    * @param searchParams 自定义查询参数，LINK_name
    * @param pageNumber  页数
    * @param pageSize  每页显示条数
    * @param sortType  排序字段
    * @return
    */
	public Page<Auth> findByCondition( Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Auth> spec = buildSpecification(userId,searchParams);
		return authDao.findAll(spec, pageRequest);
		
	}
	
	/**
	 * 创建授权
	 * @param Auth 合同
	 * @return
	 */
	public Auth create(Auth auth) {
		auth.setUpdateDate(new Date());
		auth.setCreateDate(new Date());
		return authDao.save(auth);
	}
	
	/**
	 * 修改授权
	 * @param Auth 合同
	 * @return
	 */
	public void update(Auth auth) {
		auth.setUpdateDate(new Date());
		authDao.save(auth);
	}
	
	/**
	 * 根据授权id 业务class查询授权配置
	 * @param t
	 * @param authId
	 * @return
	 */
	public <T extends AuthCfg> T findAuthCfgByAuthId(Class<T> t,Long authId,String bizcode){
		return (T)authCfgDao.findByAuthId(authId,bizcode);
	}
	public List<AuthCfg> findAllAuthCfg(Long authId){
		return authCfgDao.findAll(authId);
	}
	/**
	 * 根据授权id 业务class查询授权配置
	 * @param authId
	 * @param bizcode
	 * @return
	 */
	public AuthCfg findAuthCfgByAuthId(Long authId,String bizcode){
		return authCfgDao.findByAuthId(authId,bizcode);
	}
	/**
	 * 删除授权配置
	 * @param authCfg
	 */
	public void deleteAuthCfg(AuthCfg authCfg){
		authCfgDao.delete(authCfg);
	}
	/**
	 * 保存授权配置
	 * @param authCfg
	 */
	public void saveAuthCfg(AuthCfg authCfg){
		if(authCfg.getId() == null){
			authCfg.setCreatedDate(new Date());
		}
		authCfg.setUpdatedDate(new Date());
		authCfgDao.save(authCfg);
	}
	
	/**
	 * 通过租户 id 进行查询 授权
	 * @param uid
	 * @return
	 */
	public Auth findByUid(String uid) {
		return authDao.findByUid(uid);
	}
	/**
	 * 删除授权（伪）
	 * @param Auth 授权
	 * @return
	 */
	public void delById(Long id) {	
		Auth auth = authDao.findOne(Long.valueOf(id));
		auth.setStatus(Auth.STATUS_STALE);
		auth.setUpdateDate(new Date());
		authDao.save(auth);
	}
	/**
	 * 根据id 查询授权
	 * @param id
	 * @return
	 */
	public Auth findById(Long id){
		return authDao.findOne(id);
	}
	/**
	 * 锁定授权
	 * @param Auth 授权
	 * @return
	 */
	public void lock(String uid) {
		Auth auth = findByUid(uid);
		auth.setStatus(Auth.STATUS_LOCK);
		authDao.save(auth);
	}
	
	/**
	 * 解锁授权
	 * @param Auth 授权
	 * @return
	 */
	public void unlock(String uid) {
		Auth auth = findByUid(uid);
		auth.setStatus(Auth.STATUS_ACTIVE);
		authDao.save(auth);
	}
	/**
	 * 删除租户 id 的所有授权(伪)
	 * @param uid
	 */
	public void delByTeancyId(String uid) {
		authDao.delByTeancyId(Auth.STATUS_STALE,uid);
		
	}
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
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
	 * @param userId 
	 */
	private Specification<Auth> buildSpecification( Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		if(userId!=null&&!"".equals(userId)){
			User user = accountService.getUser(userId);
			if(!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())){
				filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));
			}
			
		}
		filters.put("status", new SearchFilter("status", Operator.EQ, Auth.STATUS_ACTIVE));
		Specification<Auth> spec = DynamicSpecifications.bySearchFilter(filters.values(), Auth.class);
		return spec;
	}
	
	/**
	 * 根据租户编号获取已授权的业务模块
	 * @param uid
	 * @return
	 */
	public Collection<Dictionary> getAuthedBizCodes(long uid){
		List<String> authBizCode = new ArrayList<String>();
		//TODO 根据授权书过滤
		Collection<Dictionary> dicts = dictViewService.getSelectModelCollection(Constants.BIZ_CODE);
		return dicts;
	}

}
