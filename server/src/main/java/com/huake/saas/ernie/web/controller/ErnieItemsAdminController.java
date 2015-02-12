package com.huake.saas.ernie.web.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.huake.saas.ernie.entity.ErnieItem;
import com.huake.saas.ernie.service.ErnieItemService;
import com.huake.saas.ernie.service.ErnieService;
import com.huake.saas.images.Photo;

/**
 * 奖品设置
 * @author Administrator
 *
 */
@Controller("ErnieItemsAdminController")
@RequestMapping("/mgr/ernieItems")
public class ErnieItemsAdminController extends BaseController {

	@Autowired
	private ErnieService ernieService;

	@Autowired
	private DictViewService dictViewService;

	@Autowired
	private ErnieItemService ernieItemService;

	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;

	
	// ///////////////////////////////////////////
	// 设置营销奖品
	// //////////////////////////////////////////

	@RequestMapping(value = "/{id}/items", method = RequestMethod.GET)
	public ModelAndView list(
			@PathVariable("id") Long id,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize)
			throws AppBizException {
		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, new Sort(
				Direction.DESC, new String[] { "id" }));
		Page<ErnieItem> ernies = ernieItemService.findAllErnieByUidAndErnieId(
				getCurrentUID(),id, pageable);
		for (ErnieItem ernieItem : ernies) {
			System.out.println("111111111"+ernieItem.getId());
			System.out.println("222222222"+ernieItem.getImage());
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("ernieItems", ernies);
		mav.addObject("ernieID", id);
		mav.setViewName("ernie/items/list");
		return mav;
	}

