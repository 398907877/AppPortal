package com.huake.saas.feedback.web.api.v1;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseApiController;
import com.huake.saas.feedback.entity.Feedback;
import com.huake.saas.feedback.service.FeedbackService;

@Controller
@RequestMapping(value = "/api/v1/feedback")
public class FeedbackApiController extends BaseApiController{
	
	@Autowired
	private FeedbackService feedBackService;
	
	/**
	 * 会员反馈信息查找
	 * @param pageNum
	 * @param pageSize
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/memberMessages",method = RequestMethod.GET , produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Feedback> memberMessages(@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum,
			@RequestParam(value = "memberId" , required = true) Long memberId) throws Exception{
		List<Feedback> mess = feedBackService.getFeeBacks(memberId,Feedback.CATEGORY_CLIENT_CALLBACK,
				Feedback.CATEGORY_SERVICE_REPLY, pageNum,Integer.valueOf(Message.PAGE_SIZE), "dateAsc");
		return mess;
	}
	
	/**
	 * 意见反馈
	 * @param messages
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/sendMessage",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value=HttpStatus.OK)
	public Map<String, String> SuggestionFreeBack(@RequestBody Feedback messages){
		feedBackService.save(messages);
		return SUCCESS_RESULT;
		
	}
	
	/**
	 * 会员阅读消息
	 * @param memberId
	 * @param msgId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{msgId}/readMessage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Feedback readMessage(@PathVariable(value = "msgId") final Long msgId) throws Exception {
		if (msgId != null) {
			// 若请求处理中带消息编号，同时处理阅读过的消息
			feedBackService.readMessage(msgId);
		}
		return feedBackService.findById(msgId);
		//return SUCCESS_RESULT;
	}
}
