package com.huake.saas.version.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.huake.saas.account.entity.User;
import com.huake.saas.account.repository.UserDao;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.version.entity.AppVersionInfo;
import com.huake.saas.version.repository.AppVersionInfoDao;




@Service("appVersionInfoService")
@Transactional(readOnly = true)
public class AppVersionInfoService {


	@Autowired
	private AppVersionInfoDao appVersionInfoDao;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 
	 * @param category 设备类型
	 */
	public AppVersionInfo getLastestAppVersionInfo(String category) {
		List<AppVersionInfo> infos = appVersionInfoDao.findByAppCategory(category);
		if( infos.size() > 0){
			return infos.get(0);
		}
		return null;
	}

	/**
	 * 分页查询
	 * @param memberId 
	 * @param userId
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<AppVersionInfo> findByCondition(Long memberId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<AppVersionInfo> spec = buildSpecification(memberId,searchParams);
		return appVersionInfoDao.findAll(spec, pageRequest);
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
	 * @param memberId 
	 */
	private Specification<AppVersionInfo> buildSpecification(Long memberId, Map<String, Object> searchParams) {
		if(memberId != null)
		{
			User user = userDao.findOne(memberId);
			if(!user.getRoles().equals(User.USER_ROLE_SYS_ADMIN))
			{
				searchParams.put("EQ_tenancyId", user.getUid()+"");
			}
		}
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<AppVersionInfo> spec = DynamicSpecifications.bySearchFilter(filters.values(), AppVersionInfo.class);
		return spec;
	}
	

	/**
	 * 保存应用版本信息
	 * @param info
	 * @return
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public AppVersionInfo addNewAppVersion(AppVersionInfo info) {
		info.setCrtDate(new Date());
		appVersionInfoDao.save(info);
		return info;
	}
	
	/**
	 * 修改更新应用版本信息
	 * @param versionInfo
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void updateversion(AppVersionInfo versionInfo)  {
		AppVersionInfo vi=appVersionInfoDao.findOne(versionInfo.getId());
		vi.setCrtDate(new Date());
		vi.setAppCategory(versionInfo.getAppCategory());
		vi.setAppName(versionInfo.getAppName());
		vi.setIntro(versionInfo.getIntro());
		vi.setUrl(versionInfo.getUrl());
		vi.setVerCode(versionInfo.getVerCode());
		vi.setVerName(versionInfo.getVerName());
		vi.setIsNew(versionInfo.getIsNew());
		appVersionInfoDao.save(vi);
	}

	/**
	 * 通过id查找
	 * @param id
	 * @return
	 */
	public AppVersionInfo findVersionById(Integer id) {
		Assert.notNull(id);
		return appVersionInfoDao.findOne(id);
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void deleteVersion(Integer id) {
		Assert.notNull(id);
		appVersionInfoDao.delete(id);
		
	}
	
}
