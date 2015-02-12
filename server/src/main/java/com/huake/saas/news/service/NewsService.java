package com.huake.saas.news.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.google.common.collect.Maps;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.activity.service.EventService;
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Compress;
import com.huake.saas.base.service.FileUploadService;
import com.huake.saas.base.service.ServiceException;
import com.huake.saas.images.ImagesUploadService;
import com.huake.saas.images.Photo;
import com.huake.saas.news.entity.News;
import com.huake.saas.news.entity.NewsCategory;
import com.huake.saas.news.entity.NewsContent;
import com.huake.saas.news.entity.NewsPictures;
import com.huake.saas.news.repository.NewsContentDao;
import com.huake.saas.news.repository.NewsDao;
import com.huake.saas.news.repository.NewsPicturesDao;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;
import com.huake.saas.util.FreeMarkertUtil;

//Spring Bean的标识.
@Component
//类中所有public函数都纳入事务管理的标识.
@Transactional
public class NewsService {
	public static final String IMAGES_NEWS_PIC_DIR = "news";
	
	private static Logger logger = LoggerFactory.getLogger(EventService.class);
	
	 /**
	  * 新闻静态文件的代理路径
	  */
	@Value("#{envProps.newsUrl}")
	private String proxyPath;
	/**
	 * html文件输出路径
	 */
	@Value("#{envProps.htmlpath}")
	private String outPath;
	/**
	 * 模板文件名称
	 */
	@Value("#{envProps.template_file}")
	private String templateFile;
	/**
	 * 文件访问路径
	 */
	@Value("#{envProps.app_files_url_prefix}")
	private String fileProxyPath;
	
	@Autowired
	private NewsDao newsDao;
	
	@Autowired
	private NewsContentDao newsContentsDao;
	
	@Autowired
	private ImagesUploadService imageUploadService;
	private NewsPicturesDao newsPicturesDao;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private FreeMarkertUtil freeMarkertUtil;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private TenancyService tenancyService;
	
	@Autowired
	private NewsCategoryService newsCategoryService;
	
	@Value("#{envProps.newsUrl}")
	private String url;
	
	public News getNews(Long id) {
		return newsDao.findOne(id);
	}
	
	public void updateNewsByUrl(News entity){
		newsDao.save(entity);
	}

	public void saveNews(News entity) {
		entity.setStatus(BaseEntity.STATUS_VALIDE);
		entity.setCrtDate(new Date());
		entity.setUpDate(new Date());
		entity.setUrl("");
		entity.setPublish("0");
		newsDao.save(entity);
	}
	
	public News updateNews(News entity) {
		long id = entity.getId();
		News oldNews = getNews(id);
		entity.setCrtDate(oldNews.getCrtDate());
		entity.setUpDate(new Date());
		entity.setStatus(oldNews.getStatus());
		//默认生成的url
		entity.setUrl(getNewsUrlByNewsId(id));
		entity.setPublish(News.NOT_PUBLILSH);
		return newsDao.save(entity);
	}
	
	public News updateNewsByEntity(News entity){
		entity.setUpDate(new Date());
		return newsDao.save(entity);
	}
	

	public News deleteNews(Long id) {
		News news = newsDao.findOne(id);
		news.setStatus(BaseEntity.STATUS_INVALIDE);
		newsDao.save(news);
		return news;
	}

	public List<News> getAllNews() {
		return (List<News>) newsDao.findAll();
	}

	// TODO userId是什么？
	public Page<News> getUserNews(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<News> spec = buildSpecification(userId, searchParams);
		return newsDao.findAll(spec, pageRequest);
	}

	/**
	 * API查询列表
	 * @param userId
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<News> getUserNewsApi(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<News> spec = buildSpecificationApi(userId, searchParams);
		return newsDao.findAll(spec, pageRequest);
	}
	
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if (BaseEntity.PAGE_CRTDATE_DESC.equals(sortType)) {
			sort = new Sort(Direction.DESC, new String[]{"banner","stick","crtDate"});
		} else if (BaseEntity.PAGE_CRTDATE_ASC.equals(sortType)) {
			sort = new Sort(Direction.ASC, BaseEntity.PAGE_CRTDATE_ASC);
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<News> buildSpecification(Long uid, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		/*if(userId!=null&&!"".equals(userId)){
			User user = accountService.getUser(userId);
			if(!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())){
				filters.put("uid", new SearchFilter("uid", Operator.EQ, user.getUid()));
			}
		}*/
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		filters.put("status", new SearchFilter("status", Operator.EQ, Constants.STATUS_VALID));
		return DynamicSpecifications.bySearchFilter(filters.values(), News.class);
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<News> buildSpecificationApi(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("uid", new SearchFilter("uid", Operator.EQ, userId));
		filters.put("status", new SearchFilter("status", Operator.EQ, Constants.STATUS_VALID));
		return DynamicSpecifications.bySearchFilter(filters.values(), News.class);
	}

