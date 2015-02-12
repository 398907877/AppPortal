package com.huake.saas.tenancy.web.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;

@Controller("tenancyApiController")
@RequestMapping(value = "/api/v1/tenancy")
public class TenancyController {
	@Autowired
	private TenancyService tenancyService;
	
	@RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Tenancy getTenancy(@RequestParam(value="uid",required=true)Long uid){
		return tenancyService.findByUid(uid);
	}
}
