package com.huake.saas.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huake.saas.category.entity.IndustryCategory;
import com.huake.saas.category.repository.IndustryCategoryDao;

@Service
public class IndustryCategoryService {
	@Autowired
	private IndustryCategoryDao categoryDao;
	
	public List<IndustryCategory> getAllCategory(){
		return categoryDao.findAll();
	}
	
	public IndustryCategory findById(Long id){
		return categoryDao.findOne(id);
	}
	
	
}
