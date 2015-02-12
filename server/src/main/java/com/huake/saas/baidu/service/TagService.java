package com.huake.saas.baidu.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.huake.saas.baidu.entity.Tag;
import com.huake.saas.baidu.repository.TagDao;



@Component
@Transactional
public class TagService {
/**
 * 分组
 */
	@Autowired
	private TagDao tagDao;
	
public TagDao getTagDao() {
	return tagDao;
}

public void setTagDao(TagDao tagDao) {
	this.tagDao = tagDao;
}
/**
 * 查询全部Tag
 * @return
 */
public List<Tag> findAllTag(){
	return (List<Tag>)tagDao.findAll();
}


/**
 * 新增tag分组
 */
public void saveTag(Tag tag){
	tagDao.save(tag);
}

/**
 *分页查询tag分组信息
 */
public Page<Tag> findByPage(int pageNumber,int pageSize,
		  String sortType){
	PageRequest pageRequest =buildPageRequest(pageNumber,pageSize,sortType);
	return tagDao.findAll(pageRequest);
}

/**
 * 创建分页请求
 */
private PageRequest buildPageRequest(int pageNumber,int pageSize,String sortType){
	Sort sort=null;
	if("auto".equals(sortType)){
		sort=new Sort(Direction.DESC,"id");
		
	}else if("createDate".equals(sortType)){
		sort=new Sort(Direction.DESC,"createDate");
	}
	return new PageRequest(pageNumber - 1,pageSize,sort);
}


}
