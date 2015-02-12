package com.huake.saas.weixin.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletRequest;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.huake.saas.account.service.ShiroDbRealm.ShiroUser;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.mobileResource.entity.MobileResource;
import com.huake.saas.mobileResource.service.MobileResourceService;
import com.huake.saas.weixin.model.BackCodeModel;
import com.huake.saas.weixin.model.MenuTime;
import com.huake.saas.weixin.model.SubMenu;
import com.huake.saas.weixin.model.WeixinKeywordRule;
import com.huake.saas.weixin.model.WeixinMenuword;
import com.huake.saas.weixin.model.WeixinTreeNode;
import com.huake.saas.weixin.repository.MenuTimeDao;
import com.huake.saas.weixin.repository.WeixinKeywordRuleDao;
import com.huake.saas.weixin.repository.WeixinMenuwordDao;
import com.huake.saas.weixin.service.MenuService;
import com.huake.saas.weixin.service.SubMenuService;
import com.huake.saas.weixin.service.WeixinService;
import com.huake.saas.weixin.service.WeixinTreeNodeService;
import com.huake.saas.weixinmenu.model.HttpRequest;

/**
 * @author wujiajun
 * 
 *         微信接入管理
 */
@RestController("weixinMenuController")
@RequestMapping(value = "/mgr/weixin/**")
public class WeixinMenuController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinMenuController.class);

	private static final String URL_CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create";

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private SubMenuService SubMenuService;
	@Autowired
	private  MenuTimeDao MenuTimeDao;

	@Autowired
	private WeixinTreeNodeService WeixinTreeNodeService;

	@Autowired
	private WeixinKeywordRuleDao weixinKeywordRuleDao;

	@Autowired
	private WeixinMenuwordDao weixinMenuwordDao;
	@Autowired
	private MenuService menuservice;
	
	@Autowired
	private  WeixinTreeNodeService nodeService; 
	
	

	/**
	 * 首页,帮助
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	
	
	
	
	@RequestMapping("noderename")
	public ModelAndView nodeRename(String nodename ,long  nodeid){
		//修改页面 tree的名字！   根据id 获取到对象，set name 即可
		logger.debug(nodename+"::::::::::::::::更改名字！");
		logger.debug(nodeid+"::::::::::::::::更改id！");
		WeixinTreeNode node = WeixinTreeNodeService.findByTreeId(nodeid);
		node.setName(nodename);
		WeixinTreeNodeService.addTreeNode(node);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("");
		return mav;
	}
	
	
	
	@RequestMapping("createmenuroot")
	public ModelAndView createMenuRoot() {
		//创建一条 初始的记录 uid  =this   name=微信主菜单入口  treeid=1    pid=0//TODO
		
		WeixinTreeNode node = new WeixinTreeNode();
		node.setTreeid(new Random().nextLong());
		node.setUid(getCurrentUId());
		node.setpId(0L);
		node.setName("微信主菜单入口");
		
		
		WeixinTreeNodeService.addTreeNode(node);
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:mgr/weixin/menuIndex");
		
		
		return mav;
	}
	
	
	

	@ResponseBody
	@RequestMapping(value = "getAccessToken", method = { RequestMethod.GET })
	public String getAccessToken() {
		logger.debug("异步获取AccessToken");
		
		
		String accessToken = menuservice.getAccessToken();
		

		return accessToken;
	}
	
	
	

    @Autowired
    private MobileResourceService mobileService;
    @Value("#{envProps.mobileUrl}")
	private String mobileUrl;
	@RequestMapping(value = "menuIndex", method = { RequestMethod.GET })
	public ModelAndView menuIndex(ServletRequest request) {
		logger.debug("菜单主页");

		// 把所有规则显示
		List<WeixinKeywordRule> rules = (List<WeixinKeywordRule>) weixinKeywordRuleDao
				.findByUid(getCurrentUId());
		//把时间也拿过去
		MenuTime time = MenuTimeDao.findByUid(getCurrentUID());
		
		//把url 拿过去
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
		mav.addObject("rules", rules);
		mav.addObject("res", res);
		mav.addObject("time", time);
		mav.setViewName("weixin/weixinmenuIndex");
		return mav;
	}

	@RequestMapping(value = "menuadd", method = { RequestMethod.GET })
	public ModelAndView menuadd(ServletRequest request) {
		logger.debug("菜单增加");
		// 把所有菜单 显示在ztree
		List<SubMenu> submenulist = SubMenuService.getAllSubmenu();
		// 把所有规则显示
		List<WeixinKeywordRule> rules = (List<WeixinKeywordRule>) weixinKeywordRuleDao
				.findAll();

		ModelAndView mav = new ModelAndView();

		mav.addObject("submenulist", submenulist);
		mav.addObject("rules", rules);

		mav.setViewName("weixin/weixinmenuadd");
		return mav;
	}

	@RequestMapping(value = "submenu", method = { RequestMethod.GET })
	public ModelAndView submenu(String submenuname) {
		logger.debug("增加一级菜单");
		logger.debug(submenuname);
		// 将一级菜单保存

		SubMenu submenu = new SubMenu();
		submenu.setName(submenuname);

		// 需要做限制 不能超过3条
		long count = SubMenuService.count();
		if (count >= 3) {
			// 后续 到页面提示
			logger.debug("数量超过了");
			throw new RuntimeException();
		} else {
			SubMenuService.save(submenu);
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/mgr/weixin/menuadd");
		return mav;
	}

	/**
	 * 增加clcik菜单或者view菜单
	 */
	@RequestMapping("clickorview")
	public void clickorview(String name) {

	}

	/**
	 * 开始 菜单的处理 TODO OK
	 * 
	 * @return
	 */
	// 点击父级标签的时候发送异步请求到后台
	@ResponseBody
	@RequestMapping(value = "getTreeNode", method = RequestMethod.POST)
	public List<WeixinTreeNode> getTreeNode() {

		System.out.println("进来了初始化nodes");
		
		//要按照顺序把 node 更具seq 查询出来，然后依次set进去
		
		

		List<WeixinTreeNode> nodes = new ArrayList<WeixinTreeNode>();

		// 把表中的数据拿出来 然后显示到页面（初始化的一条是---【微信主菜单入口】） 获取当前uid的
		List<WeixinTreeNode> datebasenodes = WeixinTreeNodeService
				.findTreeNodeByUid(getCurrentUId());
		// 把数据 插入到 nodes
		for (WeixinTreeNode weixinTreeNode : datebasenodes)
			nodes.add(weixinTreeNode);

		{

		}

		return nodes;
	}
	
	/**
	 * 增加节点，并且加入顺序
	 */
	@RequestMapping(value = "addTreeNode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Long addTreeNode(@RequestBody WeixinTreeNode node) {

		System.out.println(node.getpId());
		System.out.println(node.getName());
		System.out.println(node.getMenutype());
		System.out.println(node.getMenukey());

		System.out.println("增加    节点！！！");

		// 持久化到数据库中
		//顺序： seq 属性，根据PID查询到父级对象，查看 是否存在子集，有的话 或取到seq+1 ，没有的话就从1开始
		
		int seq=1;
		
		long pid= node.getpId();
		//获取到 父级的id  然后 根据 父级id 查询 到  当前父级id 下的 node ，如果有 取seq 最大的，如果没有 1
		
		List<WeixinTreeNode>    kids=  WeixinTreeNodeService.findAllNodeByPid(pid, getCurrentUId());
		
		if(kids.size()==0){
			//从1 开始
			seq=1;
		}else {
			//获取到最大的 seq
			List<Integer> number = new ArrayList<Integer>();
			
			for (WeixinTreeNode weixinTreeNode : kids) {
				//取到最大的seq
				number.add(weixinTreeNode.getSeq());
				
			}
			
			int  maxseq= Collections.max(number);
			
			System.out.println(maxseq+"当前  菜单下 最大的 是：：：");
			
			seq=maxseq+1;
		}
		
		
		
		
		
		
		node.setUid(getCurrentUId());
		node.setSeq(seq);
		logger.debug("Seq 是：：：："+seq);
		WeixinTreeNodeService.addTreeNode(node);

		return node.getId();
	}

	@RequestMapping(value = "deleteTreeNode", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String deleteTreeNode(@RequestParam("treeid") long id) {

		System.out.println("删除节点");

		// 用treeid 找到对应的 数据库数据 ，删除

		WeixinTreeNodeService.deleteTreeNodeById(id);

		return "成功";
	}

	@RequestMapping(value = "modifyTreeNode", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public WeixinTreeNode modifyTreeNode(@RequestBody WeixinTreeNode node) {

		System.out.println("修改      节点");

		System.out.println(node.getId());
		System.out.println(node.getName());
		System.out.println(node.getMenutype());
		System.out.println(node.getMenukey());

		// 修改

		WeixinTreeNode datanode = WeixinTreeNodeService.findByTreeId(node
				.getId());

		if ("1".equals(node.getMenutype())) {
			// 1.click TYPE1， 的话去数据库查询到 rule 然后把title取出来 存到menu表，并且关联rule

			String ruleid = node.getMenukey();

			WeixinKeywordRule rule = weixinKeywordRuleDao.findOne(Long
					.valueOf(ruleid));

			

			// 维护menu（新增一条记录，key =title）表和rule的关系 TODO
			WeixinMenuword menuword = new WeixinMenuword();
			menuword.setWord(ruleid);
			menuword.setRule(rule);
			menuword.setUid(getCurrentUId());

			weixinMenuwordDao.save(menuword);

			// 改treenode表

			datanode.setMenutype(node.getMenutype());
			datanode.setMenukey(ruleid);

			WeixinTreeNodeService.addTreeNode(datanode);

			logger.debug("type 是 111111111111111111111");

		} else if ("2".equals(node.getMenutype())) {
			// 2.view TYPE2 ，直接set进去即可


			datanode.setMenutype(node.getMenutype());
			datanode.setMenukey(node.getMenukey());

			WeixinTreeNodeService.addTreeNode(datanode);
			logger.debug("type 是 22222222222222222");

		}

		return datanode;
	}
	
	
	@RequestMapping(value = "drop", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Map<String,String> dropTreeNode(@RequestParam(value = "nodeId",required =true)long nodeId , @RequestParam(value = "targetId",required =true)long targetId,
			@RequestParam(value = "position",required =true)String position) {
		
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");
		nodeService.drop(nodeId , targetId,position);
		return null;
	
	}
	
	
	
	

	/**
	 * 同步微信菜单到 微信服务器
	 */
	@Autowired
	private WeixinTreeNodeService wxtreeDao;

	@RequestMapping(value = "synchro", method = RequestMethod.GET)
	public ModelAndView synchro() {
		System.out.println("同步进来了！！！！！！！！");
		ModelAndView mav = new ModelAndView();
		JSONObject json = menuservice.weixinMenuJson();
		
		
		String accessToken=null;
		
		try {
			
			accessToken = menuservice.getAccessToken();
			
			
		} catch (Exception e) {
			
			
			//错误返回 ，吧错误信息显示给页面
			logger.debug("获取ACCtoken 失败了：：：：");
			
			mav.addObject("msg", "连接微信服务器异常！！！");
			mav.setViewName("weixin/menuno");
			
			return mav;
			
		}

		
		
		
		
		String createmenuurl = URL_CREATE_MENU + "?access_token=" + accessToken;
		logger.debug("this is create menu  url......" + createmenuurl);
		logger.debug("send  menu json........" + json.toString());
		JSONObject back = HttpRequest.httpRequest(createmenuurl, json.toString());
		System.out.println("create  menu  OK ........................................"+back);
		//把获取到的返回码显示到页面提示
		
		BackCodeModel backcode= (BackCodeModel) JSONObject.toBean(back, BackCodeModel.class);
		

		
		
		logger.debug(backcode.getErrcode()+"返回代码是：：：：：：");
		if("0".equals(backcode.getErrcode())){
			//成功返回
			logger.debug("成功返回：：：：");
			
			mav.setViewName("weixin/menuok");
			
			
			//同步成功，记录同步的时间！
			
		
			 MenuTime time=  MenuTimeDao.findByUid(getCurrentUID());
			if(time==null){
				time=new MenuTime();
			}
			time.setUid(getCurrentUID());
			time.setMenucreatetime(new Date());
			MenuTimeDao.save(time) ;
			
		}else {
			//错误返回 ，吧错误信息显示给页面
			logger.debug("失败返回：：：：：");
			logger.debug("错误信息::::"+backcode.getErrmsg());
			
			mav.addObject("msg", backcode.getErrmsg());
			mav.setViewName("weixin/menuno");
			
			
			
		}
		
		

	
		return mav;
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public Long getCurrentUId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.uid;
	}

}
