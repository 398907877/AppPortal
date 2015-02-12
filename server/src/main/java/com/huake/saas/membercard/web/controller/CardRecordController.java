package com.huake.saas.membercard.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.membercard.entity.CardRecord;
import com.huake.saas.membercard.entity.CardRegister;
import com.huake.saas.membercard.entity.Mcard;
import com.huake.saas.membercard.service.CardRecordService;

/**
 * for index the card use record
 * @author chen weirong
 *
 */

@Controller
@RequestMapping(value = "/mgr/membercard")
public class CardRecordController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(CardRecordController.class);
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put(BaseEntity.PAGE_CRTDATE_DESC, "默认(创建时间降序)");
		sortTypes.put(BaseEntity.PAGE_CRTDATE_ASC, "创建时间升序");
	}
	
	@Autowired
	private CardRecordService cardRecordService;
	
	//index the memeber card useage list
	@RequestMapping(value = "/indexusage", method = RequestMethod.GET)
	public String useageList(
	@RequestParam(value = "page", defaultValue = "1") int pageNumber,
	@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
	@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType,
	Model model, ServletRequest request){
	Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, Message.SEARCH_CONDITIONS);
	Long userId = getCurrentUID();
	Page<CardRecord> cardRecords = cardRecordService.getAllCardRecords(userId,
	searchParams, pageNumber, pageSize, sortType);
	//List<Mcard> mcards=mcardService.getAllMcard();
	model.addAttribute("tenancy", tenancyService.getTenancy(userId));
	model.addAttribute("uid", userId);
	model.addAttribute(CardRecord.PARMS_CARD_RECORDS, cardRecords);
	//model.addAttribute(Mcard.PARMS_MEMBER_CARDS, mcards);
	model.addAttribute(Message.SOFT_TYPE, sortType);
	model.addAttribute(Message.SOFT_TYPES, sortTypes);
	model.addAttribute(Message.SEARCH_PARAMS, Servlets.encodeParameterStringWithPrefix(searchParams,Message.SEARCH_CONDITIONS));
	//logger.debug(">>>>>查询结果 cardregister>>>"+cardRegisters.getContent().size());
	return "membercard/indexusage";
	}	
}
