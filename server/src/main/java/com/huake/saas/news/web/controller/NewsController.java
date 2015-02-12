package com.huake.saas.news.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.auth.entity.NewsAuthCfg;
import com.huake.saas.auth.exception.AuthValidationException;
import com.huake.saas.auth.service.ValidateService;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Message;
import com.huake.saas.base.service.ImageUploadService;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.files.FilesUploadService;
import com.huake.saas.images.Photo;
import com.huake.saas.news.entity.News;
import com.huake.saas.news.entity.NewsCategory;
import com.huake.saas.news.entity.NewsContent;
import com.huake.saas.news.service.NewsCategoryService;
import com.huake.saas.news.service.NewsService;
import com.huake.saas.news.service.NewsStaticMessageProducer;
import com.huake.saas.redis.repository.AuthCfgRepository;
import com.huake.saas.redis.repository.NewsRepository;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;

@Controller
@RequestMapping(value = "/mgr/news")
public class NewsController extends BaseController {
	@Autowired
	private NewsService newsService;

	@Autowired
	private NewsCategoryService newsCategoryService;

	@Autowired
	private ImageUploadService imageUploadService;

	@Autowired
	private FilesUploadService filesUploadService;

	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private TenancyService tenancyService;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private NewsStaticMessageProducer nsProducer;
	
	@Autowired
	private AuthCfgRepository<NewsAuthCfg> authCfgRepository;
	
