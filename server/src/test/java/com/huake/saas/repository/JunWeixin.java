package com.huake.saas.repository;



import java.util.ArrayList;
import java.util.List;





import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;





import com.huake.saas.baidu.service.JsonConfigFactory;
import com.huake.saas.weixin.model.ClickMenu;
import com.huake.saas.weixin.model.SubMenu;
import com.huake.saas.weixin.model.ViewMenu;
import com.huake.saas.weixin.model.WeixinTreeNode;
import com.huake.saas.weixin.repository.SubMenuDao;
import com.huake.saas.weixin.repository.WeixinTreeNodeDao;

import com.huake.saas.weixin.service.MenuService;
import com.huake.saas.weixinmenu.model.HttpRequest;
import com.huake.saas.weixinmenu.model.MenuButton;
import com.huake.saas.weixinmenu.model.MenuClick;
import com.huake.saas.weixinmenu.model.MenuView;
import com.huake.saas.weixinmenu.model.Subbutton;







@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(defaultRollback = false)
public class JunWeixin  extends   SpringTransactionalTestCase{
	
	
	@Autowired
	private SubMenuDao submenudao;
	
	
	private static final String  URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx0de57dcbf14bc60e&secret=cec0293d5f620807bae15a08399455b3";
	
	private static final String URL1="https://api.weixin.qq.com/cgi-bin/menu/create";
	
	
	@Autowired
	private  WeixinTreeNodeDao wxtreeDao; 



	

	
	
	
	@Test
	public void testsubmen(){
		
		SubMenu sub = new SubMenu();
		sub.setName("1");
		
		
		java.util.List<ClickMenu>  list1 = new ArrayList<ClickMenu>();
		
		ClickMenu click = new ClickMenu();
		
		click.setSubMenu(sub);
		click.setName("1-1");
		click.setEvenkey("key");;
		list1.add(click);
		
		java.util.List<ViewMenu>  list = new ArrayList<ViewMenu>();
		
		ViewMenu view = new ViewMenu();
		view.setSubMenu(sub);
		view.setName("1-2");
		view.setUrl("baidu.com");
		list.add(view);
		
		sub.setClickmenu(list1);
		sub.setViewmenu(list);
		
		submenudao.save(sub);	
	}
	/**
	 * 菜单测试
	 */
	
	
	
	
	
	@Autowired
	private  WeixinTreeNodeDao WeixinTreeNodeDao;
	
	@Test
	public void testdelete(){
		
		WeixinTreeNode node= WeixinTreeNodeDao.findByTreeid(104);
		System.out.println(node.getName());
		
		
	}
	
	//测试生成 json 
	@Test
	public void testweixinmenutojson(){
		
	  List<WeixinTreeNode>   nodes =	 (List<WeixinTreeNode>) WeixinTreeNodeDao.findAll();
		for (WeixinTreeNode weixinTreeNode : nodes) {
			//第一层  pid=2
			List<WeixinTreeNode>  fathernode = new ArrayList<WeixinTreeNode>();
			if(weixinTreeNode.getpId()==2){
				fathernode.add(weixinTreeNode);
			}
			
			//fathernode父级菜单   
			
			
			
			
			
		}
		
		
		
		
	}

	
	@Test
	public void databasetoweixin(){

		
		
		
		
	}
	
	
	
	@Test
	public  void weixinMenu(){
		 MenuButton button=new MenuButton();
		 List<Object> obj=new ArrayList<Object>();
		 
		List<WeixinTreeNode> weixinTreeNodes=wxtreeDao.findByPidAndUid(1L,20140417122551L);
		for (WeixinTreeNode weixinTreeNode : weixinTreeNodes) {
		List<WeixinTreeNode> treeNodes=	wxtreeDao.findByPidAndUid(weixinTreeNode.getId(),20140417122551L);
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
	
	}
	

	
	
}

  







