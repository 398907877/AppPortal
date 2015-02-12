package com.huake.saas.version.web.api.v1;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huake.saas.version.entity.AppVersionInfo;
import com.huake.saas.version.service.AppVersionInfoService;




/**
 * 软件版本检查api.
 * @author zhiyong
 *
 */
@Controller
@RequestMapping("/api/v1/versions")
public class VersionController {

	@Resource(name = "appVersionInfoService")
	private AppVersionInfoService verInfoService;
	
	@RequestMapping(value = "/now/{mobile}", method = RequestMethod.GET,  produces="application/json")
    @ResponseBody
	public AppVersionInfo checkCurrentVersion(@PathVariable final String mobile){
		AppVersionInfo avi = verInfoService.getLastestAppVersionInfo(mobile);
		
		if( avi != null) return avi;
		
		return new AppVersionInfo();
	}
}