	@Autowired
	private NewsRepository newsRepository;
	/*@Autowired
	private FreeMarkertUtil freeMarkertUtil;*/

/*	@Value("#{envProps.htmlpath}")
	private String outPath;
*/
	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put(BaseEntity.PAGE_CRTDATE_DESC, "默认(创建时间降序)");
		sortTypes.put(BaseEntity.PAGE_CRTDATE_ASC, "创建时间升序");
	}

	/**
	 * 主界面
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Message.PAGE_SIZE) int pageSize,
			@RequestParam(value = Message.SOFT_TYPE, defaultValue = BaseEntity.PAGE_CRTDATE_DESC) String sortType,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, Message.SEARCH_CONDITIONS);
		Page<News> newss = newsService.getUserNews(getCurrentUID(), searchParams,
				pageNumber, pageSize, sortType);
		/*User user = accountService.getUser(userId);
		List<NewsCategory> newsCategories = null;
		if (user.getRoles().equals(User.USER_ROLE_SYS_ADMIN)) {
			newsCategories = newsCategoryService
					.getAllNewsCategorys(BaseEntity.STATUS_VALIDE);
		} else {
			long uid = user.getUid();
			newsCategories = newsCategoryService.getAllNewsCategory(
					BaseEntity.STATUS_VALIDE, uid);
		}
		if (!User.USER_ROLE_ADMIN.equals(user.getRoles())) {
			model.addAttribute("uid", user.getUid());
		}*/
		List<NewsCategory> newsCategories = newsCategoryService.getAllNewsCategory(BaseEntity.STATUS_VALIDE, getCurrentUID());
		model.addAttribute("newsCategories", newsCategories);
		model.addAttribute(News.PARMS_NEWSS, newss);
		model.addAttribute(Message.SOFT_TYPE, sortType);
		model.addAttribute(Message.SOFT_TYPES, sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute(Message.SEARCH_PARAMS, Servlets
				.encodeParameterStringWithPrefix(searchParams,
						Message.SEARCH_CONDITIONS));
		return "news/index";
	}

	/**
	 * 跳入add页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model,RedirectAttributes redirectAttributes) {
		User user = accountService.getUser(getCurrentUserId());
		
		if(!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())){
			/**
			 * 进行业务模块 授权细则校验
			 */
			String[] rulesName = {"newsDaylimit"};//要校验的细则 这里是每天添加新闻资讯的限制
			try{
				/**
				 * 进行权限校验  这里只是进入添加新闻资讯页面 
				 * 并没有实际添加新闻资讯操作 
				 * 所以 并不更新新闻资讯配置的使用情况到redis中
				 */
				validateService.check(getCurrentUID(),rulesName, null,authCfgRepository.getAuthCfg(getCurrentUID().toString()));
			} catch (AuthValidationException e) { //有异常则校验不通过 
				redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,e.getMessage());
				return "redirect:/mgr/news";
			}
		}
		
		List<NewsCategory> newsCategories = newsCategoryService.getAllNewsCategory(BaseEntity.STATUS_VALIDE, getCurrentUID());
		model.addAttribute("newsCategorys", newsCategories);

		model.addAttribute(News.PARMS_NEWS, new News());

		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_CREATE_PAGE);
		return "news/add";
	}

	/**
	 * 保存新闻
	 * 
	 * @param news
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveNews(
			@ModelAttribute("news") News news,
			@RequestParam("fileInput_thum") List<CommonsMultipartFile> fileInputxs,
			@RequestParam("photo") List<CommonsMultipartFile> fileInputs,
			HttpServletRequest request, Model model,RedirectAttributes redirectAttributes)
			throws IOException {
		User user = accountService.getUser(getCurrentUserId());
		/**
		 * 加载当前租户新闻配置使用情况
		 */
		NewsAuthCfg useCfg = authCfgRepository.getAuthCfg(getCurrentUID().toString());
		
		if(!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())){
			/**
			 * 进行业务模块 授权细则校验
			 */
			String[] rulesName = {"newsDaylimit","newsMonthVideo"};//要校验的细则
			try{
				/**
				 * 进行权限校验 这里执行了添加新闻资讯的实际操作
				 * 所以要更新   新闻资讯配置的使用情况到redis中
				 */
				validateService.check(getCurrentUID(),rulesName, news,useCfg);
				
			} catch (AuthValidationException e) { //有异常则校验不通过 
				
				List<NewsCategory> newsCategories = null;
				if (User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())) {
					newsCategories = newsCategoryService
							.getAllNewsCategorys(BaseEntity.STATUS_VALIDE);
				} else {
					long uid = user.getUid();
					newsCategories = newsCategoryService.getAllNewsCategory(
							BaseEntity.STATUS_VALIDE, uid);
				}
				if (!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())) {
					model.addAttribute("uid", user.getUid());
				}
				model.addAttribute("newsCategorys", newsCategories);
				model.addAttribute(News.PARMS_NEWS, news);
				model.addAttribute("content", news.getContents());
				model.addAttribute(Message.DATE_MUTUAL_MESSAGE,e.getMessage());
				return "news/add";
			}
		}
		
		news.setUid(String.valueOf(user.getUid()));
		//处理置顶
		if(news.getStick() != null){
			news.setStick(newsRepository.getNewsTop(news.getUid()));
			newsRepository.putNewsTop(news.getStick()+1, news.getUid());
		}
		//banner处理
		if (news.getBanner() != null) {
			news.setBanner(News.IS_BANNER);
		} else {
			news.setBanner(News.NOT_BANNER);
		}
		newsService.saveNews(news);
		
		// 默认生成的url
		news.setUrl(newsService.getNewsUrlByNewsId(news.getId()));

		// 上传普通图片
		List<NewsContent> contents = news.getContents();
		if (null != fileInputs) {
			for (int i = 0; i < fileInputs.size(); i++) {
				CommonsMultipartFile fileInput = fileInputs.get(i);
				if (fileInputs.get(i) != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
		
					List<Photo> photos = newsService.uploadNewsPic(fileInput,
							(MultipartHttpServletRequest) request);
					contents.get(i).setPhoto(imgPath+photos.get(1).getFilePath());// 默认保存200*200图片的url
				} 

			}
		}
		//处理视频存储路径
		for(int i=0;i<contents.size();i++){
			NewsContent content = contents.get(i);
			if(content.getVideo() != null && !content.getVideo().trim().equals("") && !content.getVideo().contains(imgPath) ){
				content.setVideo(imgPath+content.getVideo());
			}
		}
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
				
					List<Photo> photos = newsService.uploadThumb(fileInput,
							(MultipartHttpServletRequest) request);
					news.setPic(imgPath+photos.get(2).getFilePath());// 默认保存200*200图片的url
				}
			}
		}
		news = newsService.updateNewsByEntity(news);
		newsService.saveNewsContent(contents, news);
		
		/**
		 * 验证通过并且保存新闻到数据库中  更新新闻资讯配置的实际使用情况
		 */
		authCfgRepository.updateAuthCfg(useCfg);
		
		nsProducer.sendQueue(news.getId());//发送异步队列 处理新闻静态化
		
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.ADD_DATE_COMPLETE);
		return "redirect:/mgr/news";
	}

	/**
	 * 跳转到update界面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		News news = newsService.getNews(id);
		List<NewsContent> content = newsService.findNewsContentByNewsId(id);// 获取新闻内容
		model.addAttribute(News.PARMS_NEWS, news);
		List<NewsCategory> newsCategories = newsCategoryService.getAllNewsCategory(BaseEntity.STATUS_VALIDE, getCurrentUID());
		model.addAttribute("content",content.size()==0?null:content);
		model.addAttribute("newsCategorys", newsCategories);
		// List<NewsPictures> pictures = newsService.getPictures(id.intValue());
		// model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_UPDATE_PAGE);
		return "news/edit";
	}

	/**
	 * 新闻简介，详细图片上传
	 * 
	 * @param file
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestParam("upload") CommonsMultipartFile file,
			@RequestParam("CKEditorFuncNum") String callback,
			HttpServletResponse response) {
		String js;
		try {
			js = imageUploadService.upload(file, callback);
			PrintWriter pw = response.getWriter();
			pw.write(js);
			pw.close();
		} catch (Exception e) {
		}

	}

	/**
	 * 跳转到detail界面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "many/{id}/info", method = RequestMethod.GET)
	public String detial(@PathVariable("id") Long id, Model model) {
		News news = newsService.getNews(id);
		int newid = news.getId().intValue();
		news.setContents(newsService.findNewsContentByNewsId(newid));
		model.addAttribute(News.PARMS_NEWS, news);
		
		NewsCategory newsCategory = newsCategoryService
				.getNewsCategoryById(newsService.getNews(id).getCategoryId());
		
		//List<NewsPictures> pictures = newsService.getPictures(newid);
		//model.addAttribute(NewsPictures.PARMS_NEWS_PICTURES, pictures);
		model.addAttribute(NewsCategory.PARMS_NEWS_CATEGORY, newsCategory);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "news/detail";
	}

	/**
	 * 跳转到预览界面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}/preview", method = RequestMethod.GET)
	public String preview(@PathVariable("id") Long id, Model model) {
		News news = newsService.getNews(id);
		int newid = news.getId().intValue();
		news.setContents(newsService.findNewsContentByNewsId(newid));
		model.addAttribute(News.PARMS_NEWS, news);
		NewsCategory newsCategory = newsCategoryService
				.getNewsCategoryById(newsService.getNews(id).getCategoryId());
		//List<NewsPictures> pictures = newsService.getPictures(newid);
		String uid = newsService.getNews(id).getUid();
		Tenancy tenancy = tenancyService.getTenancy(Long.valueOf(uid));

		model.addAttribute(Tenancy.PARMS_TENANCY, tenancy);
		model.addAttribute(NewsCategory.PARMS_NEWS_CATEGORY, newsCategory);
		model.addAttribute(Message.SKIP_PARAM, Message.SKIP_DETAIL_PAGE);
		return "news/preview";
	}

	/**
	 * 更新新闻信息
	 * 
	 * @param news
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateNews(
			@ModelAttribute("news") News news,
			@RequestParam("fileInput_thum") List<CommonsMultipartFile> fileInputxs,
			@RequestParam("photo") List<CommonsMultipartFile> fileInputs,
			HttpServletRequest request,RedirectAttributes redirectAttributes)
			throws IOException {
		User user = accountService.getUser(getCurrentUserId());
		
		/**
		 * 加载当前租户新闻配置使用情况
		 */
		NewsAuthCfg useCfg = authCfgRepository.getAuthCfg(getCurrentUID().toString());
		
		if(!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())){
			/**
			 * 进行业务模块 授权细则校验
			 */
			String[] rulesName = {"newsMonthVideo"};//要校验的细则
			try{
				/**
				 * 进行权限校验 这里执行了添加新闻资讯的实际操作
				 * 所以要更新   新闻资讯配置的使用情况到redis中
				 */ 
				validateService.check(getCurrentUID(),rulesName, news,useCfg);
			} catch (AuthValidationException e) { //有异常则校验不通过 
				redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,e.getMessage());
				return "redirect:/mgr/news/update/"+news.getId();
			}
		}
		// 上传普通图片
		List<NewsContent> contents = news.getContents();
		if (null != fileInputs) {
			for (int i = 0; i < fileInputs.size(); i++) {
				CommonsMultipartFile fileInput = fileInputs.get(i);
				if (fileInputs.get(i) != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
					List<Photo> photos = newsService.uploadNewsPic(fileInput,
							(MultipartHttpServletRequest) request);
					contents.get(i).setPhoto(imgPath+photos.get(1).getFilePath());//
				}
			}
		}
		
		//处理视频存储路径
		for(int i=0;i<contents.size();i++){
			NewsContent content = news.getContents().get(i);
			if(content.getVideo() != null && !content.getVideo().trim().equals("") && !content.getVideo().contains(imgPath) ){
				content.setVideo(imgPath+content.getVideo());
			}
		}
		// 上传缩略图
		if (null != fileInputxs) {
			for (CommonsMultipartFile fileInput : fileInputxs) {
				if (fileInput != null
						&& fileInput.getOriginalFilename() != null
						&& !fileInput.getOriginalFilename().equals("")) {
				
					List<Photo> photos = newsService.uploadThumb(fileInput,
							(MultipartHttpServletRequest) request);
					news.setPic(imgPath+photos.get(2).getFilePath());
					
				}
			}
		}
		//置顶处理
		if(news.getStick() != null){
			news.setStick(newsRepository.getNewsTop(news.getUid()));
			newsRepository.putNewsTop(news.getStick()+1, news.getUid());
		}
		//banner处理
		if(news.getBanner() != null){
			news.setBanner(News.IS_BANNER);
		}else{
			news.setBanner(News.NOT_BANNER);
		}
		news = newsService.updateNews(news);
		newsService.saveNewsContent(contents, news);
		
		/**
		 * 验证通过并且保存新闻到数据库中  更新新闻资讯配置的实际使用情况
		 */
		authCfgRepository.updateAuthCfg(useCfg);
		
		nsProducer.sendQueue(news.getId());//发送异步队列 处理新闻静态化
		
		redirectAttributes.addFlashAttribute(Message.DATE_MUTUAL_MESSAGE,
				Message.UPDATE_DATE_COMPLETE);
		return "redirect:/mgr/news";
	}

	/**
	 * ajax删除（非真正意义上的删除）
	 * 
	 * @param id
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public News deleteNews(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		return newsService.deleteNews(id);
	}

	/**
	 * ajax删除（非真正意义上的删除）
	 * 
	 * @param id
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "{id}/delpic", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePic(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes) {
		newsService.deletePicture(id);
	}

	
	/**
	 * 修改名字
	 * 
	 * @param name
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changeName", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeName(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "id", required = false) Long id) {
		newsService.changeName(id, name);
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出News对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 *//*
	@ModelAttribute
	public void getNews(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute(News.PARMS_NEWS, newsService.getNews(id));
		}
	}*/

	/**
	 * uplodify上传文件方法
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadify", method = RequestMethod.POST)
	@ResponseBody
	public String uploadify(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		return filesUploadService.saveFile(fileMap,
				NewsService.IMAGES_NEWS_PIC_DIR);
	}
	
	/**
	 * ajax删除
	 * 
	 * @param id
	 * @return
	 * @throws AppBizException
	 */
	@RequestMapping(value = "delcon/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteContent(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes){
		newsService.deleteContent(id);
	}
	
	/**
	 * ajax 取消置顶
	 * @param id
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "cancelTop/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelTop(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes){
		News news = newsService.getNews(id);
		news.setStick(null);
		newsService.updateNewsByEntity(news);
	}
	
	/**
	 * ajax 置顶
	 * @param id
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "setTop/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void setTop(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes){
		News news = newsService.getNews(id);
		news.setStick(newsRepository.getNewsTop(news.getUid()));
		newsRepository.putNewsTop(news.getStick()+1, news.getUid());
		newsService.updateNewsByEntity(news);
	}
	
	/**
	 * ajax 取消banner
	 * @param id
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "cancelBanner/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelBanner(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes){
		News news = newsService.getNews(id);
		news.setBanner(News.NOT_BANNER);
		newsService.updateNewsByEntity(news);
	}
	
	/**
	 * ajax 设为banner
	 * @param id
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "setBanner/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void setBanner(@PathVariable(value = "id") Long id,
			RedirectAttributes redirectAttributes){
		News news = newsService.getNews(id);
		news.setBanner(News.IS_BANNER);
		newsService.updateNewsByEntity(news);
	}
	
	@RequestMapping(value = "showInfo")
	public String showInfo() {
		return "news/info";
	}
}