	/**
	 * 根据uid 在时间范围内查找该租户添加的新闻资讯
	 * @param uid
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public List<News> findNews(Long uid,Date startDate,Date endDate){
		Map<String, SearchFilter> filters =  Maps.newHashMap();
		
		filters.put("uid", new SearchFilter("uid", Operator.EQ, uid));
		
		filters.put("crtDate1", new SearchFilter("crtDate",Operator.GTE,startDate));//条件:创建日期大于等于当前日期
		filters.put("crtDate2", new SearchFilter("crtDate",Operator.LT,endDate));//创建日期小于当前日期天数+1
		filters.put("status", new SearchFilter("status", Operator.EQ, Constants.STATUS_VALID));//状态为正常
		List<News> newss = newsDao.findAll(DynamicSpecifications.bySearchFilter(filters.values(), News.class));
		for(int i=0;i<newss.size();i++){
			newss.get(i).setContents(findNewsContentByNewsId(newss.get(i).getId()));
		}
		return newss;
	}
  /**
   * 保存图片集
   * @param category
   * @param url
   * @param newsid
   * @param uid
   */
  public void savaNewsPictures(String category,String url,Integer newsid,String uid){
	  NewsPictures pic = new NewsPictures();
	  pic.setCategory(category);
	  pic.setNewsId(newsid);
	  pic.setCrtDate(new Date());
	  pic.setUrl(url);
	  newsPicturesDao.save(pic);
  }
   
  public List<NewsPictures> getPictures(int newsid){
	return newsPicturesDao.findNewsPicturesByNewsId(newsid);
  }

	/**
	 * 修改名字
	 * @param id
	 * @param title
	 */
	public void changeName(Long id,String title){
		News news = newsDao.findOne(id);
		news.setTitle(title);
		news.setUpDate(new Date());
		newsDao.save(news);
	}
	
	/**
	 * 自动生成url
	 * @param EventId
	 * @return
	 */
	public String getNewsUrlByNewsId(Long newsId){
		StringBuilder sb=new StringBuilder(url);
		String sbInt=newsId+"";
		sb.append(sbInt);
		return sb.toString();
	}
	
	public void deletePicture(long id){
	    newsPicturesDao.delete(id);
	}
	
	@Autowired
	public void setNewsPicturesDao(NewsPicturesDao newsPicturesDao) {
		this.newsPicturesDao = newsPicturesDao;
	}

	/**
	 * 上传新闻图片
	 * @param file
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<Photo> uploadNewsPic(CommonsMultipartFile file, final MultipartHttpServletRequest request) throws ServiceException{
		if (ServletFileUpload.isMultipartContent(request)) {
			logger.debug("start process image upload...");
			Compress smallCompress = new Compress(180, 100, 1, Constants.PHOTO_SMALL);
			Compress mediumCompress = new Compress(300, 150, 1, Constants.PHOTO_MEDIUM);
			Compress largeCompress = new Compress(480, 300, 1, Constants.PHOTO_LARGE);
			List<Photo> photos = imageUploadService.saveImage(file, IMAGES_NEWS_PIC_DIR, smallCompress, mediumCompress, largeCompress);
			return photos;
		}
		return null;
	}
	/**
	 * 上传缩略图
	 * @param file
	 * @param request
	 * @return
	 */
	public List<Photo> uploadThumb(CommonsMultipartFile file, final MultipartHttpServletRequest request){
		if (ServletFileUpload.isMultipartContent(request)) {
			logger.debug("start process image upload...");
			Compress thumbCompress = new Compress(60, 60, 1, Constants.PHOTO_THUMBNAIL);
			Compress smallCompress = new Compress(300, 150, 1, Constants.PHOTO_SMALL);
			List<Photo> photos = imageUploadService.saveImage(file, IMAGES_NEWS_PIC_DIR, thumbCompress,smallCompress);
			return photos;
		}
		return null;
	}
	/**
	 * 保存新闻内容
	 * @param contents
	 * @param news
	 */
	public void saveNewsContent(List<NewsContent> contents,News news){
		Assert.notNull(news);
		Assert.notNull(news.getId());
		if(contents != null){
			for(int i=0;i<contents.size();i++){
				NewsContent content = contents.get(i);
				content.setSeq(i+1);
				content.setNewsId(news.getId());
				newsContentsDao.save(content);
			}
		}
	}
	/**
	 * 根据新闻id查找新闻内容
	 * @param nid
	 * @return
	 */
	public List<NewsContent> findNewsContentByNewsId(long nid){
		return newsContentsDao.findNewsContentByNewsId(nid);
	}
	/**
	 * 删除新闻内容
	 * @param id
	 */
	public void deleteContent(long id){
		newsContentsDao.delete(id);
	}
	
