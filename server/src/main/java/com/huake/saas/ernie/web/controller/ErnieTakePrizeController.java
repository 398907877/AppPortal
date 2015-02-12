package com.huake.saas.ernie.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.huake.dict.service.DictViewService;
import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.ernie.entity.Ernie;
import com.huake.saas.ernie.entity.TakePrize;
import com.huake.saas.ernie.service.ErnieItemService;
import com.huake.saas.ernie.service.ErnieService;
import com.huake.saas.ernie.service.TakePrizeService;
import com.huake.saas.user.service.TenancyUserService;


@Controller
@RequestMapping("/mgr/takePrize")
public class ErnieTakePrizeController extends BaseController {

	@Autowired
	private TakePrizeService takePrizeService;

	@Autowired
	private ErnieService ernieService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize)
			throws AppBizException {
		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, new Sort(
				Direction.DESC, new String[] { "id" }));
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, Message.SEARCH_CONDITIONS);
		if(searchParams.get("EQ_ernieId") == null){
			searchParams.put("EQ_ernieId", 0);
		}
		Page<TakePrize> takePrize = takePrizeService.getAllTakePrize(getCurrentUID(), searchParams,
				pageable);
		List<Ernie> listErnie = ernieService.findByUidAndStatus(getCurrentUID(),BaseEntity.STATUS_VALIDE);
		ModelAndView mav = new ModelAndView();
		mav.addObject("uid", getCurrentUID());
		mav.addObject("takePrizes", takePrize);
		mav.addObject("ernies", listErnie);
		mav.addObject(Message.SEARCH_PARAMS, Servlets.encodeParameterStringWithPrefix(searchParams,
						Message.SEARCH_CONDITIONS));
		mav.setViewName("ernie/takePrize/list");
		return mav;
	}
	
	/**
	 * 跳转到detail界面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detial(@PathVariable("id") Long id, Model model) {
		TakePrize takePrize = takePrizeService.findById(id);
		model.addAttribute("takePrize", takePrize);
		return "ernie/takePrize/detail";
	}

}
