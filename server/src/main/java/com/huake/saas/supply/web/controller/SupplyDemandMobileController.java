package com.huake.saas.supply.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.huake.saas.base.entity.AppBizException;
import com.huake.saas.base.web.controller.BaseMobileController;
import com.huake.saas.category.service.IndustryCategoryService;
import com.huake.saas.supply.entity.SupplyDemand;
import com.huake.saas.supply.entity.SupplyDemandPhoto;
import com.huake.saas.supply.service.SupplyDemandService;


@Controller
@RequestMapping(value = "/m/supply")
public class SupplyDemandMobileController extends BaseMobileController {

	@Autowired
	private SupplyDemandService supplyDemandService;
	
	@Autowired
	private IndustryCategoryService categoryService;
	
	/**
	 * 供求信息首页
	 * @param pageNum
	 * @param UID
	 * @param type
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(@RequestParam("uid")  final String UID, @RequestParam(value = "type",defaultValue="0", required = false) final Integer type, 
			@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum)throws AppBizException{
		ModelAndView mav = new ModelAndView();
		if(UID == null){
			throw new AppBizException("无效的访问");
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		String status = 1+"";
		searchParams.put("EQ_status", status.toString());
		if(type != null){
			searchParams.put("EQ_type", type.toString());
			mav.addObject("type", type);
		}
		Page<SupplyDemand> supplyDemands = supplyDemandService.getUserSupplyDemand(Long.parseLong(UID), searchParams, pageNum,5, "auto");
		for (SupplyDemand supplyDemand : supplyDemands) {
			String title = supplyDemand.getTitle();
			String introduce = supplyDemand.getIntroduce();
			if(title!=null&title!=""){
				if(title.length()>12){
					title = title.substring(0,12)+"...";
					supplyDemand.setTitle(title);
				}
			}
			if(introduce!=null&introduce!=""){
				if(introduce.length()>60){
					introduce = introduce.substring(0,60)+"...";
					supplyDemand.setIntroduce(introduce);
				}
			}
		}
		mav.addObject("UID", UID);
		mav.addObject("supplyDemands", supplyDemands);
		mav.setViewName("mobile/supply/index");
		return mav;
		}
	
	/**
	 * 分页获取供求信息列表。
	 * @param UID
	 * @param pageNum
	 * @param type
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<SupplyDemand> list(@RequestParam("UID")  final String UID,@RequestParam(value = "type",defaultValue="0", required = false) final Integer type, 
	@RequestParam(value = "pageNum",defaultValue="2", required = false) Integer pageNum)throws AppBizException{
		Map<String, Object> searchParams = new HashMap<String, Object>();
		String status = 1+"";
		searchParams.put("EQ_status", status.toString());
		if(type != null){
			searchParams.put("EQ_type", type.toString());
		}
		List<SupplyDemand> supplyDemands = supplyDemandService.getUserSupplyDemand(Long.parseLong(UID), searchParams, pageNum,5, "auto").getContent();
		for (SupplyDemand supplyDemand : supplyDemands) {
			String title = supplyDemand.getTitle();
			String introduce = supplyDemand.getIntroduce();
			if(title!=null&title!=""){
				if(title.length()>12){
					title = title.substring(0,12)+"...";
					supplyDemand.setTitle(title);
				}
			}
			if(introduce!=null&introduce!=""){
				if(introduce.length()>60){
					introduce = introduce.substring(0,60)+"...";
					supplyDemand.setIntroduce(introduce);
				}
			}
		}
		return supplyDemands;
	}
	
	/**
	 * 供求详细页面
	 * @param eventId
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable final Long id,@RequestParam(value = "type",required = false) final Integer type) throws AppBizException{
		SupplyDemand demand = supplyDemandService.getSupplyDemand(id);
		if(null == demand){
			throw new AppBizException("出现错误了", "Object No found.");
		}
		List<SupplyDemandPhoto> pictures = supplyDemandService.getPictures(demand.getId());
		ModelAndView mav = new ModelAndView();
		mav.addObject("pictures", pictures);
		mav.addObject("type", type);
		mav.addObject("demand", demand);
		mav.setViewName("mobile/supply/show");
		return mav;
	}
	}