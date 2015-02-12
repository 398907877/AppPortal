package com.huake.saas.ernie.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.transaction.Transactional;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.google.common.collect.Maps;
import com.huake.dict.entity.Dictionary;
import com.huake.dict.service.DictViewService;
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Compress;
import com.huake.saas.ernie.entity.Ernie;
import com.huake.saas.ernie.entity.ErnieItem;
import com.huake.saas.ernie.entity.ErnieLog;
import com.huake.saas.ernie.repository.ErnieDao;
import com.huake.saas.ernie.repository.ErnieItemDao;
import com.huake.saas.images.ImagesUploadService;
import com.huake.saas.images.Photo;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

@Component
@Transactional
public class ErnieService {
	public static final String IMAGES_ERNIE_PIC_DIR = "ernie";

	@Autowired
	private ErnieDao ernieDao;
	
	@Autowired
	private ErnieItemDao ernieItemDao;
	
	@Autowired
	private ImagesUploadService imageUploadService;
	
	@Autowired
	private ErnieLogService ernieLogService;
	
	@Autowired	
	private TenancyUserService tUserService;
	
	@Autowired
	private BoBingService bobingService;
	
	@Autowired
	private DictViewService dictViewService;
	
	public Ernie findById(Long id){
		return ernieDao.findOne(id);
	}
	
	public Page<Ernie> findAllErnieByUid(Long uid, Pageable pageable){
		
		return ernieDao.findByUid(uid, pageable);
	}
	
	/**根据uid查找活动（可带其他条件）
	 * @param uid
	 * @param category
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<Ernie> getAllErnie(Long uid, Map<String, Object> searchParams, Pageable pageable){
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status", new SearchFilter("status", Operator.EQ, Constants.STATUS_VALID));
		Specification<Ernie> spec = DynamicSpecifications.bySearchFilter(filters.values(), Ernie.class);
		return ernieDao.findAll(spec, pageable);
	}
	
	/**根据uid分页查找正在进行的活动（可带其他条件）
	 * @param uid
	 * @param category
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<Ernie> getNowAllErnie(Long uid, Map<String, Object> searchParams, Pageable pageable){
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status", new SearchFilter("status", Operator.EQ, Constants.STATUS_VALID));
		Date now =  new Date();
		filters.put("startDate", new SearchFilter("startDate",Operator.LTE,now));
		filters.put("endDate", new SearchFilter("endDate",Operator.GTE,now));
		Specification<Ernie> spec = DynamicSpecifications.bySearchFilter(filters.values(), Ernie.class);
		return ernieDao.findAll(spec, pageable);
	}
	
	/**
	 * 保存营销
	 * @param ernie
	 */
	public void save(Ernie ernie) {	
		ernie.setCreatedAt(new Date());
		ernie.setUpdatedAt(new Date());
		ernie.setStatus(BaseEntity.STATUS_VALIDE);
		ernieDao.save(ernie);
		// TODO Auto-generated method stub		
	}
	
	/**
	 * 修改营销
	 * @param ernie
	 */
	public void update(Ernie ernie) {	
		Ernie updateErnie = ernieDao.findOne(ernie.getId());
		updateErnie.setUpdatedAt(new Date());
		updateErnie.setCategory(ernie.getCategory());
		updateErnie.setContent(ernie.getContent());
		updateErnie.setDescription(ernie.getDescription());
		updateErnie.setEndDate(ernie.getEndDate());
		updateErnie.setStartDate(ernie.getStartDate());
		updateErnie.setTitle(ernie.getTitle());
		updateErnie.setTime(ernie.getTime());
		updateErnie.setProbability(ernie.getProbability());
		updateErnie.setImage(ernie.getImage());
		ernieDao.save(updateErnie);
	}

	/**
	 * 通过ID伪删除
	 * @param id
	 */
	public void deleteById(Long id) {
		Ernie ernie = ernieDao.findOne(id);
		ernie.setStatus(BaseEntity.STATUS_INVALIDE);
		ernieDao.save(ernie);
	}
	
	
	/**
	 * 上传缩略图
	 * @param file
	 * @param request
	 * @return
	 */
	public List<Photo> uploadThumb(CommonsMultipartFile file, final MultipartHttpServletRequest request){
		if (ServletFileUpload.isMultipartContent(request)) {
			Compress thumbCompress = new Compress(60, 60, 1, Constants.PHOTO_THUMBNAIL);
			Compress smallCompress = new Compress(300, 150, 1, Constants.PHOTO_SMALL);
			List<Photo> photos = imageUploadService.saveImage(file, IMAGES_ERNIE_PIC_DIR, thumbCompress,smallCompress);
			return photos;
		}
		return null;
	}

