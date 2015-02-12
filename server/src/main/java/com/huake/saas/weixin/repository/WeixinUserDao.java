package com.huake.saas.weixin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.weixin.model.WeixinUser;

public interface WeixinUserDao extends PagingAndSortingRepository<WeixinUser, Long>,JpaSpecificationExecutor<WeixinUser>{

	public List<WeixinUser> findByOpenid(String openId);
}
