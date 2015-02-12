package com.huake.saas.redis.quartz;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.huake.saas.redis.repository.AuthCfgRepository;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;

public class UpdateAuthCfg {
	
	@Autowired
	private List<AuthCfgRepository> authCfgRepository;
	
	@Autowired TenancyService tenancyService;
	
	public void doUpdateAuthCfg(){
		for(AuthCfgRepository a :authCfgRepository){
			for(Tenancy t : tenancyService.getAllTenancies()){
				a.cleanAuthCfg(String.valueOf(t.getUid()));
			}
		}
	}
}