	/**
	 * 通过UID 与 状态进行进行查询 
	 * @param currentUID
	 * @param statusValide
	 * @return
	 */
	public List<Ernie> findByUidAndStatus(Long currentUID, String statusValide) {
		// TODO Auto-generated method stub
		return ernieDao.findByUidAndStatus(currentUID,statusValide);
	}
	
	/**
	 * 判断用户是否有权限抽奖
	 * @param ernieId
	 * @param memberId
	 */
	public Map<String,String> hasPower(Long ernieId, Long memberId,Long uid) {
		Map<String,String> map = new HashMap<String,String>();
		TenancyUser user = tUserService.findById(memberId);
		if(user == null || user.getStatus() == TenancyUser.del || user.getUid() != uid.longValue()){
			map.put("success", "false");
			map.put("message", "您未获得参与活动的资格");
			return map;
		}
		
		Ernie ernie = this.findById(ernieId);
		if(ernie == null){
			map.put("success", "false");
			map.put("message", "该活动不存在");
			return map;
		}else{
			if(BaseEntity.STATUS_INVALIDE.equals(ernie.getStatus())){
				map.put("success", "false");
				map.put("message", "该活动已失效");
				return map;
			}else{
				Date now = new Date();
				if(now.before(ernie.getStartDate())){
					map.put("success", "false");
					map.put("message", "该活动尚未开始");
					return map;
				}
				if(now.after(ernie.getEndDate())){
					map.put("success", "false");
					map.put("message", "该活动已结束");
					return map;
				}
			}
		}
		map.put("success", "true");
		return map;
	}

	/**
	 * 用户进行抽奖
	 * @param ernieId
	 * @param memberId
	 */
	public Map<String,Object> draw(Long ernieId, Long memberId) {

		Map<String,Object> map = new HashMap<String,Object>();
		
		Ernie e  = ernieDao.findOne(ernieId);
		BigDecimal probability = e.getProbability();
		/**
		 * 中奖概率(中奖概率不为0时)
		 */
		if(probability != null && probability.intValue() > 0 ){
			/**
			 * 生成 10000内的随机数
			 */
			Random random = new Random();
			Integer n = random.nextInt(10000) + 1;
			/**
			 * 中奖概率（中奖概率0 到 100 之间）
			 */
			int hasSelect = probability.intValue();
			/**
			 * 中奖(随机数小等于中奖概率时)
			 */
			if( n <= hasSelect*100 ){
				List<ErnieItem> list = ernieItemDao.findByErnieId(e.getId(),BaseEntity.STATUS_VALIDE);
				ErnieItem et = drawSuccess(list,n,hasSelect);
				if( et == null ){
					map.put("success", "false");
					map.put("message", "用户未中奖！");
					return map;
				}	
				/**
				 * 判断还有没有数量
				 */ 
				if( et.getNum() > 0 ){
					map.put("success", "true");
					map.put("message", "恭喜您 ，已抽中"+et.getName()+"一份！");
					map.put("ErnieItem", et.getId());
					et.setNum(et.getNum()-1);
					/**
					 * 如果有积分，给用户加积分
					 */
					
					
					/**
					 * 进行中奖记录（）
					 */	
					ErnieLog ernie = new ErnieLog();
					TenancyUser tu = tUserService.findById(memberId);
					Long uid = tu.getUid();
					ernie.setErineId(ernieId);
					ernie.setMemberId(memberId);
					ernie.setUid(uid);
					ernie.setWinning( et.getId());
					ernie.setCreatedAt(new Date());
					ernieLogService.save(ernie);
					
				}
				else{
					map.put("success", "false");
					map.put("message", "用户未中奖！");
				}
			}
			else{//不中
				map.put("success", "false");
				map.put("message", "用户未中奖！");
			}
		}
		else{
			map.put("success", "false");
			map.put("message", "用户未中奖！");
		}
		
		
		return map;
	}

