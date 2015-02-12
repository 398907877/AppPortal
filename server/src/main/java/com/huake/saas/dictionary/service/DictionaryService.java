package com.huake.saas.dictionary.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.huake.dict.entity.Dictionary;
import com.huake.dict.repository.DictionaryDao;
import com.huake.dict.service.DictViewService;
import com.huake.saas.account.service.AccountService;

/**
 * 扩展的数据字典服务
 * @author zjy
 */
@Component
@Transactional
public class DictionaryService {

	@Autowired
	private DictionaryDao dictinaryDao;
	
	@Autowired
	private DictViewService dictViewService;
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 获取全部数据字典
	 * @return
	 */
	public List<Dictionary> findDictViews(){
		return (List<Dictionary>) dictinaryDao.findAll();
	}
	
	
	
	  /**
	    * 查询数据字典分页合集
	*@param userId 
	    * @param searchParams 自定义查询参数，LINK_name
	    * @param pageNumber  页数
	    * @param pageSize  每页显示条数
	    * @param sortType  排序字段
	    * @return
	    */
		public Page<Dictionary> findByCondition( Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
				String sortType) {
			PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
			Specification<Dictionary> spec = buildSpecification(userId,searchParams);
			return dictinaryDao.findAll(spec, pageRequest);
		}
		
		private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
			Sort sort = new Sort(Direction.ASC, "seq");
			
			return new PageRequest(pageNumber - 1, pagzSize, sort);
		}
		
		/**
		 * 创建动态查询条件组合.
		 * @param userId 
		 */
		private Specification<Dictionary> buildSpecification( Long userId, Map<String, Object> searchParams) {
			Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
			
			Specification<Dictionary> spec = DynamicSpecifications.bySearchFilter(filters.values(), Dictionary.class);
			return spec;
		}
		
		/**
		 * 根据className查找数据字典
		 * @param className
		 * @return
		 */
		public List<Dictionary> findByClassName(String className){
			return dictViewService.getDictViewList(className);
		}
		
		/**
		 * 删除数据字典
		 * @param id
		 */
		public void deleteDictById(Integer id){
			dictViewService.deleteDictionary(id);
		}
		/**
		 * 保存数据字典
		 */
		public void saveDict(Dictionary dictionary){
			dictViewService.saveDictionary(dictionary);
		}
		/**
		 *  根据id查找数据字典
		 * @param id
		 */
		public Dictionary findDictById(Integer id){
			return dictViewService.findById(id);
		}
}
