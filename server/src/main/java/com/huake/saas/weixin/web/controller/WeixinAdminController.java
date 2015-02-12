package com.huake.saas.weixin.web.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.huake.dict.entity.Dictionary;
import com.huake.dict.service.DictViewService;
import com.huake.saas.auth.service.AuthService;
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.BizMappingCfg;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.service.BizMappingCfgService;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.mobileResource.entity.MobileResource;
import com.huake.saas.mobileResource.service.MobileResourceService;
import com.huake.saas.util.SpringUtils;
import com.huake.saas.weixin.annonation.WeixinTargetName;
import com.huake.saas.weixin.annonation.WeixinTargetParameter;
import com.huake.saas.weixin.model.InterfaceParam;
import com.huake.saas.weixin.model.WeixinCfg;
import com.huake.saas.weixin.model.WeixinKeyword;
import com.huake.saas.weixin.model.WeixinKeywordRule;
import com.huake.saas.weixin.service.WeixinService;

/**
 * @author wujiajun
 * 
 * 微信接入管理
 */
@RestController("weixinAdminController")
@RequestMapping(value = "/mgr/weixin/**")
public class WeixinAdminController extends BaseController {
	
	
	
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();

	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("ruleCreate", "时间");
	}
	
	
	private static Map<String, String> sortTypeskeyword = Maps.newLinkedHashMap();

	static {
		sortTypeskeyword.put("auto", "自动");
		sortTypeskeyword.put("keywordCreate", "时间");
	}
	
	
	

	private static final Logger logger = LoggerFactory.getLogger(WeixinAdminController.class);

	@Autowired
	private WeixinService weixinService;


	@Autowired
	private DictViewService dictViewService;

	@Autowired
	private BizMappingCfgService bizMappingCfgService;
	
	@Autowired
	private AuthService authService;
	
	
	
	/**
	 * 关键字部分，可查询
	 */
	@RequestMapping(value = "keywordlist", method = { RequestMethod.GET })
	public ModelAndView keyWordList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto")String sortType,
			ServletRequest request) {
		//TODO
		//TODO
		//TODO
		//TODO
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<WeixinKeyword>   keywords = weixinService.getKeywordBySpcPageble(getCurrentUserId(), searchParams, pageNumber, pageSize, sortType);	
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("keywords", keywords);
		mav.addObject("sortTypes", sortTypeskeyword);//自动查找和，时间查找
		mav.setViewName("weixin/keywordlist");
		System.out.println("去页面！！！！！！！！"+keywords);
		return mav;
	}
	
	
	
	/**
	 * 修改关键词
	 */
	@RequestMapping(value="wordModify",method=RequestMethod.POST)
	public ModelAndView wordModify(@RequestParam("id") Long id,
      @RequestParam("keywordruleid") Long tilteId){
		WeixinKeywordRule keywordRule=weixinService.getkeyRuleById(id);
	   WeixinKeywordRule keywordRule2=weixinService.getkeyRuleById(tilteId);
	  keywordRule.setTitle(keywordRule2.getTitle());
		weixinService.saverule(keywordRule);
		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:mgr/weixin/keywordlist");
		return mav;
	}
	
	/**
	 *    跳转到  关键字 增加 页面，用于添加关键字 和rule 的关系 ！ 
	 */
	@RequestMapping(value = "keywordadd", method = { RequestMethod.GET })
	public ModelAndView keyWordAdd( ) {
		//遍历 rule
		
		List<WeixinKeywordRule>  rules=  weixinService.findByUid(getCurrentUID());

		ModelAndView mav = new ModelAndView();
		mav.addObject("rules", rules);
		mav.setViewName("weixin/keywordadd");
		return mav;
	}
	
	

	/**
	 * 跳转至关键字修改页面
	 */
	@RequestMapping(value="keywords/edit",method=RequestMethod.GET)
	public ModelAndView wordsEdit(@RequestParam("id") Long id){
		System.out.println("Id:::::::"+id);
		//1.  返回  id  ==页面id     keyword 对象
		//2.所有 的 rule
		
		WeixinKeyword keyword = weixinService.getWeixinkeywordById(id);
		List<WeixinKeywordRule>   rules = weixinService.findAllRule();
		ModelAndView mav=new ModelAndView();
		mav.addObject("keyword", keyword);
		mav.addObject("rules", rules);
		mav.setViewName("weixin/wordEdit");
		return mav;
	}
	
	
	
	
	@RequestMapping(value = "wordadd", method = { RequestMethod.POST })
	public ModelAndView wordAdd(@RequestParam("keywordname")  String keywordname 
			,@RequestParam("keywordruleid") Long keywordruleid ) {

		//或取到  rule 对象
		WeixinKeywordRule rule =weixinService.getkeyRuleById(keywordruleid);
		//增加 关键字  关键字的表  	
		String[] words= keywordname.split(",");
		for (String word : words) {
			WeixinKeyword key = new WeixinKeyword();
			key.setWord(word);
			key.setRule(rule);
			key.setKeywordCreate(new Date());
			key.setUid(getCurrentUID());	
			weixinService.savekeyword(key);
		}
		System.out.println("处理完成   keyword  modify");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:mgr/weixin/keywordlist");
		return mav;
	}
	
	
	
	@RequestMapping(value = "wordEditSave", method = { RequestMethod.POST })
	public ModelAndView wordEditSave(@RequestParam("keywordid")  long keywordid 
			,@RequestParam("keywordruleid") Long keywordruleid ) {
		//keywordid找到对象   set rule
		WeixinKeyword keyword=  weixinService.getWeixinkeywordById(keywordid);
		
		WeixinKeywordRule rule = weixinService.getkeyRuleById(keywordruleid);
		keyword.setRule(rule);
		weixinService.savekeyword(keyword);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:mgr/weixin/keywordlist");
		return mav;
	}
	
	
	


	/**
	 * 首页,帮助
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "index", method = { RequestMethod.GET })
	public ModelAndView index(ServletRequest request) {

		ModelAndView mav = new ModelAndView();

		mav.setViewName("weixin/index");
		return mav;
	}

	/**
	 * 接入配置表单，配置微信appid、secure、accesstoke等
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "access", method = { RequestMethod.GET })
	public ModelAndView access(ServletRequest request ,String msg) {
		logger.debug("weixin admin index page is loaded...");
		logger.debug(msg);
		WeixinCfg cfg = weixinService.getWeixinCfg(getCurrentUID());
		if (null == cfg) {
			cfg = new WeixinCfg();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("weixinCfg", cfg);
		mav.addObject("msg", msg);
		//
		mav.addObject("cfg", cfg);
		
		mav.setViewName("weixin/access");
		return mav;
	}

	/**
	 * 保存微信接入配置
	 * 
	 * @return
	 */
	@RequestMapping(value = "saveCfg", method = { RequestMethod.POST })
	public ModelAndView saveCfg(ServletRequest request, WeixinCfg weixinCfg,
			RedirectAttributes redirectAttributes) {
		weixinService.saveWeixinCfg(weixinCfg, getCurrentUID());
		redirectAttributes.addAttribute("msg",  "已保存微信接入设置");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/mgr/weixin/access");
		return mav;
	}

	/**
	 * 规则 的 list 部分，查询
	 *
	 * @return
	 */
	@RequestMapping(value = "keywords", method = { RequestMethod.GET })
	public ModelAndView keyword(
			WeixinKeywordRule keyword, 
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto")String sortType,
			ServletRequest request) {
		//TODO
		//TODO
		//TODO
		System.out.println("进来了哦！！！！！！！！");
		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<WeixinKeywordRule>   rules = weixinService.getRuleBySpcPageble(getCurrentUserId(), searchParams, pageNumber, pageSize, sortType);	
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("rules", rules);
		mav.addObject("sortTypes", sortTypes);//自动查找和，时间查找
		mav.setViewName("weixin/keywordsIndex");
		System.out.println("去页面！！！！！！！！"+rules);
		return mav;
	}