	/**
	 * 新闻静态化生成HTML
	 * @param nid
	 * @return
	 * @throws Exception
	 */
	public String contentPublishingHtml(final Long nid) throws Exception {
		// 准备数据
		News news = getNews(nid);
		NewsCategory newsCategory = newsCategoryService
				.getNewsCategoryById(getNews(nid).getCategoryId());
		int newid = getNews(nid).getId().intValue();
		news.setContents(findNewsContentByNewsId(newid));
		String uid = getNews(nid).getUid();
		Tenancy tenancy = tenancyService.findByUid(Long.valueOf(uid));

		Map<String, Object> root = new HashMap<String, Object>();
		/**
		 * 获取新闻内容 处理新闻详细图片链接 以适应不同分辨率的图片显示
		 */
		List<NewsContent> contents = news.getContents();
		Map<String,String> photos = new HashMap<String, String>();
		if(contents != null){
			for(int i=0;i< contents.size();i++){
				String photo = contents.get(i).getPhoto();
				if(photo != null && !photo.trim().equals("")){
					String suffix = photo.substring(photo.lastIndexOf("."));
					int index = photo.lastIndexOf("_");
					photo = photo.substring(0, index+1);
					photos.put(Constants.PHOTO_SMALL+contents.get(i).getId(),photo+Constants.PHOTO_SMALL+suffix);
					photos.put(Constants.PHOTO_MEDIUM+contents.get(i).getId(),photo+Constants.PHOTO_MEDIUM+suffix);
					photos.put(Constants.PHOTO_LARGE+contents.get(i).getId(),photo+Constants.PHOTO_LARGE+suffix);
					contents.get(i).setPhotos(photos);
				}
			}
		}
		root.put(News.PARMS_NEWS, news);
		root.put(NewsCategory.PARMS_NEWS_CATEGORY, newsCategory);
		root.put("contents", contents);
		root.put(Tenancy.PARMS_TENANCY, tenancy);
		root.put("proxyPath",proxyPath);
		root.put("fileProxyPath",fileProxyPath);
		String htmlFile = "news.html";
		freeMarkertUtil.analysisTemplate(templateFile, htmlFile, root);
		File file = new File(outPath + htmlFile);
		
		// 定义文件存放路径
		String finishPath = fileUploadService.uploadNewsHtml(file, nid).get(FileUploadService.FILE_URL);
		// 业务操作
		news.setPublish(News.IS_PUBLILSH);
		news.setUrl(finishPath);
		updateNewsByUrl(news);
		return finishPath;
	}
	
	
	public Page<News> findAllNewsByCategoryAndPagination(String uid, Integer categoryId, Pageable pageable){
		return newsDao.findPublishedNewsByCategory(uid, News.STATUS_VALIDE,News.IS_PUBLILSH, categoryId, pageable);
	}

	/**
	 * 查找
	 * @param uid
	 * @return
	 */
	public Long findTopnum(String uid){
		Long topNum = newsDao.findTopnum(uid);
		return topNum == null ? 0 :topNum;
	}
	/**
	 * 查询某租户的所有banner新闻
	 * @return
	 */
	public List<News> findBannerNews(String uid){
		return newsDao.findBannerNews(uid,News.STATUS_VALIDE,News.IS_BANNER);
	}
}
