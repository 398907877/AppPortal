package com.huake.saas.dictionary.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.web.Servlets;

import com.huake.dict.entity.Dictionary;
import com.huake.saas.auth.web.controller.AuthController;
import com.huake.saas.base.web.controller.BaseController;
import com.huake.saas.dictionary.service.DictionaryService;
import com.huake.saas.news.entity.News;

/**
 * 
 * @author zjy
 *数据字典模块的相关操作
 */
@Controller("dictionaryController")
@RequestMapping("/mgr/dict/**")
public class DictionaryController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private static final String PAGE_SIZE = "5";
	
	@Autowired
	private DictionaryService dictionarySerivce;
	
	/**
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			Model model,
			ServletRequest request){
		Long userId = getCurrentUserId();
		logger.info("userId"+userId+"数据字典首页访问");
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<Dictionary> dicts = dictionarySerivce.findByCondition(userId,searchParams, pageNumber, pageSize, "seq");
		
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		model.addAttribute("dicts", dicts);
		return "dict/index";
	}
	
	@RequestMapping(value="/load")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<Dictionary> load(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "50" ) int pageSize,
			@RequestParam(value="search_EQ_className",required = false)String className,
			ServletRequest request){
		Long userId = getCurrentUserId();
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		List<Dictionary> dicts = dictionarySerivce.findByCondition(userId,searchParams, pageNumber, pageSize, null).getContent();
		return dicts;
	}
	
	@ModelAttribute("classNames")
	public List<String> getClassNames(){
		List<Dictionary> dictionarys = dictionarySerivce.findDictViews();
		List<String> classNames = new ArrayList<String>();
		for (Dictionary dictionary : dictionarys) {
			if(!classNames.contains(dictionary.getClassName())){
				classNames.add(dictionary.getClassName());
			}
		}	
		return classNames;
	}
	
	/**
	 * 跳到添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String toAdd(@RequestParam(value="search_EQ_className",required = false)String className,
			Model model){
		Dictionary dict = new Dictionary();
		List<Dictionary> dictionarys = new ArrayList<Dictionary>();
		if(className != null && className.replaceAll(" ", "") != ""){
			dict.setClassName(className);
			dictionarys = dictionarySerivce.findByClassName(className);
		}
		int maxSeq = 1;
		for (Dictionary dic : dictionarys) {
			if(dic.getSeq() > maxSeq){
				maxSeq =dic.getSeq();
			}
		}
		dict.setSeq(maxSeq);
		model.addAttribute("dict",dict);
		return "dict/add";
	}
	
	@RequestMapping(value = "getSeq", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public int getSeq(@RequestParam(value="className",required = true)String className){
		int maxSeq = 0;
		if(className != null && className.replaceAll(" ", "") != ""){
			List<Dictionary> dictionarys = dictionarySerivce.findByClassName(className);
			for (Dictionary dic : dictionarys) {
				if(dic.getSeq() > maxSeq){
					maxSeq =dic.getSeq();
				}
			}
		}
		return maxSeq+1;
	}
	
	@RequestMapping(value="/updateSeq",method=RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String updateSeq(@RequestParam(value="id",required = true)String id,@RequestParam(value="order",required = false)String order){
			String[] ids=id.split(",");
			String[] seqs=order.split(",");
			for(int i=0;i<ids.length;i++){
				Dictionary dictionary = dictionarySerivce.findDictById(Integer.parseInt(ids[i]));
				dictionary.setSeq(Integer.parseInt(seqs[i]));
				dictionarySerivce.saveDict(dictionary);
			}
	
			return "{'status':'ok'}";
	}
	/**
	 * 删除数据字典
	 * @param Id
	 * @return
	 */
	@RequestMapping(value="/delete/{Id}",method=RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String deleteDict(@PathVariable final Integer Id){
		dictionarySerivce.deleteDictById(Id);
		return  "{'status':'ok'}";
	}
	
	/**
	 * 保存
	 * @param dictionary
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/save/{oper}",method=RequestMethod.POST)
	public String saveDict(@ModelAttribute("dictionary")Dictionary dictionary,
			@PathVariable("oper")String oper,
			RedirectAttributes redirectAttributes){
		dictionarySerivce.saveDict(dictionary);
		List<Dictionary> dicts = dictionarySerivce.findByClassName(dictionary.getClassName());
		int minSeq = dictionary.getSeq();
		for(int i=0;i<dicts.size();i++){
			int seq = dicts.get(i).getSeq();
			if(minSeq <= seq && dictionary.getName() !=dicts.get(i).getName()){
				
				dicts.get(i).setSeq(seq+1);
			}
		}
		for(Dictionary dict:dicts){
			dictionarySerivce.saveDict(dict);
		}
		String operator = "add".equals(oper)==true ? "添加":"修改";
		redirectAttributes.addFlashAttribute("message", "数据字典"+ operator +"成功");
		return "redirect:/mgr/dict/index";
	}
	
	/**
	 * 跳到修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String toUpdate(@PathVariable("id")Integer id,
			Model model){
		Dictionary dict = dictionarySerivce.findDictById(id);
		model.addAttribute("dict",dict);
		return "dict/edit";
	}
	/**
	 * 判断某类ClassName的key是否存在
	 * @param 
	 * @return
	 */
	@RequestMapping(value="existKey",method=RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String existKey(@RequestParam(value="className")String className,@RequestParam(value="key")String key){
		if(className != null && className.replaceAll(" ", "") != ""){
			List<Dictionary> dictionarys = dictionarySerivce.findByClassName(className);
			for(Dictionary dict:dictionarys){
				if(dict.getKey().equals(key)){
					return "false";
				}
			}
		}
		return "true";
	}
}