/**
 * 删除关键字，重定向
 * 
 */
@RequestMapping(value="keywords/delete",method={RequestMethod.GET})	
public ModelAndView deleteKeyword(@RequestParam(value = "id")Long id, Model model){
	System.out.println("id是："+id);
	weixinService.deleteWeixinKeyWord(id);
	
	ModelAndView mav = new ModelAndView();
	mav.setViewName("redirect:/mgr/weixin/keywordlist");
	return mav;
	
}


/**
 * 删除规则
 * */
@RequestMapping(value="rules/delete",method={RequestMethod.GET})	
public ModelAndView deleterules(@RequestParam(value = "id")Long id, Model model){
	System.out.println("id是："+id);
	weixinService.deleteWeixinRule(id);
	
	ModelAndView mav = new ModelAndView();
	mav.setViewName("redirect:/mgr/weixin/keywords");
	return mav;
	
}



/**
 * 跳转  至 修改规则
 * */
@RequestMapping(value="rules/modify",method={RequestMethod.GET})	
public ModelAndView modifyrules(@RequestParam(value = "id")Long id, Model model){
	ModelAndView mav = new ModelAndView();
	//TODO
	logger.debug("修改rule：：：：：：：");
	System.out.println("id是："+id);
	WeixinKeywordRule rule= weixinService.findWeixinRule(id);
	
	//根据bizcode 获取  beanname
	String bizcode = rule.getBizCode();
	BizMappingCfg cfg= bizMappingCfgService.findByBizCode(bizcode);
	//只有是biz的 才需要获取到beanname
	if(cfg!=null){
		String beanName= cfg.getBeanName();
		//实体名字传过去
		mav.addObject("beanName", beanName);
		
	}
	
	//把链接的选择 绑定过去
	//获取到app_mobile_resource 对象，拼 url   返回给页面
	
	 List<MobileResource>   datares = mobileService.findAll();
	 
	 List<MobileResource>  res = new ArrayList<MobileResource>();
	 
	
	 for (MobileResource mobileResource : datares) {
		 MobileResource  mres = new MobileResource();
		 
		 mres.setBizCode(mobileResource.getBizCode());
		 mres.setTitle(mobileResource.getTitle());
		 
		 String tager = mobileResource.getTarget();
		 String  relurl =  mobileUrl+tager+"?uid="+getCurrentUID();
		 
		 mres.setTarget(relurl);
		 
		 
		 res.add(mres);		
	}
	
	
	mav.addObject("rule", rule);
	mav.addObject("res", res);
	mav.setViewName("weixin/rulemodify");
	return mav;
	
}


