package com.huake.saas.baidu.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;




import com.huake.saas.baidu.entity.BaiduDevice;
import com.huake.saas.baidu.repository.BaiduPushDao;



/**
 * @author wujiajun
 * @time 2014-5
 * @Description   设备信息  （BaiduDevice）    调用的服务
 * */
//Spring Bean的标识.
@Component
//类中所有public函数都纳入事务管理的标识.
@Transactional
public class BaiduPushServise {
	
	@Autowired
	private  BaiduPushDao baidupushdao;
	
	
	public BaiduPushDao getBaidupushdao() {
		return baidupushdao;
	}

	public void setBaidupushdao(BaiduPushDao baidupushdao) {
		this.baidupushdao = baidupushdao;
	}

	/**
	 * 用于增加设备
	 * 
	 */

	public void  addDevice( BaiduDevice baidudevice ){
		baidupushdao.save(baidudevice);

	}
	
	/**
	 * 用于删除设备
	 * 
	 */
	public void  delDevice( long id ){
		
		baidupushdao.delete(id);

	}
	
	public List<BaiduDevice> getall(){
		
	return	(List<BaiduDevice>) baidupushdao.findAll();	
		
	}
	
	
	
	
	
	

}
