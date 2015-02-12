package com.huake.saas.baidu.web.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.huake.saas.baidu.entity.BaiduDevice;
import com.huake.saas.baidu.service.BaiduPushServise;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;


/**
 * @author wujiajun
 * @time 2014-5
 * @Description  api 用于设备信息的处理 （CRUD） ...
 * */



@Controller
@RequestMapping(value={"/api/v1/push/*"})
public class PushApiController {
	
	
	public static final Map<String , Object> 	SUCCESSRETURN=new  HashMap<String, Object>();
	
	static{
		
		SUCCESSRETURN.put("success", "true");
		
	}
	
	
	@Autowired
	private BaiduPushServise  BaiduPushServise;
	@Autowired
	private   TenancyUserService    TenancyUserService;
	
	/**
	 * 
	 * @param baidudevice
	 * @return 状态
	 */

	
	@RequestMapping(value={"addDevice"},method=RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object>  addDevice(@RequestBody BaiduDevice baidudevice){
		//1.把数据添加到设备表
		//2.建立和tancyuser的关系
		
		/**
		 * 1.userId+channelId的组合 是唯一的 对应一个baidudevice
		 * 流程：userId，channelId 先判断是否存在
		 *         存在：更新device的状态
		 *         不存在：添加记录
		 *         
		 *  2.建立和tancyuser的关系       
		 */
		
		
		
		System.out.println("ininininininin");
		
		Date nowdate= new Date();
		baidudevice.setCrtDate(nowdate);
	//	baidudevice.setCategrory("地区福州");

		TenancyUser user= TenancyUserService.findById(baidudevice.getMemberId());
		
		baidudevice.setTenancyUser(user);

		BaiduPushServise.addDevice(baidudevice);
		
		
		return SUCCESSRETURN;
		
	}
	
	@ResponseBody
	@RequestMapping(value={"geimsg"},method=RequestMethod.GET)
	public  List<BaiduDevice> geimsg(){
		return BaiduPushServise.getall();
	}
	
	

}
