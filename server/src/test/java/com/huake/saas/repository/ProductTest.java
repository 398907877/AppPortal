package com.huake.saas.repository;



import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.huake.saas.base.entity.BaseEntity;
import com.huake.saas.invitation.entity.Invitation;
import com.huake.saas.invitation.respository.InvitationDao;
import com.huake.saas.product.entity.Product;
import com.huake.saas.product.repository.CategoryDao;
import com.huake.saas.product.repository.ProductDao;
import com.huake.saas.supply.entity.SupplyDemand;
import com.huake.saas.supply.repository.SupplyDemandDao;

/**
 * 
 * @author hxl 
 *test
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(defaultRollback = false)
public class ProductTest extends SpringTransactionalTestCase{
	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private ProductDao productDao;

	@Autowired 
	private InvitationDao invitationDao;
	
	@Autowired
	private SupplyDemandDao sdDao;
	/**
	 * 产品展示测试
	 */
	@Test
	public void testFindProducts() throws Exception {
		Pageable pageable = new PageRequest(0, new Integer(5), new Sort(Direction.DESC, new String[] { "crtDate" }));
		Page<Product> result = productDao.findProducts(BaseEntity.STATUS_VALIDE, "20140417122551",pageable );
		System.out.println(result.getContent().size());
	}
	
	@Test
	public void testFindByCategory(){
		Pageable pageable = new PageRequest(0, new Integer(5), new Sort(Direction.DESC, new String[] { "crtDate" }));
		Page<Product> result = productDao.findByCategory(Integer.valueOf(1), BaseEntity.STATUS_VALIDE, "20140417122551", pageable);
		
		System.out.println(result.getContent().size());
	}
	
	@Test
	public void testFindInvitation(){
		Pageable pageable = new PageRequest(0, new Integer(5), new Sort(Direction.DESC, new String[] { "crtDate" }));
		Page<Invitation> result = invitationDao.findInvitation(BaseEntity.STATUS_VALIDE,"20140417122551", pageable);
		
		System.out.println(result.getContent().size());
	}
	
	@Test 
	public void testFindSupplyDemandByType(){
		Pageable pageable = new PageRequest(0, new Integer(5), new Sort(Direction.DESC, new String[] { "crtDate" }));
		Page<SupplyDemand> result = sdDao.findSupplyDemandByType(BaseEntity.STATUS_VALIDE, SupplyDemand.SUPPLY_DEMAND_SUP, "20140417122551", pageable);
		
		System.out.println(result.getContent().size());
	}
}

