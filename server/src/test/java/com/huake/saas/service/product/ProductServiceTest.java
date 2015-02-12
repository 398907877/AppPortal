 package com.huake.saas.service.product;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.huake.saas.product.repository.ProductDao;
import com.huake.saas.product.service.ProductService;

public class ProductServiceTest {
	private static Logger logger = LoggerFactory
			.getLogger(ProductServiceTest.class);
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductDao mockproductDao;
	
//	@Before
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//		ShiroTestUtils.mockSubject(new ShiroUser(3L, "foo", "Foo"));
//		//ShiroTestUtils.mockSubject(new Product(3L,"title","intro","pic",1,"detail","1"));
//	}
	
//	@Test
//	public void getProduct() {
//		mockproductDao.findOne(3L);
//	}
//	
//	@Test
//	public void saveProduct() {
//		Product product = ProductDate.randomProduct();
//		Date currentTime = new Date();
//		product.setCrtDate(currentTime);
//		product.setUpDate(currentTime);
//		productService.saveProduct(product);
//         //看是否更新 和 是否为空  notblank
//		assertThat(product.getCrtDate()).isEqualTo(currentTime);
//		assertThat(product.getUpDate()).isEqualTo(currentTime);
//		assertThat(product.getTitle()).isNotNull();
//		assertThat(product.getDetail()).isNotNull();
//		assertThat(product.getIntro()).isNotNull();
//	}
//	
//	@Test
//	public void updateProduct() {
//		// 如果明文密码不为空，加密密码会被更新.
//		Product product = ProductDate.randomProduct();
//		productService.updateProduct(product);
//		assertThat(product.getTitle()).isNotNull();
//		assertThat(product.getDetail()).isNotNull();
//		assertThat(product.getIntro()).isNotNull();
//
//	}
//  //  @Test
///*	public void deleteProduct() {
//    	Product product = ProductDate.randomProduct();
//	
//		logger.info("****************************"+product.getId());
//    	productService.deleteProduct(2L);
//    	Mockito.verify(mockproductDao).delete(2L);
//	}
//	*/
//	@Test
//	public void getAllProduct(){
//		productService.getAllProduct();
//	}
	
	/*@Test
	public void getUserProduct(){
		Map<String, Object> params = new TreeMap<String, Object>();
		productService.getUserProduct(2L,params,1,1,"auto");
		productService.getUserProduct(2L,params,1,1,"crtDate");
	}
	*/
	
}
