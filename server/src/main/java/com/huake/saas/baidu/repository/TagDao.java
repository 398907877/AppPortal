

package com.huake.saas.baidu.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.baidu.entity.Tag;



public interface TagDao extends PagingAndSortingRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

}
