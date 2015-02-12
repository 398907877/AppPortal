package com.huake.saas.tcustomer.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.utils.Collections3;

import com.huake.saas.account.entity.User;
import com.huake.saas.account.service.AccountService;
import com.huake.saas.base.Constants;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.entity.Compress;
import com.huake.saas.base.service.ServiceException;
import com.huake.saas.category.entity.IndustryCategory;
import com.huake.saas.category.repository.IndustryCategoryDao;
import com.huake.saas.images.ImagesUploadService;
import com.huake.saas.images.Photo;
import com.huake.saas.tcustomer.entity.CustomerType;
import com.huake.saas.tcustomer.entity.Tcustomer;
import com.huake.saas.tcustomer.repository.CustomerTypeDao;
import com.huake.saas.tcustomer.repository.TcustomerDao;
import com.huake.saas.tenancy.entity.Tenancy;
import com.huake.saas.tenancy.service.TenancyService;

/**
 * 租户客户表
 * 
 * @author hyl
 * 
 */
@Component
@Transactional
public class TcustomerService {

	public static final String IMAGES_tcustomer_PIC_DIR = "customer";
	@Autowired
	private TcustomerDao TCDao;
	@Autowired
	private CustomerTypeDao CTDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private TenancyService tenancyService;

	@Autowired
	private IndustryCategoryDao categoryDao;
	
	@Autowired
	private ImagesUploadService imageUploadService;
	

	/**
	 * 分页
	 * 
	 * @param userId
	 *            用户id(可以不管)
	 * @param searchParams
	 *            （查询条件 ， 是以 key=EQ_name value=xxx ）
	 * @param pageNumber
	 *            (可以不管)
	 * @param pageSize
	 *            (可以不管)
	 * @param sortType
	 *            (可以不管)
	 * @return
	 */
	public Page<Tcustomer> findByCondition(Long userId,
			Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Specification<Tcustomer> spec = buildSpecification(userId, searchParams);
		return TCDao.findAll(spec, pageRequest);
	}

