package com.huake.saas.ernie.web.controller;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huake.dict.entity.Dictionary;
import com.huake.dict.service.DictViewService;
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.ernie.entity.Ernie;
import com.huake.saas.ernie.service.ErnieItemService;
import com.huake.saas.ernie.service.ErnieService;
import com.huake.saas.images.Photo;

@Controller("ernieAdminController")
@RequestMapping("/mgr/ernies")
public class ErnieAdminController extends BaseController {

	@Autowired
	private ErnieService ernieService;

	@Autowired
	private DictViewService dictViewService;

	@Autowired
	private ErnieItemService ernieItemService;

	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;

	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class,"startDate",new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd HH:mm"), true));
		binder.registerCustomEditor(Date.class,"endDate",new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd HH:mm"), true));

	}
	
	@RequestMapping(value = "/showInfo", method = RequestMethod.GET)
	public String index()
			throws AppBizException {
		return "ernie/info";
	}
	/**
	 * 列举所有.当前只有一个有效，服务端UPDATE要注意
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize)
			throws AppBizException {
		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, new Sort(
				Direction.DESC, new String[] { "updatedAt" }));
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Page<Ernie> ernies = ernieService.getAllErnie(getCurrentUID(), searchParams, pageable);
		ModelAndView mav = new ModelAndView();
		mav.addObject("ernies", ernies);
		mav.setViewName("ernie/list");
		mav.addObject("dicts", dictViewService.getDictViewList("ERNIE_CATE"));
		return mav;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newErnie() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("ernie", new Ernie());
		mav.setViewName("ernie/new");
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute("ernie") Ernie ernie,
			@RequestParam("images") List<CommonsMultipartFile> fileInputxs,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {

					List<Photo> photos = ernieService.uploadThumb(fileInput,
							(MultipartHttpServletRequest) request);
					ernie.setImage(imgPath + photos.get(2).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		ernieService.save(ernie);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.ADD_DATE_COMPLETE);
		return "redirect:/mgr/ernies/list";
	}

	@RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Long id, Model model) {
		Ernie ernie = ernieService.findById(id);
		model.addAttribute("ernie", ernie);
		return "ernie/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("ernie") Ernie ernie,
			@RequestParam("images") List<CommonsMultipartFile> fileInputxs,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {

					List<Photo> photos = ernieService.uploadThumb(fileInput,
							(MultipartHttpServletRequest) request);
					ernie.setImage(imgPath + photos.get(2).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		ernieService.update(ernie);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/ernies/list";
	}

	@RequestMapping(value = "{id}/delete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("id") Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		ernieService.deleteById(id);
		map.put("success", true);
		return map;
	}


	@ModelAttribute("ernieCates")
	public Collection<Dictionary> getErniesCates() {
		return dictViewService.getSelectModelCollection(Constants.ERNIE_CATE);
	}
	
	/*****************************博饼活动***************************/
	
	/**
	 * 列举所有.当前只有一个有效，服务端UPDATE要注意
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "/bobing/list", method = RequestMethod.GET)
	public ModelAndView bobingList(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize)
			throws AppBizException {
		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, new Sort(
				Direction.DESC, new String[] { "updatedAt" }));
		Map<String, Object> searchParams = new HashMap<String, Object>();
		//查询是博饼的活动记录
		searchParams.put("EQ_category", Ernie.CATEGORY_VALID);
		Page<Ernie> ernies = ernieService.getAllErnie(getCurrentUID(), searchParams, pageable);
		ModelAndView mav = new ModelAndView();
		mav.addObject("ernies", ernies);
		mav.setViewName("ernie/bobing/list");
		return mav;
	}
	
	/**博饼活动新增页
	 * @return
	 */
	@RequestMapping(value = "/bobing/new", method = RequestMethod.GET)
	public ModelAndView newBobingErnie() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("ernie", new Ernie());
		mav.addObject("bobing","博饼");
		mav.setViewName("ernie/bobing/new");
		return mav;
	}
	
	/**博饼活动保存
	 * @param ernie
	 * @param fileInputxs
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bobing/create", method = RequestMethod.POST)
	public String bobingCreate(@ModelAttribute("ernie") Ernie ernie,
			@RequestParam("images") List<CommonsMultipartFile> fileInputxs,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {

					List<Photo> photos = ernieService.uploadThumb(fileInput,
							(MultipartHttpServletRequest) request);
					ernie.setImage(imgPath + photos.get(2).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		ernie.setCategory(Ernie.CATEGORY_VALID);
		ernieService.save(ernie);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.ADD_DATE_COMPLETE);
		return "redirect:/mgr/ernies/bobing/list";
	}

	/**博饼修改页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bobing/{id}/edit", method = RequestMethod.GET)
	public String bobingEdit(@PathVariable("id") Long id, Model model) {
		Ernie ernie = ernieService.findById(id);
		model.addAttribute("ernie", ernie);
		model.addAttribute("bobing", "博饼");
		return "ernie/bobing/edit";
	}

	/**博饼活动修改
	 * @param ernie
	 * @param fileInputxs
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bobing/update", method = RequestMethod.POST)
	public String bobingUpdate(@ModelAttribute("ernie") Ernie ernie,
			@RequestParam("images") List<CommonsMultipartFile> fileInputxs,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {

					List<Photo> photos = ernieService.uploadThumb(fileInput,
							(MultipartHttpServletRequest) request);
					ernie.setImage(imgPath + photos.get(2).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		ernie.setCategory(Ernie.CATEGORY_VALID);
		ernieService.update(ernie);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/ernies/bobing/list";
	}
}
