package com.huake.saas.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huake.saas.base.entity.BizMappingCfg;
import com.huake.saas.base.repository.BizMappingCfgDao;



@Component
public class BizMappingCfgService {
	
	@Autowired
	private  BizMappingCfgDao  BizMappingCfgDao;
	
	public List<BizMappingCfg> findAllBizmapping(){
		
		
		
		
		
		return  (List<BizMappingCfg>) BizMappingCfgDao.findAll();
	}
	public   BizMappingCfg findByBizCode(String bizCode){
		return BizMappingCfgDao.findByBizCode(bizCode);
	}
	

}
