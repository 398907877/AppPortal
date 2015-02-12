package com.huake.saas.weixin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.huake.base.ServiceException;
import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.base.Constants;
import com.huake.saas.product.entity.Category;
import com.huake.saas.weixin.model.AccessToken;
import com.huake.saas.weixin.model.WeixinCfg;
import com.huake.saas.weixin.model.WeixinKeyword;
import com.huake.saas.weixin.model.WeixinKeywordRule;
import com.huake.saas.weixin.repository.WeixinCfgDao;
import com.huake.saas.weixin.repository.WeixinKeywordDao;
import com.huake.saas.weixin.repository.WeixinKeywordRuleDao;

/**
 * 微信接入服务
 * @author laidingqing
 *
 */
@Service("weixinService")
public class WeixinService {

	
	@Autowired
	private AccountService accountService;

	@Autowired
	private WeixinCfgDao weixinCfgDao;
	
	@Autowired
	private WeixinKeywordRuleDao keywordRuleDao;
	
	@Autowired
	private WeixinKeywordDao keywordDao;
	
	/**
	 * 获取微信请求Token，参考微信文档，发起http至微信服务器获取.
	 * @param uid
	 * @throws ServiceException
	 */
	public AccessToken getWeixinAccessToken(Long uid) throws ServiceException{
		return null;
	}
	
	/**
	 * 保存微信端接入配置.
	 * @param cfg
	 * @return
	 * @throws ServiceException
	 */
	public WeixinCfg saveWeixinCfg(WeixinCfg cfg, Long uid) throws ServiceException{
		WeixinCfg originCfg = weixinCfgDao.findByUid(uid);
		if( null == originCfg){
			originCfg = new WeixinCfg();
			originCfg.setUid(uid);
		}
		originCfg.setAppid(cfg.getAppid());
		originCfg.setSecret(cfg.getSecret());
		originCfg.setToken(cfg.getToken());
		originCfg.setExpire(cfg.getExpire());
		originCfg.setAccessToken(cfg.getAccessToken());
		//TODO request AccessToken
		
		weixinCfgDao.save(originCfg);
		
		return originCfg;
	}
	
	/**
	 * 根据租户编号获取微信配置项
	 * @param uid
	 * @return
	 * @throws ServiceException
	 */
	public WeixinCfg getWeixinCfg(Long uid) throws ServiceException{
		return weixinCfgDao.findByUid(uid);
	}
	
	/**
	 * 获取租户下定义的所有关键字
	 * @param uid
	 * @return
	 */
	public List<WeixinKeywordRule> getWeixinKeywordRule(long uid){
		return keywordRuleDao.findByUid(uid);
	}
	
	public Page<WeixinKeywordRule> getWeixinRules(long uid, Pageable pageable){
		return keywordRuleDao.findByUid(uid, pageable);
	}
	
	
	
	public Page<WeixinKeyword> getWeixinkeywordsPage(long uid, Pageable pageable){
		return keywordDao.findByUid(uid, pageable);
	}
	
	
	public WeixinKeyword getWeixinkeywordById(long id){
		return keywordDao.findOne(id);
	}
	
	
	
	
	/**
	 * 获取微信默认规则，关键字为help
	 * @param uid
	 * @return
	 */
	public WeixinKeywordRule getWeixinKeywordRuleByDefault(long uid){
		List<WeixinKeyword> words = keywordDao.findByUidAndWord(uid, Constants.WEIXIN_DEFAULT_KEYWORD);
		if( words.size() > 0){
			WeixinKeyword keyword = words.get(0);
			return keyword.getRule();
		}
		return null;
	}
	/**
	 * 根据规则Id 查询关键词
	 */
	public List<WeixinKeyword>  getKeywordByRuleId(Long id){
		return  keywordDao.findByruldId(id);
	}
	/**
	 * 根据Id删除微信规则
	 * @param entity
	 */
	public void deleteWeixinRule(Long id){
		keywordRuleDao.delete(id);
	}
	
	
	/**
	 * 
	 * @param 获取到rule对象！
	 */
	public WeixinKeywordRule findWeixinRule(Long id){
	 return 	keywordRuleDao.findOne(id);
	}
	
	
	public void deleteWeixinKeyWord(Long id){

		keywordDao.delete(keywordDao.findOne(id));
		
	}
	
	
	
	
	
	
	public void savekeyword(WeixinKeyword entity){
		keywordDao.save(entity);
	}
	public void saverule(WeixinKeywordRule entity){
		keywordRuleDao.save(entity);
	}
	/**
	 * 根据Id查询规则
	 */
	public WeixinKeywordRule getkeyRuleById(Long id){
		return keywordRuleDao.findOne(id);
	}
	
