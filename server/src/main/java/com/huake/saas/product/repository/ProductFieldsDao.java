package com.huake.saas.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.huake.saas.product.entity.ProductFields;;

public interface ProductFieldsDao extends PagingAndSortingRepository<ProductFields, Long>, JpaSpecificationExecutor<ProductFields>{
	List<ProductFields> findByProductId(Long productId);
}