/**
 * 获取当前 rule的，所有的参数 对象
 */
@ResponseBody
@RequestMapping(value = "rules/modify/getallpara", method = { RequestMethod.GET })
public List<InterfaceParam> getAllPara(ServletRequest request,long id) {
	logger.debug("修改规则，获取到所有的参数对象");
	logger.debug("rule  的  id  ：：：：："+id);
	
     WeixinKeywordRule rule= weixinService.findWeixinRule(id);
	
	//根据bizcode 获取  beanname
	String bizcode = rule.getBizCode();
	BizMappingCfg cfg= bizMappingCfgService.findByBizCode(bizcode);
	
	
	List<InterfaceParam> paramsAnnolist= new ArrayList<InterfaceParam>();
	Class<?>[]    bclass=   SpringUtils.getBean(cfg.getBeanName()).getClass().getInterfaces();
	for (Class<?> oneclass : bclass) {
		Method []  methods = oneclass.getMethods();
		for (Method method : methods) {
			if(rule.getProxyInterface().equals(method.getName())){
				Annotation[][]	parasannots= method.getParameterAnnotations();
				 for (Annotation[] annots : parasannots) {
					 WeixinTargetParameter paratag= (WeixinTargetParameter) annots[0];
					 InterfaceParam interfaceParam = new InterfaceParam();
					 interfaceParam.setEnName(paratag.enname());//中文
					 interfaceParam.setZhName(paratag.zhname());//英文
					 interfaceParam.setType(paratag.type());//类型
					 interfaceParam.setDescription(paratag.description());//描述
					 interfaceParam.setHandle(paratag.handle());//处理方式

					 paramsAnnolist.add(interfaceParam);
				 }
			}
		}
	}
	
	return paramsAnnolist;
}