	@RequestMapping(value = "/{id}/new", method = RequestMethod.GET)
	public ModelAndView save(@PathVariable("id") Long id) throws AppBizException {
		ModelAndView mav = new ModelAndView();
		Ernie ernie = ernieService.findById(id);
		int otherProbability = ernieItemService.findOtherProbability(id);
		mav.addObject("ernieID", id);
		mav.addObject("ernieTitle", ernie.getTitle());
		mav.addObject("otherProbability", otherProbability);
		mav.setViewName("ernie/items/new");
		return mav;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("ernieItem") ErnieItem ernieItem,
			@RequestParam("fileInput") List<CommonsMultipartFile> fileInputxs,
			RedirectAttributes redirectAttributes, HttpServletRequest request)
			throws AppBizException {
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {

					List<Photo> photos = ernieService.uploadThumb(fileInput,
							(MultipartHttpServletRequest) request);
					ernieItem.setImage(imgPath + photos.get(2).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		long id = ernieItem.getErnie().getId();
		Ernie ernie = ernieService.findById(id);
		ernieItem.setErnie(ernie);
		ernieItem.setUid(getCurrentUID());
		ernieItemService.save(ernieItem);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/ernieItems/"+id+"/items";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id,Model model) throws AppBizException {
		ErnieItem ernieItem = ernieItemService.findById(id);
		long ernieId = ernieItem.getErnie().getId();
		int otherProbability = ernieItemService.findOtherProbability(ernieId);
		model.addAttribute("otherProbability", otherProbability - ernieItem.getProbability().intValue());
		model.addAttribute("ernieID", ernieId);
		model.addAttribute("ernieTitle", ernieItem.getErnie().getTitle());
		model.addAttribute("ernieItem", ernieItem);
		return "ernie/items/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("ernieItem") ErnieItem ernieItem,
			@RequestParam("fileInput") List<CommonsMultipartFile> fileInputxs,
			RedirectAttributes redirectAttributes, HttpServletRequest request)
			throws AppBizException {
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {

					List<Photo> photos = ernieService.uploadThumb(fileInput,
							(MultipartHttpServletRequest) request);
					ernieItem.setImage(imgPath + photos.get(2).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		long id = ernieItem.getErnie().getId();
		System.out.println("==========="+id);
		Ernie ernie = ernieService.findById(id);
		ernieItem.setErnie(ernie);
		ernieItem.setUid(getCurrentUID());
		ernieItemService.update(ernieItem);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/ernieItems/"+id+"/items";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String itemsDelete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) throws ServletException, IOException{
		ernieItemService.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "恭喜，您删除奖项成功!");
		ErnieItem ernieItem = ernieItemService.findById(id);
		long ernieId = ernieItem.getErnie().getId();
		return "redirect:/mgr/ernieItems/"+ernieId+"/items";
	}

	@ModelAttribute("ernieCates")
	public Collection<Dictionary> getErniesCates() {
		return dictViewService.getSelectModelCollection(Constants.ERNIE_CATE);
	}
	
	/*****************************博饼活动***************************/
	
	
	
	
	@ModelAttribute("ernieBobing")
	public Collection<Dictionary> getErniesBobing() {
		return dictViewService.getSelectModelCollection(Constants.BOBING);
	}
	
	@RequestMapping(value = "/bobing/{id}/items", method = RequestMethod.GET)
	public ModelAndView bobingList(
			@PathVariable("id") Long id,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize)
			throws AppBizException {
		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, new Sort(
				Direction.DESC, new String[] { "id" }));
		Page<ErnieItem> ernies = ernieItemService.findAllErnieByUidAndErnieId(
				getCurrentUID(),id, pageable);
//		for (ErnieItem ernieItem : ernies) {
//			String b = ernieItem.getBobing();
//			if(b!=null&b!=""){
//				if(b.equals("yixiu")){
//					ernieItem.setBobing("一秀");
//				}else if(b.equals("erju")){
//					ernieItem.setBobing("二举");
//				}else if(b.equals("sanhong")){
//					ernieItem.setBobing("三红");
//				}else if(b.equals("sijin")){
//					ernieItem.setBobing("四进");
//				}else if(b.equals("duitang")){
//					ernieItem.setBobing("对堂");
//				}else if(b.equals("zhuangyuan")){
//					ernieItem.setBobing("状元");
//				}
//			}
//		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("ernieItems", ernies);
		mav.addObject("ernieID", id);
		mav.setViewName("ernie/items/bobingList");
		return mav;
	}

	@RequestMapping(value = "/bobing/{id}/new", method = RequestMethod.GET)
	public ModelAndView bobingNew(@PathVariable("id") Long id) throws AppBizException {
		ModelAndView mav = new ModelAndView();
		Ernie ernie = ernieService.findById(id);
		ErnieItem ernieItem = new ErnieItem();
		mav.addObject("ernieID", id);
		mav.addObject("ernieTitle", ernie.getTitle());
		mav.addObject("ernieItem", ernieItem);
		mav.setViewName("ernie/items/bobingNew");
		return mav;
	}

	@RequestMapping(value = "/bobing/save", method = RequestMethod.POST)
	public String bobingSave(@ModelAttribute("ernieItem") ErnieItem ernieItem,
			@RequestParam("fileInput") List<CommonsMultipartFile> fileInputxs,
			RedirectAttributes redirectAttributes, HttpServletRequest request)
			throws AppBizException {
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {

					List<Photo> photos = ernieService.uploadThumb(fileInput,
							(MultipartHttpServletRequest) request);
					ernieItem.setImage(imgPath + photos.get(2).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		long id = ernieItem.getErnie().getId();
		Ernie ernie = ernieService.findById(id);
		ernieItem.setErnie(ernie);
		ernieItem.setUid(getCurrentUID());
		ernieItemService.save(ernieItem);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/ernieItems/bobing/"+id+"/items";
	}

	@RequestMapping(value = "/bobing/{id}/edit", method = RequestMethod.GET)
	public String bobingUpdate(@PathVariable("id") Long id,Model model) throws AppBizException {
		ErnieItem ernieItem = ernieItemService.findById(id);
		long ernieId = ernieItem.getErnie().getId();
		model.addAttribute("ernieID", ernieId);
		model.addAttribute("ernieTitle", ernieItem.getErnie().getTitle());
		model.addAttribute("ernieItem", ernieItem);
		return "ernie/items/bobingEdit";
	}

	@RequestMapping(value = "/bobing/update", method = RequestMethod.POST)
	public String bobingUpdate(@ModelAttribute("ernieItem") ErnieItem ernieItem,
			@RequestParam("fileInput") List<CommonsMultipartFile> fileInputxs,
			RedirectAttributes redirectAttributes, HttpServletRequest request)
			throws AppBizException {
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {

					List<Photo> photos = ernieService.uploadThumb(fileInput,
							(MultipartHttpServletRequest) request);
					ernieItem.setImage(imgPath + photos.get(2).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		long id = ernieItem.getErnie().getId();
		Ernie ernie = ernieService.findById(id);
		ernieItem.setErnie(ernie);
		ernieItem.setUid(getCurrentUID());
		ernieItemService.update(ernieItem);
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/ernieItems/bobing/"+id+"/items";
	}

	@RequestMapping(value = "/bobing/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) throws ServletException, IOException{
		ernieItemService.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "恭喜，您删除奖项成功!");
		ErnieItem ernieItem = ernieItemService.findById(id);
		long ernieId = ernieItem.getErnie().getId();
		return "redirect:/mgr/ernieItems/bobing/"+ernieId+"/items";
	}
}
