package com.huake.saas.baidu.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletRequest;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushBroadcastMessageRequest;
import com.baidu.yun.channel.model.PushTagMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.SetTagRequest;
import com.huake.saas.account.service.ShiroDbRealm.ShiroUser;
import com.huake.saas.baidu.entity.BaiduDevice;
import com.huake.saas.baidu.entity.Message;
import com.huake.saas.baidu.entity.Tag;
import com.huake.saas.baidu.service.BaiduPushServise;
import com.huake.saas.baidu.service.JsonConfigFactory;
import com.huake.saas.baidu.service.SendClient;
import com.huake.saas.baidu.service.TagService;

import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.service.TenancyUserService;

/**
 * 
 * @author wujiajun 百度推送
 *
 */
@Controller("baiduController")
@RequestMapping(value = "/mgr/baidu/*")
public class BaiduPushController {
	private static final String PAGE_SIZE = "5";

	@Autowired
	private TenancyUserService tuserservice;
	@Autowired
	private BaiduPushServise bpushservice;
	@Autowired
	private TenancyUserService tenancyuserservice;
	@Autowired
	private TagService tagService;
	
	
	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}


	public TenancyUserService getTuserservice() {
		return tuserservice;
	}

	public void setTuserservice(TenancyUserService tuserservice) {
		this.tuserservice = tuserservice;
	}

	/**
	 * 百度推送页面（包含单人推送，与分组推送）
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String turnBaiduPush(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page2", defaultValue = "1") int pageNumber2,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {
	/*
	 * 单人推送条件分页查询
	 */
		Long userId = getCurrentUserId();
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		Page<TenancyUser> list = tuserservice.findByCondition(userId,
				searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("users", list);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets
				.encodeParameterStringWithPrefix(searchParams, "search_"));
		for (TenancyUser tenancyUser : list.getContent()) {
			System.out.println("用户名称有：" + tenancyUser.getName());
		}
