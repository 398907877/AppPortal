package com.huake.saas.activity.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.activity.entity.EventPhoto;
import com.huake.saas.base.repository.GenericRepository;

public interface EventPhotoDao extends GenericRepository<EventPhoto, Long> ,PagingAndSortingRepository<EventPhoto, Long>,JpaSpecificationExecutor<EventPhoto>{

}
