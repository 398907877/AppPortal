package com.huake.saas.membercard.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.membercard.entity.CardRegister;
import com.huake.saas.membercard.repository.CardRegisterDao;

@Component
@Transactional
public class CardRegisterService {
	
	private static Logger logger = LoggerFactory.getLogger(CardRegisterService.class);
	
	@Autowired
	private CardRegisterDao cardRegisterDao;

	public Page<CardRegister> getAllCardRegisters(Long userId,
			Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<CardRegister> spec = buildSpecificationApi(userId, searchParams);
		Page<CardRegister> cardRegisters= cardRegisterDao.findAll(spec, pageRequest);
		return cardRegisters;
	}
	
	
	/**
	 * create the request for page.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if (BaseEntity.PAGE_CRTDATE_DESC.equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if (BaseEntity.PAGE_CRTDATE_ASC.equals(sortType)) {
			sort = new Sort(Direction.ASC, BaseEntity.PAGE_CRTDATE_ASC);
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}		
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<CardRegister> buildSpecificationApi(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, userId));
		return DynamicSpecifications.bySearchFilter(filters.values(), CardRegister.class);
	}


	public void saveRegisger(CardRegister entity) {
		// TODO Auto-generated method stub
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		entity.setCrtDate(new Date());
		entity.setUpDate(new Date());
		cardRegisterDao.save(entity);
	}


	public List<CardRegister> getCardRegistersByCardId(Long id) {
		// TODO Auto-generated method stub
		String status="1";
		Integer cardId=Integer.valueOf(id.toString());
		List<CardRegister> registers=cardRegisterDao.findRegistersByCardId(cardId,status);
		return registers;
	}


	public CardRegister findLoginName(String cardNumber) {
		// TODO Auto-generated method stub
		String status="1";
		CardRegister cardRegister=cardRegisterDao.findCardRegisterByCardNumber(cardNumber,status);
		return cardRegister;
	}	

}
