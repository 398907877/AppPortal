package com.huake.saas.weixin.service;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huake.saas.base.entity.BizMappingCfg;
import com.huake.saas.base.repository.BizMappingCfgDao;
import com.huake.saas.util.SpringUtils;
import com.huake.saas.weixin.aop.WebContext;
import com.huake.saas.weixin.model.AbstractWeixinMessage;
import com.huake.saas.weixin.model.FromWeixinEventMessage;
import com.huake.saas.weixin.model.ToWeixinTextMessage;
import com.huake.saas.weixin.model.WeixinKeywordRule;
import com.huake.saas.weixin.model.WeixinMenuword;
import com.huake.saas.weixin.repository.WeixinKeywordRuleDao;
import com.huake.saas.weixin.repository.WeixinMenuwordDao;

import freemarker.template.utility.StringUtil;
/**
 * 菜单事件处理
 * @author hxl
 *
 */
@Service("menuEventTargetAdapter")
public class MenuEventTargetAdapter implements MessageRender {
	private static final Logger logger = LoggerFactory.getLogger(TextEventTargetAdapter.class);
	@Autowired
	private WeixinMenuwordDao weixinMenuwordDao;
	
	@Autowired
	private WeixinKeywordRuleDao weixinKeywordRuleDao;
	@Autowired
	private BizMappingCfgDao bizMappingCfgDao;
	

	
	
	public AbstractWeixinMessage render(AbstractWeixinMessage message) {
	
		logger.debug("process menu event target...");
		if(message instanceof FromWeixinEventMessage){
			FromWeixinEventMessage from = (FromWeixinEventMessage)message;
			String word=from.getEventKey();
			System.out.println("keyword:"+word);
			//context 用于获取当前用户的id  
			WebContext context = WebContext.getInstance();
			context.setFromUserName(message.getFromUserName());
			context.setToUserName(message.getToUserName());
			List<WeixinMenuword> keywords=weixinMenuwordDao.findByUidAndWord(new Long(context.getUid()), word);
			WeixinMenuword keyword=null;
			if(keywords.size()>0){
				keyword=keywords.get(0);
			}
			if(null==keyword){
				System.out.println("keyword查询没有东西"); return null;
			}
			//获取对应规则
			WeixinKeywordRule rule = weixinKeywordRuleDao.findById(keyword.getRule().getId());

			if( rule.getBizCode() == null){
				System.out.println("rule查询没有东西");
				//返回默认值
				ToWeixinTextMessage to = new ToWeixinTextMessage();
				to.setFromUserName(context.getToUserName());
				to.setToUserName(context.getFromUserName());
				to.setContent(rule.getText());
				to.setCreateTime(System.currentTimeMillis());
				
				try{
					JAXBContext jaxb = JAXBContext.newInstance(ToWeixinTextMessage.class);  
		        	Marshaller marshaller = jaxb.createMarshaller();  
		        	marshaller.marshal(to, System.out);
		        	
				}catch(Exception e){
					e.printStackTrace();
				}
				
				return to;
			}else{
				//根据定义适配业务映射, TODO
				BizMappingCfg cfg = bizMappingCfgDao.findByBizCode( rule.getBizCode());
				if( cfg == null) {
					System.out.println("mapping没东西");
					logger.error("微信业务映射配置不正确:" + rule.getBizCode());
					return null;
				}	
				String[] params = StringUtil.split(rule.getParams(), ',');
				AbstractWeixinMessage to = (AbstractWeixinMessage)SpringUtils.invokeBeanMethod(cfg.getBeanName(), rule.getProxyInterface(), params);
				
				return to;
			}
		}
		
		return null;
	}
}
