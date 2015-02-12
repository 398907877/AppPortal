package com.huake.saas.company.web.api.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.base.web.controller.BaseApiController;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.company.entity.Company;
import com.huake.saas.company.entity.CompanyCategory;
import com.huake.saas.company.entity.CompanyDetail;
import com.huake.saas.company.entity.CompanyPictures;
import com.huake.saas.company.service.CompanyCategoryService;
import com.huake.saas.company.service.CompanyService;
import com.huake.saas.news.entity.NewsCategory;

@Controller
@RequestMapping(value = "/api/v1/company")
public class CompanyApiController extends BaseApiController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyCategoryService companyCategoryService;

	/**
	 * 企业列表
	 * 
	 * @param categoryId
	 * @param first
	 * @param max
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public CompanyDetail getCompanys(
			@RequestParam(value = "uid", required = true) Long uid,
			@RequestParam(value = "categoryId", required = false) String categoryId,
			@RequestParam(value = "pageNum", required = false) Integer pageNum) {
		boolean isFirst = false;
		int max = 0;
		if (null == pageNum || pageNum <= 0) {
			pageNum = 1;
			max = BaseEntity.PAGE_SIZE;
		} else if (null != pageNum && pageNum > 0 && null != categoryId&&!"".equals(categoryId)) {
			max = BaseEntity.DATE_MAX;
		} else if (null != pageNum && pageNum > 0 && (null == categoryId||"".equals(categoryId))){
			max = BaseEntity.DATE_MAX;
			isFirst = true;
		}
		List<Company> apis = new ArrayList<Company>();
		List<Company> aps = new ArrayList<Company>();
		List<CompanyCategory> category = companyCategoryService
				.getAllCompanyCategorys(NewsCategory.STATUS_VALIDE, uid);
		List<Company> details = null;
		if (null == categoryId || "".equals(categoryId)) {
			if (null != category && category.size() > 0) {
				for (CompanyCategory type : category) {
					Map<String, Object> searchParams = new HashMap<String, Object>();
					categoryId = type.getId().toString();
					searchParams.put("EQ_categoryId", categoryId);
					searchParams.put("EQ_status", BaseEntity.STATUS_VALIDE);
					List<Company> companies = companyService.getUserProductApi(
							uid, searchParams, pageNum, max,
							BaseEntity.PAGE_CRTDATE_DESC).getContent();
					details = companyService
							.getCompanyDetailsByCompanys(companies);
					for (Company api : details) {
						apis.add(api);
					}
				}
				if (isFirst && apis != null && apis.size() >= 10) {
					for (int i = 0; i < 10; i++) {
						aps.add(apis.get(i));
					}
				} else {
					for (int i = 0; i < apis.size(); i++) {
						aps.add(apis.get(i));
					}
				}
			}
		} else {
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("EQ_categoryId", categoryId);
			searchParams.put("EQ_status", BaseEntity.STATUS_VALIDE);
			List<Company> companies = companyService.getUserProductApi(uid,
					searchParams, pageNum, max, BaseEntity.PAGE_CRTDATE_DESC)
					.getContent();
			details = companyService.getCompanyDetailsByCompanys(companies);
			for (Company api : details) {
				apis.add(api);
			}
		}
		// 获取缩略图
		/*for (Company detail : apis) {
			int picid = Integer.parseInt(detail.getId().toString());
			List<CompanyPictures> pictures = companyService.getPictures(picid);
			if (pictures != null && pictures.size() > 0) {
				detail.setPic(pictures.get(0).getUrl());
			}
		}*/
		CompanyDetail detail = new CompanyDetail();
		detail.setCategory(category);

		if (!isFirst) {
			detail.setCompany(apis);
		} else {
			detail.setCompany(aps);
		}
		return detail;
	}

	/**
	 * 企业详情
	 * 
	 * @param id
	 *            (企业Id)
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Company eventDetail(@PathVariable final Long id,
			@RequestParam(value = "uid", required = true) Long uid) {
		Company company = companyService.savaCompanyDetail(companyService.getCompany(id));
		long categoryId = company.getCategoryId();
		CompanyCategory category = companyCategoryService
				.getCompanyCategory(categoryId);
		company.setCategory(category.getName());
		return company;
	}

}
