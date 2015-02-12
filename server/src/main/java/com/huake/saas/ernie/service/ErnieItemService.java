package com.huake.saas.ernie.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.ernie.entity.ErnieItem;
import com.huake.saas.ernie.repository.ErnieItemDao;

@Component
@Transactional
public class ErnieItemService {

	@Autowired
	private ErnieItemDao ernieItemDao;
	
	public ErnieItem findById(Long id){
		return ernieItemDao.findOne(id);
	}
	
	public Page<ErnieItem> findAllErnieByUid(Long uid, Pageable pageable){
		
		return ernieItemDao.findByUid(uid, pageable,BaseEntity.STATUS_VALIDE);
	}
	
	public Page<ErnieItem> findAllErnieByUidAndErnieId(Long uid,Long ernieId,Pageable pageable){
		
		return ernieItemDao.findByUid(uid, ernieId, pageable,BaseEntity.STATUS_VALIDE);
	}

	/**
	 * 保存营销奖品
	 * @param ernie
	 */
	public void save(ErnieItem ernie) {	
		ernie.setStatus(BaseEntity.STATUS_VALIDE);
		ernieItemDao.save(ernie);
		// TODO Auto-generated method stub		
	}
	
	/**
	 * 修改营销奖品
	 * @param ernie
	 */
	public void update(ErnieItem ernie) {	
		ErnieItem updateErnie = ernieItemDao.findOne(ernie.getId());
		updateErnie.setImage(ernie.getImage());
		updateErnie.setIntegral(ernie.getIntegral());
		updateErnie.setName(ernie.getName());
		updateErnie.setProbability(ernie.getProbability());
		updateErnie.setBobing(ernie.getBobing());
		ernieItemDao.save(updateErnie);
	}

	/**
	 * 通过ID伪删除
	 * @param id
	 */
	public void deleteById(Long id) {
		ErnieItem ernie = ernieItemDao.findOne(id);
		ernie.setStatus(BaseEntity.STATUS_INVALIDE);
		ernieItemDao.save(ernie);
	}

	/**
	 * 通过活动ID返回其它项的中奖率
	 * @param id
	 * @return
	 */
	public int findOtherProbability(Long id) {
		List<ErnieItem> ernieItems = ernieItemDao.findByErnieId(id,BaseEntity.STATUS_VALIDE);
		int otherProbability = 0;
		for (ErnieItem e : ernieItems) {
			otherProbability += e.getProbability().intValue();
		}
		return otherProbability;
	}

	/**
	 * 通过抽奖活动，取得奖品项
	 * @param id
	 * @return
	 */
	public List<ErnieItem> findByErineId(Long id) {
		return ernieItemDao.findByErnieId(id,BaseEntity.STATUS_VALIDE);
	}
	
}
