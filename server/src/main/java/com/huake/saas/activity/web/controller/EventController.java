package com.huake.saas.activity.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.huake.saas.activity.entity.Event;
import com.huake.saas.activity.service.EventService;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.images.ImagesUploadService;
import com.huake.saas.images.Photo;

/**
 * 活动管理控制器
 * 
 * @author laidingqing
 * 
 */
@Controller
@RequestMapping(value = "/mgr/events")
public class EventController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(EventController.class);
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("crtDate", "创建时间");
	}

	@Autowired
	private EventService eventService;

	@Autowired
	private ImagesUploadService imagesUploadService;

	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, "startDate", new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
		binder.registerCustomEditor(Date.class, "endDate", new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
	}
	/**
	 * 列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		Page<Event> events = eventService.getAllEventsByUid(getCurrentUID(),
				searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("events", events);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "events/index";
	}

	/**
	 * 新建活动
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@ModelAttribute("event") Event event) {
		event.setUid(getCurrentUID());
		return "events/add";
	}

	/**
	 * 新增活动.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String create(@ModelAttribute("event") Event event, @RequestParam(value = "file", required = true) CommonsMultipartFile file, 
			HttpServletRequest request, RedirectAttributes redirectAttributes) throws ServletException, IOException {
		List<Photo> photos = eventService.uploadEventPoster(file, (MultipartHttpServletRequest)request);
		event.setUid(getCurrentUID());
		eventService.createEvent(event);
		
		eventService.saveEventImages(photos, event);
		//update photo
		redirectAttributes.addFlashAttribute("message", "恭喜，您新增的活动保存成功!");
		return "redirect:/mgr/events/index";
	}
	
	
	/**
	 * 修改活动.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id,Model model
			) throws ServletException, IOException {

		Event event = eventService.findById(id);
		model.addAttribute("event", event);
		//update photo
		return "events/update";
	}
	
	/**
	 * 修改活动.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("event") Event event1, @RequestParam(value = "file") CommonsMultipartFile file, 
			HttpServletRequest request, RedirectAttributes redirectAttributes) throws ServletException, IOException {

		Event event = eventService.findById(event1.getId());
		event.setPoster(event1.getPoster());
		// file 一直有值，
		if(file != null)
		{
			List<Photo> photos = null;
			try {
				photos = eventService.uploadEventPoster(file, (MultipartHttpServletRequest)request);
				eventService.saveEventImages(photos, event);
			} catch (Exception e) {
				logger.info("活动保存图片不存在！不上传文件file却有值。");
			}
		
		}
		event.setStartDate(event1.getStartDate());
		event.setEndDate(event1.getEndDate());
		event.setInfo(event1.getInfo());
		event.setTitle(event1.getTitle());
		event.setAddress(event1.getAddress());
		event.setLinkman(event1.getLinkman());
		event.setTel(event1.getTel());
		eventService.update(event);
		
		//update photo
		redirectAttributes.addFlashAttribute("message", "恭喜，您修改的活动保存成功!");
		return "redirect:/mgr/events/index";
	}
	/**
	 * 查询活动报名人员
	 * @param id 活动id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{id}/show",method = RequestMethod.GET)
	public String entered(@PathVariable("id") Long id,Model model){
		model.addAttribute("users", eventService.findEntered(id));
		return "/events/show";
	}
	
	/**
	 * 删除
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletExceptionupdate/{id}
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String del(
			@PathVariable("id") Long id, RedirectAttributes redirectAttributes) throws ServletException, IOException {
		eventService.del(id);
		redirectAttributes.addFlashAttribute("message", "恭喜，您删除的活动成功!");
		return "redirect:/mgr/events/index";
	}
	
	@RequestMapping(value = "showInfo")
	public String showInfo() {
		return "events/info";
	}
	
}
