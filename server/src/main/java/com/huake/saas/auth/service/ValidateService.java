package com.huake.saas.auth.service;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huake.saas.auth.entity.AuthCfg;
import com.huake.saas.auth.exception.AuthValidationException;
import com.huake.saas.auth.vrule.IVRule;

/**
 * 校验业务模块授权细则service
 * @author zjy
 */
@Component
public class ValidateService {
	
	@Autowired
	private Map<String,IVRule> rules;//校验规则集合
	
	/**
	 * 遍历校验规则集合，调用相对应校验规则check方法校验 
	 * 只要一个校验不通过，则校验结果为不通过
	 * @param uid 要校验的租户id
	 * @param rulesName 要校验的规则名称
	 * @param value 要校验的数据
	 * @return
	 */
	public void check(Long uid,String[] rulesName,Object value,AuthCfg useCfg)throws AuthValidationException{
		/**
		 * 没有要校验规则 不进行处理
		 */
		if(rulesName == null || rulesName.length == 0){
			return;
		}
		/**
		 * 遍历规则名称并校验相对应规则
		 */
		for(String ruleName : rulesName){
			for(Entry<String, IVRule> entry : rules.entrySet()){
				if(ruleName.equals(entry.getKey())){
					entry.getValue().check(uid,value,useCfg);//进入校验  抛出异常则为校验不通过
				}
			}
		}
	}
}