/*
 * 分组推送分页查询
 */
		Page<Tag> list2=tagService.findByPage(pageNumber2, pageSize, sortType);
		model.addAttribute("tags", list2);
		return "baidupush/baidupush";

	}

	/**
	 * 用户设备的测试
	 * 
	 */
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {
		System.out.println(pageNumber + "页数111111111");
		System.out.println("进来了！！！");
		Long userId = getCurrentUserId();
		// logger.info("userId"+userId+"会员管理");
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		Page<TenancyUser> list = tuserservice.findByCondition(userId,
				searchParams, pageNumber, pageSize, sortType);

		for (TenancyUser tenancyUser : list) {

		}

		model.addAttribute("users", list);
		// model.addAttribute("sortType", sortType);
		// model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets
				.encodeParameterStringWithPrefix(searchParams, "search_"));
		for (TenancyUser tenancyUser : list.getContent()) {
			System.out.println("用户名称有：" + tenancyUser.getName());
		}
		return "baidupush/alertdiv";

	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}

	/**
	 * @author wujiajun
	 * @param BaiduDevice
	 * @return 成功 失败
	 */

	@RequestMapping(value = "push", method = RequestMethod.POST)
	public String push(
			  @RequestParam(value="deviceType")int deviceType,
			  @RequestParam(value="populationType")int peopleType,
			  @RequestParam(value="messageType")int messageType,
			  @RequestParam(value="memberId" ,defaultValue="0")long memberId,
			  @RequestParam(value="message")String message,
			  @RequestParam(value="title")String title,
			 @RequestParam(value="tagName",defaultValue="0")String tagName) {
// deviceType 推送类型（安卓，ios）
//  memberId  用户Id
// messageType  消息类型  		
// title 消息绑定名
// message  消息内容
// populationType  推送人群
// tagName  分组推送
		
		System.out.println("进来了push！！！");
		System.out.println("推送类型:"+deviceType);
		System.out.println("用户Id:"+memberId);
		System.out.println("消息类型:"+messageType);
		System.out.println("消息绑定名:"+title);
		System.out.println("消息内容"+message);
		System.out.println("推送人群:"+peopleType);
		System.out.println("分组推送:"+tagName);

		/**
		 * 注册BaiduChannelClient 重要!!!
		 */
		BaiduChannelClient sendclient = SendClient.getChannelClient();

		// ------------------------------------------------------

		switch (peopleType) {
		case 0:// 全部
			System.out.println("ALL");
			PushBroadcastMessageRequest request = new PushBroadcastMessageRequest();

			// 设置设备类型
			request.setDeviceType(deviceType);
			// 设置消息类型
			request.setMessageType(messageType);
			/*
			 * 将要发送的信息封装成message对象 注意：转换json
			 */
			Message msg = new Message();
			msg.setId(new Random().nextLong());
			msg.setTitle(title);
			msg.setMessage(message);
			Date date = new Date();
			SimpleDateFormat fomat = new SimpleDateFormat(
					"yyyy年MM月dd日 HH时mm分ss秒");
			String newdate = fomat.format(date);
			msg.setCrDate(newdate);
			// msg.setMemberId((long) device.getMemberId());

			JSONObject json = JSONObject.fromObject(msg,
					JsonConfigFactory.getInstance());

			System.out.println(json.toString() + "多人的数据");

			request.setMessage(json.toString());

			try {
				sendclient.pushBroadcastMessage(request);
			} catch (ChannelClientException e) {

				System.out.println("全部--处理客户端错误异常");
				e.printStackTrace();
			} catch (ChannelServerException e) {
				System.out.println("全部-- 处理服务端错误异常");

				e.printStackTrace();
			}

			break;
		case 1:// 单人

			System.out.println("ONE");

			// --------------------------

			PushUnicastMessageRequest onerequest = new PushUnicastMessageRequest();

			// 设置设备类型
			onerequest.setDeviceType(deviceType);
			// 设置消息类型
			onerequest.setMessageType(messageType);


			/*
			 * 将要发送的信息封装成message对象 注意：转换json
			 */
			Message onemsg = new Message();
			onemsg.setId(new Random().nextLong());
			onemsg.setTitle(title);
			onemsg.setMessage(message);
			Date onedate = new Date();
			SimpleDateFormat onefomat = new SimpleDateFormat(
					"yyyy年MM月dd日 HH时mm分ss秒");
			String onenewdate = onefomat.format(onedate);
			onemsg.setCrDate(onenewdate);
	

			JSONObject onejson = JSONObject.fromObject(onemsg,
					JsonConfigFactory.getInstance());

			System.out.println(onejson.toString() + "单人的数据");


			
			
			/*
			 * 单人的推送 需要获取到用户的信息 userid channlid
			 */
			// TODO 1.获取传过来的 memberid 2.查询数据库获取到userid+channlid
			 TenancyUser user=  tenancyuserservice.findById(memberId);
			  List<BaiduDevice>   devices=     user.getBaiduDevice();
			
			  System.out.println("当前用户有"+devices.size()+"个设备");
			  
			for (BaiduDevice baiduDevice : devices) {
				onerequest.setUserId(baiduDevice.getUserId());
				onerequest.setChannelId(baiduDevice.getChannelId());
				onerequest.setMessage(onejson.toString());
				
				try {
					sendclient.pushUnicastMessage(onerequest);
				} catch (ChannelClientException e) {
					// TODO Auto-generated catch block
					System.out.println("单人-- 处理客户端错误异常");
					e.printStackTrace();
				} catch (ChannelServerException e) {
					// TODO Auto-generated catch block
					System.out.println("单人-- 处理服务端错误异常");
					e.printStackTrace();
				}
			}
			

			break;
		case 2:// 分组
			System.out.println("TAG");

			// ------------------------------------------

			PushTagMessageRequest tagrequest = new PushTagMessageRequest();

			// 设置设备类型
			tagrequest.setDeviceType(deviceType);
			// 设置消息类型
			tagrequest.setMessageType(messageType);

			/*
			 * 将要发送的信息封装成message对象 注意：转换json
			 */
			Message tagmsg = new Message();
			tagmsg.setId(new Random().nextLong());
			tagmsg.setTitle(title);
			tagmsg.setMessage(message);
			Date tagdate = new Date();
			SimpleDateFormat tagfomat = new SimpleDateFormat(
					"yyyy年MM月dd日 HH时mm分ss秒");
			String tagnewdate = tagfomat.format(tagdate);
			tagmsg.setCrDate(tagnewdate);
			//tagmsg.setMemberId((long) device.getMemberId());

			JSONObject tagjson = JSONObject.fromObject(tagmsg,
					JsonConfigFactory.getInstance());

			System.out.println(tagjson.toString() + "tag的数据");

		
			tagrequest.setTagName(tagName);
			tagrequest.setMessage(tagjson.toString());
           System.out.println("分组插入了吗：？");
			try {
			
				sendclient.pushTagMessage(tagrequest);
			} catch (ChannelClientException e) {

				System.out.println("tag-- 处理客户端错误异常");
				e.printStackTrace();
			} catch (ChannelServerException e) {

				System.out.println("tag-- 处理服务端错误异常");
				e.printStackTrace();
			}

			break;
		default:
			System.out.println("OTHER");
			break;
		}

		return "redirect:/mgr/baidu/index";

	}//method end

	@RequestMapping(value = { "setTag"} , method = RequestMethod.GET)
	public String settag(@RequestParam(value="id") long tenancyuserid, @RequestParam(value="tag") String tag,RedirectAttributes redirectAttributes) {
 //测试接受页面数据
		System.out.println("11111111111111111");
		System.out.println("id是："+tenancyuserid+",tag是："+tag);
       List<Tag> lists= tagService.findAllTag();
       Boolean oop=true;
    for (Tag tag2 : lists) {
    
    	System.out.println("tag2.getName:"+tag2.getName());
		if(tag.equals(tag2.getName())){
			oop=false;
			break;
		}
	}
    //判段数据库是否存在此分组名称，如果不存在，新增
    if(oop){
    	System.out.println("不存在");
    	Tag  tag3=new Tag();
    	tag3.setName(tag);
    	tagService.saveTag(tag3);
    }
    
    
   
		// 获取到当期选择用户对象
		TenancyUser tenancyuser = tenancyuserservice.findById(tenancyuserid);

		List<BaiduDevice> baiduDevices = tenancyuser.getBaiduDevice();

		// 找出 当前用户还在使用的设备 然后进行推送
		List<BaiduDevice> inuseDevice = new ArrayList<BaiduDevice>();

		for (BaiduDevice baiduDevice : baiduDevices) {
			if (baiduDevice.getStatus() == 0) {
				inuseDevice.add(baiduDevice);
				
			}

		}

		BaiduChannelClient client = SendClient.getChannelClient();

		SetTagRequest req = new SetTagRequest();

		// 对当前用户的所有可用设备进行分组
		for (BaiduDevice baiduDevice : inuseDevice) {

			req.setUserId(baiduDevice.getUserId());
			req.setTag(tag);

			try {
				client.setTag(req);
			} catch (ChannelClientException e) {

				e.printStackTrace();
			} catch (ChannelServerException e) {

				e.printStackTrace();
			}

		}
	redirectAttributes.addFlashAttribute("message", "设置用户分组成功");
	return	"redirect:/mgr/tenancyUser/index";
	}//method end
	
	
}