/**
 * 规则修改，填写完毕后，，  保存
 */
@RequestMapping(value = "rules/modify/save", method = { RequestMethod.POST })
public ModelAndView modifySave(HttpServletRequest req) {
	ModelAndView mav = new ModelAndView();
	System.out.println("规则修改保存");
	
	
	//TODO现在页面的获取 ，保存
	System.out.println(req.getParameter("rulename")+":::::rulename");
	System.out.println(req.getParameter("proxyInterface")+":::::methodname");	
	System.out.println(req.getParameter("bizCode")+":::::bizcode");
			//动态的参数 ，在页面把他们的名字放到  input 的 value 中 去  ！！  然后后台取值
	System.out.println(req.getParameter("allpara")+":::::allpara");
	System.out.println(req.getParameter("wpCategory")+":::::wpCategory");
	System.out.println(req.getParameter("text")+":::::text");
	System.out.println(req.getParameter("url")+":::::url");	 
	
	System.out.println(req.getParameter("ruleid")+":::::ruleid");	 
	

	//获取固定的参数
			String keyword=req.getParameter("rulename");
			String wpcategory=req.getParameter("wpCategory");
			String methodname=req.getParameter("proxyInterface");
			String bizcode=req.getParameter("bizCode");
			String parasString=req.getParameter("allpara");
			
			String ruleid=req.getParameter("ruleid");
			
			// 参数为多个的时候要用，分割 存储多个规则
		     String []   keys = keyword.split(",");
		     System.out.println(keys.toString()+"::::::::::::::::::::::::::::::规则++++++++++++++++");
		     //uid
		 	long uid=getCurrentUID();
	

			//获取到页面点的动态参数-----就是-所有参数部分
	
			String[] paranames=parasString.split(",");
			logger.debug( String.valueOf(paranames.length)+"数组的长度");
			
			List<Object> paras = new ArrayList<Object>();
			//获取 所有参数
			for (int i = 0; i < paranames.length; i++) {
				String name= paranames[i];
				//if  else  如果是id的话   直接从上下文 去取，不需要输入 
				if("uid".equals(name)){
					String saveuid=String.valueOf(uid);
					paras.add(saveuid);		
				}else{
					Object value=req.getParameter(name);	
					paras.add(value);
				}
			}
			String param="";
			for (Object object : paras) {	
				param =param+(String)object+",";
			}
			//去除后一个，
			param= param.substring(0,param.length()-1);		
			logger.debug("存储到数据库的参数"+"::::::::"+param);
			
			//把页面的参数持久化到数据库
			
			
	
	//TEXT  URL BIZ
			if("biz".equals(wpcategory)){
				System.out.println("BIZ..........");
			     for (int i = 0; i < keys.length; i++) {
			    	 
			    		//获取到方法的中文名
			 		BizMappingCfg cfg= bizMappingCfgService.findByBizCode(bizcode);
			 		String beanName= cfg.getBeanName();
			 		
			 		Class<?>[]    bclass=   SpringUtils.getBean(beanName).getClass().getInterfaces();
			 		String chinesename ="";
			 		for (Class<?> oneclass : bclass) {
			 			Method []  methods = oneclass.getMethods();
			 			
			 			for (Method method : methods) {
			 				if(methodname.equals(method.getName())){
			 					
			 					WeixinTargetName tag= method.getAnnotation(WeixinTargetName.class);
			 					chinesename =tag.value();
			 				}
			 			}
			 		}
			    	 
			    	 
			 		//把多个规则名称 添加到规则表中
			    	 
			    	 
			    	 
			 		 WeixinKeywordRule rule = weixinService.findWeixinRule(Long.valueOf(ruleid));	
			 			rule.setBizCode(bizcode);
			 			rule.setCreatedAt(new Date());
			 			rule.setParams(param);
			 			rule.setTitle(keyword);
			 			rule.setProxyInterface(methodname);
			 			rule.setChineseProxyInterFace(chinesename);
			 			rule.setRuleCreate(new Date());
			 			
			 			//设置wpcate
			 			rule.setWpCategory("biz");
			 			//设置text null
			 			rule.setText(null);

			 			weixinService.saverule(rule);		
			 		}
				
			}else if("text".equals(wpcategory)){
				System.out.println("TEXT..........");
				 for (int i = 0; i < keys.length; i++) {
				 		//把多个规则名称 添加到规则表中
					 WeixinKeywordRule rule = weixinService.findWeixinRule(Long.valueOf(ruleid));	
						
						rule.setRuleCreate(new Date());
						rule.setWpCategory(wpcategory);
						rule.setText(req.getParameter("text"));
						rule.setTitle(keyword);
						rule.setTitle(keys[i]);
						rule.setRuleCreate(new Date());
						
			 			
			 			//设置wpcate
			 			rule.setWpCategory("text");
			 			//把字段清空
			 			rule.setBizCode(null);
			 			rule.setChineseProxyInterFace(null);
			 			rule.setProxyInterface(null);
			 			rule.setParams(null);
						
						weixinService.saverule(rule);		
				 		}
			}else if ("url".equals(wpcategory)){
				System.out.println("URL..........");
				 for (int i = 0; i < keys.length; i++) {
				 		//把多个规则名称 添加到规则表中
						WeixinKeywordRule rule = weixinService.findWeixinRule(Long.valueOf(ruleid));		
						
		
					
						rule.setText(req.getParameter("url"));
						rule.setTitle(keys[i]);
						rule.setTitle(keyword);
						rule.setRuleCreate(new Date());
						
			 			//设置wpcate
			 			rule.setWpCategory("url");
			 			//把字段清空
			 			rule.setBizCode(null);
			 			rule.setChineseProxyInterFace(null);
			 			rule.setProxyInterface(null);
			 			rule.setParams(null);
						
						weixinService.saverule(rule);	
						
				 		}
			}
	
    mav.setViewName("redirect:mgr/weixin/keywords");
	return mav;
	
}






     @Autowired
     private MobileResourceService mobileService;
     @Value("#{envProps.mobileUrl}")
 	private String mobileUrl;
	/**
	 * 新建规则 负责页面跳转 平且 把表中的模块数据显示出来
	 */
	@RequestMapping(value = "keywords/new", method = { RequestMethod.GET })
	public ModelAndView newKeywordRule(ServletRequest request, Model model) {
		//从数据字典里面拿数据  bizcode  和  描述     显示到页面
		//获取beanName  用于 后续para 的查找
		
		//获取到app_mobile_resource 对象，拼 url   返回给页面
		
		 List<MobileResource>   datares = mobileService.findAll();
		 
		 List<MobileResource>  res = new ArrayList<MobileResource>();
		 
		
		 for (MobileResource mobileResource : datares) {
			 MobileResource  mres = new MobileResource();
			 
			 mres.setBizCode(mobileResource.getBizCode());
			 mres.setTitle(mobileResource.getTitle());
			 
			 String tager = mobileResource.getTarget();
			 String  relurl =  mobileUrl+tager+"?uid="+getCurrentUID();
			 
			 mres.setTarget(relurl);
			 
			 
			 res.add(mres);
			 
			 
			 
			
		}
		
		
		
		 
		ModelAndView mav = new ModelAndView();
		model.addAttribute("res", res);
		model.addAttribute("rule", new WeixinKeywordRule());
		mav.setViewName("weixin/keywordsNew");
		return mav;
	}

	/**获取method
	 * @author wujiajun
	 * @param beanname
	 * @param bizcode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "keywords/beanname", method = RequestMethod.GET)
	public Map<String, String> getAllMethod(String bizcode) {
		BizMappingCfg cfg= bizMappingCfgService.findByBizCode(bizcode);
		String beanname= cfg.getBeanName();
		Map<String, String> methodMap = new HashMap<String, String>();
		Class<?>[] bclass = SpringUtils.getBean(beanname).getClass().getInterfaces();
		for (Class<?> class1 : bclass) {
			Method[] methods = class1.getMethods();
			for (Method method : methods) {
				String enMethodName = method.getName();
				WeixinTargetName methodannot = method.getAnnotation(WeixinTargetName.class);
				String zhMethodName = methodannot.value();
				methodMap.put(enMethodName, zhMethodName);
			}
		}
		
		return methodMap;
	}
	
	/**
	 * 获取paras
	 * @author wujiajun
	 * @param  beanname  methodname
	 */
	@RequestMapping(value="keywords/methodname",method=RequestMethod.GET)
	@ResponseBody
	public List<InterfaceParam> getAllParas(String bizcode,String methodname){
		BizMappingCfg cfg= bizMappingCfgService.findByBizCode(bizcode);
		String beanName= cfg.getBeanName();
		if( null != beanName){
			//TODO
		}
		List<InterfaceParam> paramsAnnolist= new ArrayList<InterfaceParam>();
		Class<?>[]    bclass=   SpringUtils.getBean(beanName).getClass().getInterfaces();
		for (Class<?> oneclass : bclass) {
			Method []  methods = oneclass.getMethods();
			for (Method method : methods) {
				if(methodname.equals(method.getName())){
					Annotation[][]	parasannots= method.getParameterAnnotations();
					 for (Annotation[] annots : parasannots) {
						 WeixinTargetParameter paratag= (WeixinTargetParameter) annots[0];
						 InterfaceParam interfaceParam = new InterfaceParam();
						 interfaceParam.setEnName(paratag.enname());//中文
						 interfaceParam.setZhName(paratag.zhname());//英文
						 interfaceParam.setType(paratag.type());//类型
						 interfaceParam.setDescription(paratag.description());//描述
						 interfaceParam.setHandle(paratag.handle());//处理方式

						 paramsAnnolist.add(interfaceParam);
					 }
				}
			}
		}
		return paramsAnnolist;
	}
	
	
	/**
	 * 新增提交   持久化到表中
	 * @return
	 */
	@RequestMapping(value="keywords/addkey")
	public ModelAndView addKeyword(HttpServletRequest req ,HttpServletResponse rsp){
		
		//TODO现在页面的获取 ，保存
		logger.debug(req.getParameter("rulename")+":::::rulename");
		logger.debug(req.getParameter("proxyInterface")+":::::methodname");	
		logger.debug(req.getParameter("bizCode")+":::::bizcode");
		//动态的参数 ，在页面把他们的名字放到  input 的 value 中 去  ！！  然后后台取值
		logger.debug(req.getParameter("allpara")+":::::allpara");
		logger.debug(req.getParameter("wpCategory")+":::::wpCategory");
		logger.debug(req.getParameter("text")+":::::text");
		logger.debug(req.getParameter("url")+":::::url");	 
		
		//获取固定的参数
		String keyword=req.getParameter("rulename");
		String wpcategory=req.getParameter("wpCategory");
		// 参数为多个的时候要用，分割 存储多个关键字
	     String []   keys = keyword.split(",");
	     System.out.println(keys.toString()+"::::::::::::::::::::::::::::::多个关键字++++++++++++++++");
	     //uid
	 	long uid=getCurrentUID();
		String methodname=req.getParameter("proxyInterface");
		String bizcode=req.getParameter("bizCode");
		//获取到页面点的动态参数-----就是-所有参数部分
		String parasString=req.getParameter("allpara");
		String[] paranames=parasString.split(",");
		logger.debug( String.valueOf(paranames.length)+"数组的长度");
		
		List<Object> paras = new ArrayList<Object>();
		//获取 所有参数
		for (int i = 0; i < paranames.length; i++) {
			String name= paranames[i];
			//if  else  如果是id的话   直接从上下文 去取，不需要输入 
			if("uid".equals(name)){
				String saveuid=String.valueOf(uid);
				paras.add(saveuid);		
			}else{
				Object value=req.getParameter(name);	
				paras.add(value);
			}
		}
		String param="";
		for (Object object : paras) {	
			param =param+(String)object+",";
		}
		//去除后一个，
		param= param.substring(0,param.length()-1);		
		logger.debug("存储到数据库的参数"+"::::::::"+param);
		
		//把页面的参数持久化到数据库
		
		
	
		
		
		
		//TEXT  URL BIZ
		if("biz".equals(wpcategory)){
			System.out.println("BIZ..........");
		     for (int i = 0; i < keys.length; i++) {
		    	 
		    		//获取到方法的中文名
		 		BizMappingCfg cfg= bizMappingCfgService.findByBizCode(bizcode);
		 		String beanName= cfg.getBeanName();
		 		
		 		Class<?>[]    bclass=   SpringUtils.getBean(beanName).getClass().getInterfaces();
		 		String chinesename ="";
		 		for (Class<?> oneclass : bclass) {
		 			Method []  methods = oneclass.getMethods();
		 			
		 			for (Method method : methods) {
		 				if(methodname.equals(method.getName())){
		 					
		 					WeixinTargetName tag= method.getAnnotation(WeixinTargetName.class);
		 					chinesename =tag.value();
		 				}
		 			}
		 		}
		    	 
		    	 
		 		//把多个规则名称 添加到规则表中
		    	 
		    	 
		    	 
		 	        WeixinKeywordRule rule= new WeixinKeywordRule();
		 			rule.setBizCode(bizcode);
		 			rule.setCreatedAt(new Date());
		 			rule.setParams(param);
		 			rule.setProxyInterface(methodname);
		 			rule.setChineseProxyInterFace(chinesename);
		 			rule.setUid(uid);	
		 			
		 			SimpleDateFormat format= new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		 			
		 			rule.setRuleCreate(new Date());
		 			rule.setWpCategory(wpcategory);
		 			rule.setTitle(keys[i]);
		 			weixinService.saverule(rule);		
		 		}
			
		}else if("text".equals(wpcategory)){
			System.out.println("TEXT..........");
			 for (int i = 0; i < keys.length; i++) {
			 		//把多个规则名称 添加到规则表中
				 WeixinKeywordRule rule= new WeixinKeywordRule();
					rule.setUid(uid);
					rule.setRuleCreate(new Date());
					rule.setWpCategory(wpcategory);
					rule.setText(req.getParameter("text"));
					rule.setTitle(keys[i]);
					rule.setRuleCreate(new Date());
					weixinService.saverule(rule);		
			 		}
		}else if ("url".equals(wpcategory)){
			System.out.println("URL..........");
			 for (int i = 0; i < keys.length; i++) {
			 		//把多个规则名称 添加到规则表中
				 WeixinKeywordRule rule= new WeixinKeywordRule();
					rule.setUid(uid);
					rule.setWpCategory(wpcategory);
					rule.setText(req.getParameter("url"));
					rule.setTitle(keys[i]);
					rule.setRuleCreate(new Date());
					weixinService.saverule(rule);		
			 		}
		}
		
		
	
		

	  ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:mgr/weixin/keywords");
		return mav;
	}
	
	/**
	 * 获取已授权过的业务代码字典列表
	 * @return
	 */
	@ModelAttribute("authedBizCodes")
	public Collection<Dictionary> authedBizCodes(){
		return authService.getAuthedBizCodes(this.getCurrentUID());
	}
	/**
	 * 获取微信回复分类类型
	 * @return
	 */
	@ModelAttribute("wpCategories")
	public Collection<Dictionary> wpCategories(){
		return dictViewService.getSelectModelCollection(Constants.WP_REPLY_CATE);
	}
	
}
