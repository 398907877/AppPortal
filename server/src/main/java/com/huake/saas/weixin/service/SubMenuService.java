package com.huake.saas.weixin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.huake.saas.weixin.model.SubMenu;
import com.huake.saas.weixin.repository.SubMenuDao;

/**
 * 
 * @author wujiajun
 *
 */

@Service("submenuservice")
public class SubMenuService {
	
	
	@Autowired
	private SubMenuDao   SubMenuDao;
	
	public void save(SubMenu entity){
		
		SubMenuDao.save(entity);

		
	}
	
	public List<SubMenu> getAllSubmenu(){
		
		return  (List<SubMenu>) SubMenuDao.findAll();

	}

	public long count(){
		
		return  SubMenuDao.count();

	}

	
}
