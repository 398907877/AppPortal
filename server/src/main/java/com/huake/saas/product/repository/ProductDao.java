package com.huake.saas.product.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.product.entity.Product;

public interface ProductDao extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {
   @Query("from Product p where p.categoryId= :cid and p.uid= :uid")
   List<Product> findProductsByCId(@Param("cid")Integer cid,@Param("uid")String uid);

   @Query("select p from Product p where p.categoryId = :cid and p.uid = :uid and p.status = :status")
   Page<Product> findByCategory(@Param("cid")Integer cid,@Param("status")String status,@Param("uid")String uid,Pageable pageable);
  
   @Query("select p from Product p where p.uid = :uid and p.status = :status")
   Page<Product> findProducts(@Param("status")String status,@Param("uid")String uid,Pageable pageable);
}
