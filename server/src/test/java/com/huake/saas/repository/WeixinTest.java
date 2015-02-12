package com.huake.saas.repository;
/**
 * 微信测试
 * hxl
 */
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.huake.saas.baidu.service.JsonConfigFactory;
import com.huake.saas.product.entity.Product;
import com.huake.saas.product.repository.ProductDao;
import com.huake.saas.weixin.repository.WeixinKeywordDao;
import com.huake.saas.weixin.repository.WeixinKeywordRuleDao;
import com.huake.saas.weixin.service.MenuService;
import com.huake.saas.weixin.service.WeixinTreeNodeService;
import com.huake.saas.weixinmenu.model.HttpRequest;
import com.huake.saas.weixinmenu.model.MenuButton;
import com.huake.saas.weixinmenu.model.MenuClick;
import com.huake.saas.weixinmenu.model.MenuView;
import com.huake.saas.weixinmenu.model.Subbutton;

import freemarker.template.Template;


@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(defaultRollback = false)
public class WeixinTest extends SpringTransactionalTestCase{
@Autowired
private WeixinKeywordDao weixinKeywordDao;
@Autowired
private WeixinKeywordRuleDao  weixinKeywordRuleDao;
@Autowired
private ProductDao  productDao; 
@Autowired
private  WeixinTreeNodeService wxtreeDao; 

/**
 * 删除规则
 * @throws Exception
 */
@Test
public void testDelete() throws Exception {
	weixinKeywordRuleDao.delete(23l);
}
/**
 * 产品测试
 */
@Test
public void testProduct() throws Exception {
Product p=	productDao.findOne(2l);
     System.out.println("产品是："+p.getPictures());

   /* for (ProductPictures pic : p.getPictures() ) {
		System.out.println("地址是："+pic.getUrl());
	}*/
	
}
/**
 * 关键词
 */

public static JSONObject testmenu() throws Exception {
	 MenuButton button=new MenuButton();
     MenuClick one2=new MenuClick();
    Subbutton one1=new Subbutton();
	 MenuView thr =new MenuView();
	 
     one2.setKey("1");
	 one2.setType("click");
	 one2.setName("一级");
	 
	 
     thr.setUrl("www.baidu.com");
	 thr.setName("二级");
	 thr.setType("view");
	
	 
	 
	 List<Object> list1=new ArrayList<Object>();
	 list1.add(thr);
	 one1.setName("菜单1");
	 one1.setSub_button(list1);
	  
	  List<Object> listAll=new ArrayList<Object>();
    listAll.add(one1);
	  listAll.add(one2);
	  
	  button.setButton(listAll);
	 JSONObject json = JSONObject.fromObject(button,
				JsonConfigFactory.getInstance());
	return json;
	 
	 
}
/**
 * 测试http发送请求
 * @throws Exception
 */
private  static final String  URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx0de57dcbf14bc60e&secret=cec0293d5f620807bae15a08399455b3";
private static final String URL1="https://api.weixin.qq.com/cgi-bin/menu/create";
@Test
public void testHttp() throws Exception {
	
 String param=null;


 JSONObject json=HttpRequest.httpRequest(URL, null);
 
  
  param="?access_token="+json.getString("access_token");
  String hurl=URL1+param;

  JSONObject testmenu = testmenu();
  System.out.println("hurl11111111"+hurl);
  System.out.println("testmen22222"+testmenu.toString());
  HttpRequest.httpRequest(hurl, testmenu.toString());


  
  
}
@Test
public void testString() throws Exception {
	String str="\"123";
	System.out.println(str);
}



@Test
public void testGet(){
	
	RestTemplate  RestTemplate = new RestTemplate();
	
	String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx0de57dcbf14bc60e&secret=cec0293d5f620807bae15a08399455b3";
	
	
	String rsp = RestTemplate.getForObject(url, String.class, new String[]{});
	System.out.println(rsp);
	

}

@Test
public void testPost(){
	
	RestTemplate  RestTemplate = new RestTemplate();
	
	String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx0de57dcbf14bc60e&secret=cec0293d5f620807bae15a08399455b3";
	
	
	String rsp = RestTemplate.getForObject(url, String.class, new String[]{});
	JSONObject  json =   JSONObject.fromObject(rsp);
	String  acc =  json.getString("access_token");
	
	
	System.out.println(rsp);
	System.out.println(acc);
	
  String menuc="https://api.weixin.qq.com/cgi-bin/menu/create";
  
  String urlll=menuc+"?access_token="+acc;
  
  System.out.println("url is ..............."+urlll);
  
  //
  
  String json1="{\"button\":[{\"name\":\"JUNZER1\",\"sub_button\":[{\"name\":\"BBB\",\"type\":\"view\",\"url\":\"http://baidu.com\"}]},{\"name\":\"JUNZER2\",\"sub_button\":[{\"key\":\"222\",\"name\":\"AAA\",\"type\":\"click\"}]}]}";

  
  String  back= RestTemplate.postForObject(urlll, json1, String.class, new String[]{});
  System.out.println("back......................."+back);
	
	
	

}
@Test
public void getJson(){
	
	MenuService menuService = new MenuService();
	
	JSONObject json = menuService.weixinMenuJson();
	
	System.out.println(json);
}


}
