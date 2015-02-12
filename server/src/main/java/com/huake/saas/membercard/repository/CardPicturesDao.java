package com.huake.saas.membercard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.membercard.entity.CardPictures;
import com.huake.saas.product.entity.ProductPictures;

/**
 * 
 * @author chen weirong
 *
 */
public interface CardPicturesDao  extends PagingAndSortingRepository<CardPictures, Long>, JpaSpecificationExecutor<CardPictures>{
	@Query("from CardPictures c where c.cardId= :cardId")
    CardPictures findCardPicturesByCardId(@Param("cardId")Integer cardId);
}
