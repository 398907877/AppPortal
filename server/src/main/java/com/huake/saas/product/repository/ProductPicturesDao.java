package com.huake.saas.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.product.entity.ProductPictures;

public interface ProductPicturesDao  extends PagingAndSortingRepository<ProductPictures, Long>, JpaSpecificationExecutor<ProductPictures>{
	@Query("from ProductPictures c where c.productId= :productid")
    List<ProductPictures> findProductPicturesByProductId(@Param("productid")Integer productid);
}
