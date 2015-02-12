package com.huake.saas.membercard.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;















import com.google.common.collect.Maps;
import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.membercard.entity.CardRegister;
import com.huake.saas.membercard.entity.Mcard;
import com.huake.saas.membercard.service.CardRegisterService;
import com.huake.saas.membercard.service.McardService;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;
import com.huake.saas.util.UtilDate;

import freemarker.core.DebugBreak;

/**
 * for member and card register
 * @author chen weirong
 *
 */
@Controller
@RequestMapping(value = "/mgr/membercard")
public class CardRegisterController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(CardRegisterController.class);
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put(BaseEntity.PAGE_CRTDATE_DESC, "默认(创建时间降序)");
		sortTypes.put(BaseEntity.PAGE_CRTDATE_ASC, "创建时间升序");
	}
	
	@Autowired
	private CardRegisterService cardRegisterService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private McardService mcardService;
	
	@Autowired	
	private TenancyUserService userService;
	
	//index the memeber card associated with member
	@RequestMapping(value = "/indexreg", method = RequestMethod.GET)
	public String cardList(
	@RequestParam(value = "page", defaultValue = "1") int pageNumber,
	@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
	@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType,
	Model model, ServletRequest request){
	Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, Message.SEARCH_CONDITIONS);
	Long userId = getCurrentUID();
	Page<CardRegister> cardRegisters = cardRegisterService.getAllCardRegisters(userId,
	searchParams, pageNumber, pageSize, sortType);
	List<Mcard> mcards=mcardService.getAllMcard();
	model.addAttribute("tenancy", tenancyService.getTenancy(userId));
	model.addAttribute("uid", userId);
	model.addAttribute(CardRegister.PARMS_CARD_REGISTERS, cardRegisters);
	model.addAttribute(Mcard.PARMS_MEMBER_CARDS, mcards);
	model.addAttribute(Message.SOFT_TYPE, sortType);
	model.addAttribute(Message.SOFT_TYPES, sortTypes);
	model.addAttribute(Message.SEARCH_PARAMS, Servlets.encodeParameterStringWithPrefix(searchParams,Message.SEARCH_CONDITIONS));
	return "membercard/indexreg";
	}
	
	
	@RequestMapping(value = "/release", method = RequestMethod.GET)
	public String cardRelease(Model model)
	{
		User user = accountService.getUser(getCurrentUserId());
		if (!User.USER_ROLE_ADMIN.equals(user.getRoles())) {
			model.addAttribute("uid", user.getUid());
		}
		List<Mcard> mcards=mcardService.getAllMcard();
		model.addAttribute(CardRegister.PARMS_CARD_REGISTER, new CardRegister());
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_CREATE_PAGE);
		model.addAttribute(Mcard.PARMS_MEMBER_CARDS,mcards);
		return "membercard/release";
	}
	
	/**
	 * switch to member select page
	 * @return
	 */
	@RequestMapping(value = "/selects", method = RequestMethod.GET)
	public String memberList(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType,
			Model model, ServletRequest request)
	{
		User user = accountService.getUser(getCurrentUserId());
		if (!User.USER_ROLE_ADMIN.equals(user.getRoles())) {
			model.addAttribute("uid", user.getUid());
		}
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, Message.SEARCH_CONDITIONS);
		Page<TenancyUser> tenancyUsers=userService.findByCondition(getCurrentUserId(), searchParams, pageNumber, pageSize, sortType);
		//List<TenancyUser> users=userService.findAllUsers();
		model.addAttribute(CardRegister.PARMS_CARD_REGISTER, new CardRegister());
		model.addAttribute("tenancyUsers",tenancyUsers);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_CREATE_PAGE);
		return "membercard/selects";
	}
	
	//save the model attribute
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerMemberCard(			
			@ModelAttribute(CardRegister.PARMS_CARD_REGISTER) CardRegister cardRegister,
			@RequestParam("selectMember") String memberList,
			HttpServletRequest request, RedirectAttributes redirectAttributes){
		String members[]=memberList.split(",");
		Long userId = getCurrentUID();
		//User user = accountService.getUser(getCurrentUserId());
		for (int i = 0; i < members.length; i++)
		{
			CardRegister register=new CardRegister();
			register.setNickName(members[i]);
			register.setUid(String.valueOf(userId));
			TenancyUser tenancyUser=userService.findByLoginnameAndUid(members[i], userId);
			register.setHolderName(tenancyUser.getName());
			//UtilDate.getOrderNum();
			register.setCardNumber("HK-{OA}-"+UtilDate.getOrderNum()+i);
			register.setCardId(cardRegister.getCardId());
			register.setGetMethod(cardRegister.getGetMethod());
			cardRegisterService.saveRegisger(register);
		}
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.ADD_DATE_COMPLETE);
		//return "membercard/cardlist";
		return "redirect:/mgr/membercard/indexreg";
	}
	
	@RequestMapping(value = "/showuser/{loginname}", method = RequestMethod.GET)
	public String showTenancyUser(@PathVariable("loginname") String loginname, Model model){
		Long userId = getCurrentUID();
		TenancyUser user=userService.findByLoginnameAndUid(loginname, userId);
		model.addAttribute("forumUser",user);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "membercard/showuser";
	}
	
	@RequestMapping(value = "/getuser/{cardNumber}", method = RequestMethod.GET)
	public String showTenancyUserByCardNumber(@PathVariable("cardNumber") String cardNumber, Model model){
		Long userId = getCurrentUID();
		CardRegister  cardRegister=cardRegisterService.findLoginName(cardNumber);
		TenancyUser user=userService.findByLoginnameAndUid(cardRegister.getNickName(), userId);
		model.addAttribute("forumUser",user);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "membercard/showuser";
	}	
}