	public List<WeixinKeywordRule>   findAllRule(){

		
		return (List<WeixinKeywordRule>) keywordRuleDao.findAll();
	} 
	
	/**
	 * -------------------------------------------rule 开始
	 * 创建分页的 rule 
	 * 
	 * wujiajun
	 */
	
	public Page<WeixinKeywordRule> getRuleBySpcPageble(Long userId, 
			Map<String, Object> searchParams,
			int pageNumber,
			int pageSize,
			String sortType){
		//TODO
		//1.pagerequest 获取
		//2.spc 获取
		
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<WeixinKeywordRule> spec = buildSpecification(userId, searchParams);
		
		
		
		return keywordRuleDao.findAll(spec, pageRequest);
		
	} 
	
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ruleCreate");
		} else if ("ruleCreate".equals(sortType)) {
			sort = new Sort(Direction.DESC, "ruleCreate");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	private Specification<WeixinKeywordRule> buildSpecification( Long userId, Map<String, Object> searchParams) {
		System.out.println("user  id  ::::"+userId);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//		查看用户是否是  ADMIN	不是得话要用uid 限制查询
//		if(userId!=null&&!"".equals(userId)){
//			User user = accountService.getUser(userId);
//			if(!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())){
//				filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));
//			}
//		}
		
		//加上uid限制
		User user = accountService.getUser(userId);
		
		filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));
		
		
		
		
		Specification<WeixinKeywordRule> spec = DynamicSpecifications.bySearchFilter(filters.values(), WeixinKeywordRule.class);
		return spec;
	}
	/**
	 * -------------------------------------------rule 结束
	 * wujiajun
	 */
	
	
	
	
	
	
	
	/**
	 * -------------------------------------------keyword 开始
	 * 创建分页的 keyword
	 * 
	 * wujiajun
	 */
	
	public Page<WeixinKeyword> getKeywordBySpcPageble(Long userId, 
			Map<String, Object> searchParams,
			int pageNumber,
			int pageSize,
			String sortType){
		//TODO
		//1.pagerequest 获取
		//2.spc 获取
		
		PageRequest pageRequest = buildPageRequestKeyword(pageNumber, pageSize, sortType);
		Specification<WeixinKeyword> spec = buildSpecificationKeyword(userId, searchParams);
		
		
		
		return keywordDao.findAll(spec, pageRequest);
		
	} 
	
	private PageRequest buildPageRequestKeyword(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "keywordCreate");
		} else if ("keywordCreate".equals(sortType)) {
			sort = new Sort(Direction.DESC, "keywordCreate");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	private Specification<WeixinKeyword> buildSpecificationKeyword( Long userId, Map<String, Object> searchParams) {
		System.out.println("user  id  ::::"+userId);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//		查看用户是否是  ADMIN	不是得话要用uid 限制查询
//		if(userId!=null&&!"".equals(userId)){
//			User user = accountService.getUser(userId);
//			if(!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())){
//				filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));
//			}
//		}
		
		//加上uid限制
		User user = accountService.getUser(userId);
		
		filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));
		
		
		
		Specification<WeixinKeyword> spec = DynamicSpecifications.bySearchFilter(filters.values(), WeixinKeyword.class);
		return spec;
	}
	/**
	 * -------------------------------------------keyword 结束
	 * wujiajun
	 */

	public List<WeixinKeywordRule> findByUid(Long currentUID) {
		// TODO Auto-generated method stub
		
		return keywordRuleDao.findByUid(currentUID);
	}
	
	
	
	
}
