package com.huake.saas;

import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public abstract class BaseTransactionalTestCase extends
		SpringTransactionalTestCase {

}
