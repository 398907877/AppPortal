package com.huake.saas.weixin.service;

import java.util.ArrayList;
import java.util.List;











import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONObject;

import com.huake.saas.account.service.ShiroDbRealm.ShiroUser;
import com.huake.saas.baidu.service.JsonConfigFactory;
import com.huake.saas.weixin.model.WeixinCfg;
import com.huake.saas.weixin.model.WeixinTreeNode;
import com.huake.saas.weixinmenu.model.MenuButton;
import com.huake.saas.weixinmenu.model.MenuClick;
import com.huake.saas.weixinmenu.model.MenuView;
import com.huake.saas.weixinmenu.model.Subbutton;

@Service("weixinapiapi")
public class MenuService {
	@Autowired
	private  WeixinTreeNodeService wxtreeDao; 
	


	@Autowired
	private WeixinService weixinService;
	

	

	/**
	 * 数据保存到对象，形成微信菜单,
	 */
	public  JSONObject weixinMenuJson(){ 
		
		//获取到根的 id  ，用于查询子集
		
		WeixinTreeNode root=  wxtreeDao.findRootByPidAndUid(0L, getCurrentUID());
		long rootid=root.getId();
		
		
		MenuButton button=new MenuButton();
		 List<Object> obj=new ArrayList<Object>();
		 
		List<WeixinTreeNode> weixinTreeNodes=wxtreeDao.findByPidAndUid(rootid,getCurrentUID());
		for (WeixinTreeNode weixinTreeNode : weixinTreeNodes) {
		List<WeixinTreeNode> treeNodes=	wxtreeDao.findByPidAndUid(weixinTreeNode.getId(),getCurrentUID());
			//判断是否有子集 
			if(treeNodes.size()<=0){
				if(weixinTreeNode.getMenutype().equals("1")){
					 MenuClick one1=new MenuClick();
					 one1.setKey(weixinTreeNode.getMenukey());
					 one1.setName(weixinTreeNode.getName());
					 one1.setType("click");
					 obj.add(one1);
					 
				}
				else{
					 MenuView one1 =new MenuView();
					 one1.setUrl(weixinTreeNode.getMenukey());
					 one1.setName(weixinTreeNode.getName());
					 one1.setType("view");
					 obj.add(one1);
				}
			
				
			}
			//有子集时
			else{
				 Subbutton two2=new Subbutton();
				 two2.setName(weixinTreeNode.getName());
				 List<Object> sub=new ArrayList<Object>();
			for (WeixinTreeNode weixinTreeNode2 : treeNodes) {
			    if(weixinTreeNode2.getMenutype().equals("1")){
			    	MenuClick one1=new MenuClick();
			    	 one1.setKey(weixinTreeNode2.getMenukey());
					 one1.setName(weixinTreeNode2.getName());
					 one1.setType("click");
			        sub.add(one1);
			    }else{
			    	 MenuView one1 =new MenuView();
			    	 one1.setUrl(weixinTreeNode2.getMenukey());
					 one1.setName(weixinTreeNode2.getName());
					 one1.setType("view");
				     sub.add(one1);
			    }
			   two2.setSub_button(sub); 
			}
			
			obj.add(two2);
			}
			

			
		}
		button.setButton(obj);	
		//将对象转换成json
	
			 JSONObject jsonn = JSONObject.fromObject(button,
						JsonConfigFactory.getInstance());
			 System.out.println("json是："+jsonn.toString());
			 return jsonn;
	}
	
	public Long getCurrentUID() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.getUid();
	}
	
	
	
	//获取access_token
	public String getAccessToken(){

		RestTemplate  restTemplate=new  RestTemplate();
		//找到当前uid 对应的cfg 然后取得 appid  secret
		WeixinCfg cfg = weixinService.getWeixinCfg(getCurrentUID());
		
		String appid =cfg.getAppid();
		String secret = cfg.getSecret();
		
		
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret+"";
		
		
		
		
		//TODO   从  cfg 表中拿取数据
		
	

		String  rsp = restTemplate.getForObject(url, String.class, new String[]{});
	     System.out.println(rsp);
		
		JSONObject  json =   JSONObject.fromObject(rsp);
		String  acc =  json.getString("access_token");
		
		
		
		
		return acc;
		
	}
	//发送POST
	public  String menuPost(String  url,Object  body){
		RestTemplate  restTemplate=new  RestTemplate();
		
		  String  back= restTemplate.postForObject(url, body, String.class, new String[]{});
		  
		  return back;
	}
	
	//发送Get
	public  String menuGet(String  url){

		RestTemplate  restTemplate=new  RestTemplate();
		String back = restTemplate.getForObject(url, String.class, new String[]{});
		  
		  return back;
	}
	
	
	
	  
	}

