package com.huake.saas.supply.web.api.v1;

import java.util.HashMap;
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

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.web.controller.BaseApiController;
import com.huake.saas.supply.entity.SupplyDemand;
import com.huake.saas.supply.service.SupplyDemandService;

@Controller
@RequestMapping(value = "/api/v1/supply")
public class SupplyDemandApiController extends BaseApiController{

	@Autowired
	private SupplyDemandService supplyDemandService;

	  /**
	   * @param type   类型id
	   * @param type   供求类型
	   * @param supplyDemandType   信息需求类型
	   * @param first 
	   * @param max
	   * @return SupplyDemandDetail
	   */
		@RequestMapping(value = "/list", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
		@ResponseBody
		@ResponseStatus(value = HttpStatus.OK)
		public List<SupplyDemand> getSupplyDemand(
				@RequestParam(value = "uid", required = true) Long uid,
				@RequestParam(value = "type",defaultValue="0", required = false) String type,
				/*@RequestParam(value = "typeId", required = false) String typeId,*/
				@RequestParam(value = "title", required = false) String title,
				@RequestParam(value = "pageNum",defaultValue="1", required = false) Integer pageNum){
	      /* List<SupplyDemandType> supplys = supplyDemandTypeService.getSupplyDemandTypesAndType(Integer.parseInt(BaseEntity.STATUS_VALIDE),SupplyDemandType.TYPE_VALUE_SUPPLY);
	       List<SupplyDemandType> demands = supplyDemandTypeService.getSupplyDemandTypesAndType(Integer.parseInt(BaseEntity.STATUS_VALIDE),SupplyDemandType.TYPE_VALUE_DEMAND);*/
	     /*  
	      * 供求里面具体类型
	      * if(null==typeId||"".equals(typeId)){
	          if(null==type||"".equals(type)){
	    	     type = String.valueOf(SupplyDemandType.TYPE_VALUE_SUPPLY);
	          }
	          if(type.equals(String.valueOf(SupplyDemandType.TYPE_VALUE_SUPPLY))){
		    	   typeId = String.valueOf(supplys.get(0).getId());
		       }else{
		    	   typeId = String.valueOf(demands.get(0).getId());
		       }
	       }else{
	    	   SupplyDemandType demandType = supplyDemandTypeService.getSupplyDemandType(Long.parseLong(typeId));
	    	   type=String.valueOf(demandType.getType());
	       }*/
	       Map<String, Object> searchParams = new HashMap<String, Object>();
	       searchParams.put("EQ_type",type);
	      /* searchParams.put("EQ_typeId", typeId);*/
	       searchParams.put("LIKE_title", title);
	       searchParams.put("EQ_status", BaseEntity.STATUS_VALIDE);
	       List<SupplyDemand> supplyDemands = supplyDemandService.getUserSupplyDemandApi(uid,
					searchParams, pageNum, BaseEntity.DATE_MAX, BaseEntity.PAGE_CRTDATE_DESC).getContent();
	       
	       /*SupplyDemandDetail demandDetail = new SupplyDemandDetail();*/
	       /*demandDetail.setSupplys(supplys);
	       demandDetail.setDemands(demands);*/
	       /*demandDetail.setSupplyDemands(supplyDemands);*/
	       return supplyDemands;
		}
		
		
		/**
		 * 供求信息详情
		 * @param id(供求信息Id)
		 * @return SupplyDemandApi
		 */
		@RequestMapping(value="/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
		@ResponseBody
		@ResponseStatus(value=HttpStatus.OK)
		@Deprecated
		public SupplyDemand getDetail(@PathVariable final Long id,@RequestParam(value = "uid", required = true) Long uid){
			return supplyDemandService.savaSupplyDemandDetail(supplyDemandService.getSupplyDemand(id));
		}
		
		/**
		 * 发布供求信息详情
		 * @param supplyDemand
		 * @return
		 */
		@RequestMapping(value="/publish",method = RequestMethod.POST, produces="application/json;charset=UTF-8")
		@ResponseBody
	    @ResponseStatus(value=HttpStatus.OK)
		public Map<String,String> publish(@RequestBody SupplyDemand supplyDemand){
			supplyDemandService.saveSupplyDemand(supplyDemand);
			return SUCCESS_RESULT;
		}
}
