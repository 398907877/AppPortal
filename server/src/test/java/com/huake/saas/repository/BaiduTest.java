package com.huake.saas.repository;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.huake.saas.activity.repository.EventDao;
import com.huake.saas.baidu.entity.BaiduDevice;
import com.huake.saas.baidu.service.BaiduPushServise;
import com.huake.saas.category.entity.BaiduCategory;
import com.huake.saas.category.repository.BaiduCategoryDao;
import com.huake.saas.news.repository.NewsDao;
import com.huake.saas.news.service.NewsWeixinTargetImpl;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.repository.TenancyUserDao;
import com.huake.saas.util.SpringUtils;
import com.huake.saas.weixin.annonation.WeixinTargetName;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;


@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(defaultRollback = false)
public class BaiduTest  extends   SpringTransactionalTestCase{
	@Autowired
	private   BaiduPushServise   BaiduPushServise;

	@Autowired
	private BaiduCategoryDao  baiduCategoryDao; 
	@Autowired
	private TenancyUserDao tenancyUserDao;

	@Test
	public void testsave(){
		BaiduDevice baiduDevice= new BaiduDevice();
	//	baiduDevice.setCategrory("fuzhou");
	//	baiduDevice.setChannelId("22222");
		baiduDevice.setUserId("1111");
		baiduDevice.setCrtDate(new Date());
	//	baiduDevice.setMemberId(888888888);
		
		BaiduPushServise.addDevice(baiduDevice);
		
		
		
	}
	@Test
	public void testDevice() throws Exception {
		BaiduDevice baiduDevice=new BaiduDevice();
		baiduDevice.setTitle("lalalallall");
		BaiduPushServise.addDevice(baiduDevice);
	}
	@Test
	public void  testjson(){
		
		BaiduDevice  baiduDevice = new BaiduDevice();
		baiduDevice.setDeviceType(3);
		baiduDevice.setUserId("11111");
		
		JSONObject  json  =JSONObject.fromObject(baiduDevice);
				
		System.out.println(json);
		
	}
/**
 * 测试设备，分类，用户的插入与查询	
 */
	@Test
	public void testsaveD() throws Exception {
		BaiduDevice baiduDevice=new BaiduDevice();
		BaiduDevice baiduDevice2=new BaiduDevice();
		
		TenancyUser tenancyUser=new TenancyUser();
		TenancyUser tenancyUser2=new TenancyUser();
		
	   BaiduCategory baiduCategory=new BaiduCategory();	
	   BaiduCategory baiduCategory2=new BaiduCategory();
	  //插入设备数据,创建日期
	   baiduDevice.setCrtDate(new Date());
	   baiduDevice2.setCrtDate(new Date());
	   //与分类的关系
	   baiduCategory.setName("类型001");
	   baiduCategory2.setName("类型002");
	   List<BaiduCategory> list2=new ArrayList<BaiduCategory>();
	   list2.add(baiduCategory);
	   list2.add(baiduCategory2);
	   baiduDevice.setBaiCategory(list2);
	   baiduCategory.setBaiduDevice(baiduDevice);
	   baiduCategory2.setBaiduDevice(baiduDevice);
	   //与用户的关联关系
	   tenancyUser.setUnit("编号001");
	   tenancyUser2.setUnit("编号002");
	   List<BaiduDevice> list1=new ArrayList<BaiduDevice>();
	   list1.add(baiduDevice);
	   list1.add(baiduDevice2); 
	   tenancyUser.setBaiduDevice(list1);
	   baiduDevice.setTenancyUser(tenancyUser);
	   baiduDevice2.setTenancyUser(tenancyUser);
	   //数据持久化
	   BaiduPushServise.addDevice(baiduDevice2);
	   BaiduPushServise.addDevice(baiduDevice);
	   tenancyUserDao.save(tenancyUser);
	   tenancyUserDao.save(tenancyUser2);
	   baiduCategoryDao.save(baiduCategory);
	   baiduCategoryDao.save(baiduCategory2);
	   
	}
	
	
	/**
 * 测试设备，分类，用户的插入与查询	
 */
	@Test
	public void testsaveD1(){
		BaiduDevice baiduDevice=new BaiduDevice();
		BaiduDevice baiduDevice2=new BaiduDevice();
		
		TenancyUser tenancyUser=new TenancyUser();
		TenancyUser tenancyUser2=new TenancyUser();
		
	   BaiduCategory baiduCategory=new BaiduCategory();	
	   BaiduCategory baiduCategory2=new BaiduCategory();
	  //插入设备数据,创建日期
	   baiduDevice.setCrtDate(new Date());
	   baiduDevice2.setCrtDate(new Date());
	   //与分类的关系
	   baiduCategory.setName("类型001");
	   baiduCategory2.setName("类型002");
	   List<BaiduCategory> list2=new ArrayList<BaiduCategory>();
	   list2.add(baiduCategory);
	   list2.add(baiduCategory2);
	   baiduDevice.setBaiCategory(list2);
	   baiduCategory.setBaiduDevice(baiduDevice);
	   baiduCategory2.setBaiduDevice(baiduDevice);
	   //与用户的关联关系
	   tenancyUser.setUnit("编号001");
	   tenancyUser2.setUnit("编号002");
	   List<BaiduDevice> list1=new ArrayList<BaiduDevice>();
	   list1.add(baiduDevice);
	   list1.add(baiduDevice2); 
	   tenancyUser.setBaiduDevice(list1);
	   baiduDevice.setTenancyUser(tenancyUser);
	   baiduDevice2.setTenancyUser(tenancyUser);
	   //数据持久化
	   BaiduPushServise.addDevice(baiduDevice2);
	   BaiduPushServise.addDevice(baiduDevice);
	   tenancyUserDao.save(tenancyUser);
	   tenancyUserDao.save(tenancyUser2);
	   baiduCategoryDao.save(baiduCategory);
	   baiduCategoryDao.save(baiduCategory2);
	   
	}
	@Test
	public void testAnnotation() throws ClassNotFoundException{

		
		Object  object =SpringUtils.getBean("NewsWeixinTarget");
		
		
		Method[] methods= object.getClass().getMethods();
		WeixinTargetName   methodname=  object.getClass().getAnnotation(WeixinTargetName.class);

		
		for (int i = 0; i < methods.length; i++) {
		
			System.out.println(	methods[i].getName());
			System.out.println(methods[i]);
		}
		
	}

	
	
	

}
