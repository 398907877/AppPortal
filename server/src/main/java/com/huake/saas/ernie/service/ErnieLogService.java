package com.huake.saas.ernie.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.huake.saas.base.Constants;
import com.huake.saas.ernie.entity.Ernie;
import com.huake.saas.ernie.entity.ErnieItem;
import com.huake.saas.ernie.entity.ErnieLog;
import com.huake.saas.ernie.repository.ErnieLogDao;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

@Component
@Transactional
public class ErnieLogService {

	@Autowired
	private ErnieLogDao ernieLogDao;
	
	@Autowired
	private TenancyUserService userService;
	
	@Autowired
	private ErnieItemService ernieItemService;
	
	public ErnieLog findById(Long id){
		return ernieLogDao.findOne(id);
	}
	
	/**根据uid分页查询出所有中奖记录
	 * @param uid
	 * @param pageable
	 * @return
	 */
	public Page<ErnieLog> findAllErnieLogByUid(Long uid, Pageable pageable){
		return ernieLogDao.findByUid(uid, pageable);
	}
	
	/**根据uid查找活动（可带其他条件）
	 * @param uid
	 * @param category
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<ErnieLog> getAllErnieLog(Long uid, Map<String, Object> searchParams, Pageable pageable){
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		Specification<ErnieLog> spec = DynamicSpecifications.bySearchFilter(filters.values(), ErnieLog.class);
		return ernieLogDao.findAll(spec, pageable);
	}
	
	
	/**根据uid和erineId分页查询出所有中奖记录
	 * @param uid
	 * @param erineId
	 * @param pageable
	 * @return
	 */
	public Page<ErnieLog> findAllErnieLogByUidAndErineId(Long uid, Long erineId, Pageable pageable){
		return ernieLogDao.findByUidAndErineId(uid, erineId, pageable);
	}

	/**
	 * 保存营销
	 * @param ernie
	 */
	public void save(ErnieLog ernie) {	
		ernie.setCreatedAt(new Date());
		ernieLogDao.save(ernie);
		// TODO Auto-generated method stub		
	}
	
	/**
	 * 修改营销
	 * @param ernie
	 */
	public void update(ErnieLog ernie) {	
		ErnieLog updateErnie = ernieLogDao.findOne(ernie.getId());
		updateErnie.setMemberId(ernie.getMemberId());
		updateErnie.setWinning(ernie.getWinning());
		ernieLogDao.save(updateErnie);
	}

	/**
	 * 通过ID伪删除
	 * @param id
	 */
	public void deleteById(Long id) {
		ernieLogDao.delete(id);
	}

	/**
	 * 查询用户中奖记录
	 * @param ernieId 活动id
	 * @param memberId 会员id
	 * @return
	 */
	public List<ErnieLog> findByMemberIdAndErnieId(Long ernieId,Long memberId){
		if(ernieId == null || memberId == null ){
			return null;
		}
		List<ErnieLog> ernieLogs = this.ernieLogDao.findByMemberIdAndErnieId(ernieId, memberId);
		setMemberNameAndPrizeName(ernieLogs);
		return ernieLogs; 
	}

	/**
	 * 设置 中奖者姓名 奖品名称
	 * @param ernieLogs
	 */
	private void setMemberNameAndPrizeName(List<ErnieLog> ernieLogs) {
		for(int i=0;i< ernieLogs.size();i++){
			ErnieLog el = ernieLogs.get(i);
			TenancyUser user = userService.findById(el.getMemberId());
			if(user != null){
				el.setMemberName(user.getNickname());
			}else{
				el.setMemberName("*****");
			}
			ErnieItem ernieItem = ernieItemService.findById(el.getWinning());
			if(ernieItem != null){
				el.setPrizeName(ernieItem.getName());
				el.setLevel(ernieItem.getBobing());
			}else{
				el.setPrizeName("奖品");
			}
		}
	}
	
	
	/**
	 * 分页获取 活动 中奖记录
	 * @param pageSize
	 * @param pageNum
	 * @param ernieId
	 * @return
	 */
	public List<ErnieLog> findByErnieId(int pageSize,int pageNum,Long ernieId){
		if(ernieId == null){
			return null;
		}
		Pageable pageable = new PageRequest(pageNum, pageSize, new Sort(Direction.DESC, new String[] { "createdAt" }));
		Page<ErnieLog> result = ernieLogDao.findByErnieId(ernieId, pageable);
		setMemberNameAndPrizeName(result.getContent());
		return result.getContent();
	}
	
}
