package com.huake.saas.feedback.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.feedback.entity.Feedback;
import com.huake.saas.feedback.service.FeedbackService;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

@Controller("feedBackController")
@RequestMapping("/mgr/feedback")
public class FeedbackController extends BaseController{
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private TenancyUserService tenancyUserService;
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put(BaseEntity.PAGE_CRTDATE_DESC, "默认(创建时间降序)");
		sortTypes.put(BaseEntity.PAGE_CRTDATE_ASC, "创建时间升序");
	}
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType,
			Model model, ServletRequest request){
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long uid = getCurrentUID();
		Page< Feedback> messages = feedbackService.getMessages(uid,searchParams, pageNumber, pageSize, sortType);

		
	
		model.addAttribute("messages", messages);;
		
		model.addAttribute("messages", messages);;
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "feedback/index";
	}
	
	/**
	 * 回复页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{memberId}/toReply/{msgId}", method = RequestMethod.GET)
	public String createForm(Model model,@PathVariable("memberId") Long memberId,@PathVariable("msgId") Long msgId) {
	    Feedback message = feedbackService.findById(msgId);
		model.addAttribute("message", message);
		model.addAttribute("memberId", memberId);
		model.addAttribute("msgId", msgId);
		return "feedback/replyForm";
	}
	
	/**
	 * 回复意见，反馈
	 * @param mess
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/reply/{msgId}", method = RequestMethod.POST)
	public String reply(@Valid Feedback mess, RedirectAttributes redirectAttributes,
			@PathVariable("msgId") Long msgId) {
		//boolean flag=false;
		mess.setCategory(Feedback.CATEGORY_SERVICE_REPLY);
		feedbackService.save(mess);
		/*if(flag){
			pushMessageService.pushMessageByMemberId(Long.valueOf(mess.getMemberId()), "",
					messageSource.getMessage("support_reply",new String[] {  },	Locale.SIMPLIFIED_CHINESE), Messages.CATEGORY_FEEBACK_REPLY,mess.getId());
		}*/
		Feedback m= feedbackService.findById(msgId);
		m.setStatus(Feedback.STATUS_READ);
		feedbackService.update(m);
		redirectAttributes.addFlashAttribute("message", "回复成功");
		return "redirect:/mgr/feedback/index?search_EQ_status=1";
	}
	
	/**
	 * 删除反馈信息
	 * @param mess
	 * @param redirectAttributes
	 * @param msgId
	 * @return
	 */
	@RequestMapping(value = "/delete/{msgId}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<String,Object> delete(@Valid Feedback mess, RedirectAttributes redirectAttributes,
			@PathVariable("msgId") Long msgId) {
		Map<String,Object> map = new HashMap<String, Object>();
		Feedback m = feedbackService.findById(msgId);
		m.setStatus(BaseEntity.STATUS_INVALIDE);
		feedbackService.update(m);
		map.put("success", "true");
		return map;
	}

}