	/**
	 * 分页
	 * 
	 * @param uid
	 * @param searchParams
	 *            （查询条件 ， 是以 key=EQ_name value=xxx ）
	 * @param pageNumber
	 *            (可以不管)
	 * @param pageSize
	 *            (可以不管)
	 * @param sortType
	 *            (可以不管)
	 * @return
	 */
	public Page<Tcustomer> findByCondition2(Long uid,
			Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {


		Pageable pageable = buildPageRequest(pageNumber, pageSize, sortType);

		String hql = "select distinct customer from Tcustomer customer inner  join customer.customerTypes type ";
		String countHql = "select count(distinct customer) from Tcustomer customer inner join customer.customerTypes type ";
		String whereJpql = this.buildString(uid, searchParams);
		
		String  orderby = this.buildOrderBy(sortType);
		
		System.out.println(hql);
		System.out.println(countHql);
		System.out.println(whereJpql);
		System.out.println(orderby);
		return TCDao.getScrollDataByJpql(hql, countHql, whereJpql,
				orderby, pageable);
	}

	/**
	 * 
	 * @param file
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<Photo> uploadPic(CommonsMultipartFile file, final MultipartHttpServletRequest request) throws ServiceException{
		if (ServletFileUpload.isMultipartContent(request)) {
			Compress smallCompress = new Compress(300, 150, 1, Constants.PHOTO_SMALL);
			Compress thumbCompress = new Compress(60, 60, 1, Constants.PHOTO_THUMBNAIL);
			List<Photo> photos = imageUploadService.saveImage(file, IMAGES_tcustomer_PIC_DIR, smallCompress,thumbCompress);
			return photos;
		}
		return null;
	}
	
	/**
	 * 查询单个Tcustomer对象
	 * 
	 * @param id
	 * @return
	 */
	public Tcustomer get(Long id) {
		return TCDao.findOne(id);
	}

	/**
	 * 保存租户客户
	 * 
	 * @param customer
	 * @param types
	 */
	public void save(Tcustomer customer, String[] typeIds, String uid) {

		Tenancy tenancy = tenancyService.getTenancy(Long.valueOf(uid));
		for (String id : typeIds) {
			IndustryCategory type = categoryDao.findOne(Long.valueOf(id));
			customer.getCustomerTypes().add(type);
		}
		customer.setCreateDate(new Date());
		customer.setUpdateDate(new Date());
		customer.setStatus(Tcustomer.normal);
		customer.setTenancy(tenancy);
		TCDao.save(customer);
	}

	/**
	 * 修改租户客户
	 * 
	 * @param customer
	 * @param typeIds
	 */
	public void update(Tcustomer customer, String[] typeIds) {
		Tcustomer cus = TCDao.findOne(customer.getId());
		cus.setAddress(customer.getAddress());
		cus.setContent(customer.getContent());
		cus.setFax(customer.getFax());
		cus.setName(customer.getName());
		cus.setTel(customer.getTel());
		cus.setBusinessScope(customer.getBusinessScope());
		cus.setLinkman(customer.getLinkman());
		cus.setProducts(customer.getProducts());;
		cus.setUpdateDate(new Date());
		cus.setPic(customer.getPic());
		Set<IndustryCategory> set = new HashSet<IndustryCategory>();
		for (String id : typeIds) {
			IndustryCategory type = categoryDao.findOne(Long.valueOf(id));
			set.add(type);
		}
		cus.setCustomerTypes(set);
		TCDao.save(cus);
	}

	/**
	 * 删除租户客户 伪
	 * 
	 * @param customer
	 */
	public void del(Tcustomer customer) {
		Tcustomer cus = TCDao.findOne(customer.getId());
		cus.setStatus(Tcustomer.del);
		TCDao.save(cus);
	}

	/**
	 * 查询单个客户类型
	 * 
	 * @param id
	 * @return
	 */
	public CustomerType getType(long id) {
		return CTDao.findOne(id);
	}

	/**
	 * 保存客户分类类型
	 * 
	 * @param ctype
	 */
	public void saveType(CustomerType ctype) {
		ctype.setCreateDate(new Date());
		ctype.setStatus(CustomerType.normal);
		CTDao.save(ctype);
	}

	/**
	 * 删除客户分类 伪
	 * 
	 * @param ctype
	 */
	public void delType(CustomerType ctype) {
		CustomerType type = CTDao.findOne(ctype.getId());
		type.setStatus(CustomerType.del);
		CTDao.save(type);
	}

	/**
	 * 修改客户分类
	 * 
	 * @param ctype
	 */
	public void updateType(CustomerType ctype) {
		CustomerType type = CTDao.findOne(ctype.getId());
		type.setTypeContent(ctype.getTypeContent());
		CTDao.save(type);
	}

	/**
	 * 通过租户id查询相关的客户分类
	 * 
	 * @param id
	 * @return
	 */
	public List<CustomerType> findCustomerTypesByUid(long id) {
		return CTDao.findListByUidAndStatus(id, CustomerType.normal);
	}

	/**
	 * 用于首页显示，修改租户客户的详细页面调用findCustomerTypesByUid 通过 租户ID去查 首页要通过用户的ID ，判断用户是否为
	 * 超级管理员，是则取出所有的类型，不是则取出租户ID相关的类型
	 * 
	 * @param id
	 * @return
	 */
	public List<CustomerType> findCustomerTypesByUserid(long id) {
		User user = accountService.getUser(id);
		if (!User.USER_ROLE_ADMIN.equals(user.getRoles())) {
			Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
			filters.put("status", new SearchFilter("status", Operator.EQ,
					CustomerType.normal));
			filters.put("uid", new SearchFilter("uid", Operator.EQ,
					user.getUid()));
			Specification<CustomerType> spec = DynamicSpecifications
					.bySearchFilter(filters.values(), CustomerType.class);
			return CTDao.findAll(spec);
		}
		return this.findCustomerTypesByUid(user.getUid());
	}

	/**
	 * 进行分页查询
	 * 
	 * @param userId
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<CustomerType> findByTypeCondition(Long userId,
			Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Specification<CustomerType> spec = buildTypeSpecification(userId,
				searchParams);
		return CTDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * @param userId
	 */
	private Specification<CustomerType> buildTypeSpecification(Long userId,
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		if (userId != null && !"".equals(userId)) {
			User user = accountService.getUser(userId);
			if (!User.USER_ROLE_ADMIN.equals(user.getRoles())) {
				filters.put("uid",
						new SearchFilter("uid", Operator.EQ, user.getUid()));
			}
		}
		filters.put("status", new SearchFilter("status", Operator.EQ,
				CustomerType.normal));
		Specification<CustomerType> spec = DynamicSpecifications
				.bySearchFilter(filters.values(), CustomerType.class);
		return spec;
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("createDate".equals(sortType)) {
			sort = new Sort(Direction.DESC, "createDate");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * @param userId
	 */
	private Specification<Tcustomer> buildSpecification(Long userId,
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		if (userId != null && !"".equals(userId)) {
			User user = accountService.getUser(userId);
			if (!User.USER_ROLE_ADMIN.equals(user.getRoles())) {
				filters.put("tenancy.uid", new SearchFilter("tenancy.uid",
						Operator.EQ, user.getUid()));
			}
		}
		filters.put("status", new SearchFilter("status", Operator.EQ,
				Tcustomer.normal));
		Specification<Tcustomer> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), Tcustomer.class);
		return spec;
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * @param userId
	 */
	private String buildString(Long uid, Map<String, Object> searchParams) {

		StringBuffer whereSql = new StringBuffer();
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		whereSql.append(" 1 = 1 ");
		/*if (userId != null && !"".equals(userId)) {
			User user = accountService.getUser(userId);
			if (!User.USER_ROLE_SYS_ADMIN.equals(user.getRoles())) {
				filters.put("customer.tenancy.uid", new SearchFilter(
						"customer.tenancy.uid", Operator.EQ, user.getUid()));
			}
		}*/
		filters.put("customer.tenancy.uid", new SearchFilter(
				"customer.tenancy.uid", Operator.EQ, uid));
		filters.put("customer.status", new SearchFilter("customer.status",
				Operator.EQ, Tcustomer.normal));
		
		return bySearchWhere(filters.values(),whereSql);

	}

	/**
	 * 生成条件语句
	 * @param filters
	 * @param whereSql
	 * @return
	 */
	private String bySearchWhere(Collection<SearchFilter> filters,
			StringBuffer whereSql) {

		if (Collections3.isNotEmpty(filters)) {
			for (SearchFilter filter : filters) {
				switch (filter.operator) {
				case EQ:
					whereSql.append(" and " + filter.fieldName.replace("0", ".") + " = '"
							+ filter.value +"'");
					break;
				case LIKE:
					whereSql.append(" and " + filter.fieldName.replace("0", ".") + " like '%"
							+ filter.value + "%'");
					break;

				}
			}

		}
		return whereSql.toString();

	}

	
	
	/**
	 * 创建排序条件.
	 */
	private String  buildOrderBy(
			String sortType) {
	
		if ("auto".equals(sortType)) {
			return "order by customer.id desc";
		} else if ("createDate".equals(sortType)) {
			return "order by customer.createDate desc";
		}
		return "";
	}
	/**
	 * 根据会员企业类型
	 * 分页查询会员企业列表
	 * @param typeId
	 * @param uid
	 * @param pageNum
	 * @return
	 */
	public Page<Tcustomer> findTcustomerByType(long typeId,Long uid,int pageNum){
		
		Pageable pageable = new PageRequest(pageNum, BaseEntity.DATE_MAX, new Sort(Direction.DESC, new String[] { "createDate" }));
		if(typeId == 0){
			return TCDao.findTcustomer(Tcustomer.normal, uid, pageable);
		}
		return TCDao.findTcustomerByType(typeId, Tcustomer.normal, uid, pageable);
	}
	/**
	 * 根据租户id查找所有 会员企业
	 * @param uid
	 * @return
	 */
	public List<Tcustomer> findAllTcustomer(Long uid){
		return TCDao.findAllTcustomer(Tcustomer.normal, uid);
	}
	
	public Page<Tcustomer> findByCondition(Long uid,int pageNum,String condition){
		Pageable pageable = new PageRequest(0, new Integer(5), new Sort(Direction.DESC, new String[] { "createDate" }));
		Page<Tcustomer> result = TCDao.findByCondition(Tcustomer.normal, uid, condition, pageable);
		
		return result;
	}
}
