package com.huake.saas.ernie.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.ernie.entity.Ernie;
import com.huake.saas.ernie.entity.ErnieLog;
import com.huake.saas.ernie.entity.TakePrize;
import com.huake.saas.ernie.repository.TakePrizeDao;

@Component
@Transactional
public class TakePrizeService {
	@Autowired
	private TakePrizeDao takePrizeDao;
	
	@Autowired 
	private ErnieService ernieService;
	
	@Autowired
	private ErnieLogService logService;
	/**
	 * 兑奖 到期 期限
	 */
	@Value("#{envProps.takePrize_dayLimit}")
	private Integer dayLimit;
	
	
	/**
	 * 进行 兑奖  
	 * @param ernieId
	 * @param memberId
	 * @return
	 */
	public Map<String,String> doTakePrize(TakePrize takePrize){
		Map<String,String> result = new HashMap<String, String>();
		if(takePrize.getMemberId() == null || takePrize.getErnieId() == null){
			result.put("success", "false");
			result.put("message", "该活动不存在或者您未登录");
			return result;
		}
		Ernie ernie = ernieService.findById(takePrize.getErnieId());
		if( ernie== null || !BaseEntity.STATUS_VALIDE.equals(ernie.getStatus())){
			result.put("success", "false");
			result.put("message", "该活动已无效");
			return result;
		}
		
		Date now = new Date();
		/**
		 * 当前日期小于结束日期 不能兑奖
		 */
		if(now.before(ernie.getEndDate())){
			result.put("success", "false");
			result.put("message", "活动尚未结束，无法兑奖");
			return result;
		}
		/**
		 * 当前日期大于 结束日期 加上兑奖期限 不能兑奖
		 */
		if(now.after(DateUtils.addDays(ernie.getEndDate(), dayLimit))){
			result.put("success", "false");
			result.put("message", "已超过兑奖期限，无法兑奖");
			return result;
		}
		
		List<ErnieLog> ernieLog = logService.findByMemberIdAndErnieId(takePrize.getErnieId(), takePrize.getMemberId());
		if(ernieLog == null || ernieLog.size() == 0){
			result.put("success", "false");
			result.put("message", "您未在该活动中中奖，无法兑奖");
			return result;
		}
		TakePrize oldTakePrize = takePrizeDao.findByMemberIdAndErnieId(takePrize.getErnieId(), takePrize.getMemberId());
		if(oldTakePrize != null){
			result.put("success", "false");
			result.put("message", "您已兑奖，不能重复兑奖");
			return result;
		}
		takePrize.setStatus(BaseEntity.STATUS_VALIDE);
		takePrize.setCreatedAt(now);
		takePrizeDao.save(takePrize);
		result.put("success", "true");
		result.put("message", "兑奖成功");
		return result;
	}
	
	/**根据uid查找兑奖记录（可带其他条件）
	 * @param uid
	 * @param category
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<TakePrize> getAllTakePrize(Long uid, Map<String, Object> searchParams, Pageable pageable){
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		Specification<TakePrize> spec = DynamicSpecifications.bySearchFilter(filters.values(), TakePrize.class);
		return takePrizeDao.findAll(spec, pageable);
	}
	
	public TakePrize findById(Long id){
		return takePrizeDao.findOne(id);
	}
}