	/**
	 * 抽中哪项
	 * @param et
	 * @param n
	 */
	private ErnieItem drawSuccess(List<ErnieItem> list, Integer n,int hasSelect) {
		List<ErnieItem> matchList = new ArrayList<ErnieItem>();
		//总概率
		int mainProbability = 0;
		//list 为 概率从大到小
		for ( ErnieItem et : list ) {
			mainProbability += et.getProbability().intValue();
		}
		
		//list 为 概率从大到小
		for ( ErnieItem et : list ) {
			/**
			 * i 单项中奖数
			 */
			int i = mainProbability*hasSelect;
			if( n.intValue() < i ){
				matchList.add(et);
			}
			mainProbability = mainProbability - et.getProbability().intValue();
		}
		/**
		 * 返回最后一项，即最难中的那一个
		 */
		if( matchList.size() == 0 )
		{
			return null;
		}
		return matchList.get(matchList.size()-1);	
	}
	
	/**
	 * 进行博饼 摇奖
	 * @return
	 */
	public Map<String,Object> doBobing(Long ernieId,Long memberId,Long uid){
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		List<Integer> dice = bobingService.getDice(); //六个色子的点数
		String result = bobingService.getResult(dice);//判断结果
		String resultValue = null;
		ErnieItem ernieItem = ernieItemDao.findByBobingAndErnieId(result, ernieId,BaseEntity.STATUS_VALIDE);
		
		List<Dictionary> dicts = dictViewService.getDictViewList("BOBING");
		if(dicts != null && dicts.size()>0){
			for(Dictionary dict : dicts){
				if(dict.getKey().equals(result)){
					resultValue = dict.getValue();//获取结果对应的中文
				}
			}
		}
		returnMap.put("dice", dice);
		StringBuffer message = new StringBuffer();
		if(ErnieItem.BOBING_BUZHONG.equals(result)){
			message = message.append("很遗憾，您没有摇中");
		}else{
			message.append("恭喜您，摇到了");
			message.append(resultValue);
			
		}
		returnMap.put("result", result);
		
		if(ernieItem == null){
			returnMap.put("success", "false");
			if(!ErnieItem.BOBING_BUZHONG.equals(result)){
				message.append("。很遗憾，该项暂未设定任何奖品");
			}
			returnMap.put("ernieItemId", null);
		}else{
			if(ernieItem.getNum() == 0){
				message.append("。很遗憾，该奖品已被抢光");
			}else{
				returnMap.put("success", "true");
				message.append("。获得了");
				message.append(ernieItem.getName());
			
				ErnieLog ernieLog = new ErnieLog();
			
				ernieLog.setErineId(ernieId);
				ernieLog.setMemberId(memberId);
				ernieLog.setUid(uid);
				ernieLog.setWinning( ernieItem.getId());
				ernieLog.setCreatedAt(new Date());
				ernieLogService.save(ernieLog);//保存中奖 记录
			
				ernieItem.setNum(ernieItem.getNum()-1);//奖品数量 减一
				returnMap.put("ernieItemId", ernieItem.getId().toString());
				ernieItemDao.save(ernieItem);
				returnMap.put("ernieItemId", ernieItem.getId().toString());
			}
		}

		returnMap.put("message", message.toString());
		return returnMap;
	}
	/**
	 * 根据uid查询当前正在进行的 营销互动
	 * @param uid
	 * @return
	 */
	public List<Ernie> findNowErnieByUid(Long uid){
		Map<String, SearchFilter> filters = Maps.newHashMap();
		filters.put("status", new SearchFilter("status", Operator.EQ,BaseEntity.STATUS_VALIDE));
		filters.put("uid", new SearchFilter("uid", Operator.EQ,uid));
		Date now =  new Date();
		filters.put("startDate", new SearchFilter("startDate",Operator.LTE,now));
		filters.put("endDate", new SearchFilter("endDate",Operator.GTE,now));
		Specification<Ernie> spec = DynamicSpecifications.bySearchFilter(filters.values(), Ernie.class);
		
		return ernieDao.findAll(spec);
	}
}
