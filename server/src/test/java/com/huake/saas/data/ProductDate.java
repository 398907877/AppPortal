package com.huake.saas.data;

import org.springside.modules.test.data.RandomData;

import com.huake.saas.account.entity.User;
import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.product.entity.Product;

public class ProductDate {
	public static Product randomProduct() {
		Product product = new Product();
		product.setTitle(randomTitle());
		product.setDetail(randomDetail());
		product.setIntro(randomIntro());
		int categoryId = (int) randomCategory();
		product.setCategoryId(categoryId);
		User user = new User();
		product.setUid(String.valueOf(user.getUid()));
		product.setStatus(BaseEntity.STATUS_VALIDE);
		return product;
	}

	public static String randomTitle() {
		return RandomData.randomName("Product");
	}
	
	
	public static String randomPic() {
		return RandomData.randomName("pic");
	}
	
	public static String randomDetail() {
		return RandomData.randomName("detail");
	}
	
	public static String randomIntro() {
		return RandomData.randomName("intro");
	}
	
	public static long randomCategory() {
		return RandomData.randomId();
	}
}
