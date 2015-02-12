package com.huake.saas.membercard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.huake.saas.base.repository.GenericRepository;
import com.huake.saas.membercard.entity.CardRegister;
import com.huake.saas.membercard.entity.Mcard;
import com.huake.saas.product.entity.Product;
import com.huake.saas.product.entity.ProductFields;

public interface CardRegisterDao extends GenericRepository<CardRegister, Long> ,PagingAndSortingRepository<CardRegister, Long>,JpaSpecificationExecutor<CardRegister>{
	
	   @Query("from CardRegister cr where cr.cardId= :cardId and cr.status= :status")
	   List<CardRegister> findRegistersByCardId(@Param("cardId")Integer cardId,@Param("status")String status);
	   @Query("from CardRegister cr where cr.cardNumber= :cardNumber and cr.status= :status")
	CardRegister findCardRegisterByCardNumber(@Param("cardNumber")String cardNumber, @Param("status")String status);
}
