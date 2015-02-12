package com.huake.saas.addresslist.web.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.huake.saas.addresslist.entity.AddressList;
import com.huake.saas.addresslist.entity.AddressListApi;
import com.huake.saas.addresslist.entity.AddressListType;
import com.huake.saas.addresslist.service.AddressListService;
import com.huake.saas.addresslist.service.AddressListTypeService;
import com.huake.saas.base.web.controller.BaseApiController;
import com.huake.saas.place.entity.PlaceRelation;
import com.huake.saas.place.service.PlaceRelationService;

@Controller
@RequestMapping(value = "/api/v1/addressList")
public class AddressListApiController extends BaseApiController{
	
	@Autowired	
	private AddressListService service;
	
	@Autowired	
	private AddressListTypeService addressListTypeService;
	
	@Autowired
	private PlaceRelationService placeService;
	
	/**
	 * 通讯录列表
	 * @param uid
	 * @param deptId
	 * @param first
	 * @param max
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET,  produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<AddressListApi> getAddress(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestParam(value="cid",defaultValue="0",required=true)long cid){
		
		List<AddressList> listAdd = new ArrayList<AddressList>();
		List<AddressListApi> listAddApi = new ArrayList<AddressListApi>();
		if(cid != 0){
			listAdd.addAll(service.findByDeptId(cid,uid));
		}else{
			listAdd.addAll(service.findAll(uid));
		}
		for(int i=0;i<listAdd.size();i++){
			listAddApi.add(formatAddressList(listAdd.get(i)));
		}
		
		return listAddApi;
	}
	
	private AddressListApi formatAddressList(AddressList addressList){
		AddressListApi addApi = new AddressListApi();
		addApi = service.getAddressListApi(addressList);
		if(addressList.getProvince() != null){
			PlaceRelation province = placeService.findProvinceById(Integer.valueOf(addressList.getProvince()));
			addApi.setProvince(province.getName());
		}
		if(addressList.getProvince() != null){
			PlaceRelation city = placeService.findCitysName(addressList.getCity());
			addApi.setCity(city.getName());
		}
		if(!AddressList.PUBLIC.equals(addressList.getPublicTel())){
			if(addressList.getTel() != null && !addressList.getTel().trim().equals("")){
				addApi.setTel(replaceSubString(addressList.getTel(),4));
			}
		}
		if(!AddressList.PUBLIC.equals(addressList.getPublicPhone())){
			if(addressList.getOfficePhone() != null && !addressList.getOfficePhone().trim().equals("")){
				addApi.setOfficePhone(replaceSubString(addressList.getOfficePhone(),4));
			}
		}
		if(!AddressList.PUBLIC.equals(addressList.getPublicEmail())){
			String email = addressList.getEmail();
			if(email != null && !email.trim().equals("")){
				addApi.setEmail(StringUtils.leftPad(email.substring(email.lastIndexOf("@")), 15, '*'));
			}
		}
		return addApi;
	}
	
	/**
	 * 把字符串的后n位用“*”号代替
	 * @param str  要代替的字符串
	 * @param n   代替的位数
	 * @return
	 */
    private String replaceSubString(String str,int n){
    	String sub="";
		sub = str.substring(0, str.length()-n);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<n;i++){
			sb=sb.append("*");
		}
		sub+=sb.toString();
    	return sub;
    }
	/**
	 * 通讯录详情（单个人）
	 * @param id(通讯录Id)
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value=HttpStatus.OK)
	public AddressListApi detail(@PathVariable final Long id,@RequestParam(value = "uid", required = true) Long uid){
		AddressList add = service.findById(id);
		formatAddressList(add);
		return formatAddressList(add);
	}
	
	/**
	 * 获取通讯录分组列表
	 * @param uid
	 */
	@RequestMapping(value="/category",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value=HttpStatus.OK)
	public List<AddressListType> getCategory(@RequestParam(value = "uid", required = true)Long uid){
		List<AddressListType> list = new ArrayList<AddressListType>();
		AddressListType all = new AddressListType();
		all.setId(Long.valueOf(0));
		all.setName("全部");
		all.setUid(uid.toString());
		all.setStatus(AddressListType.STATUS_VALIDE);
		list.add(all);
		list.addAll(addressListTypeService.getAddressListTypes(AddressListType.STATUS_VALIDE, uid.toString()));
		
 		return list;
	}
}
