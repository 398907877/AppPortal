package com.huake.saas.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.huake.saas.baidu.entity.Tag;
import com.huake.saas.baidu.repository.TagDao;
import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.user.repository.TenancyUserDao;


@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(defaultRollback = false)
public class TagTest extends SpringTransactionalTestCase{
@Autowired
private TagDao tagDao;

@Autowired
private TenancyUserDao tenancyUserDao;
/**
 * 测试分组的插入
 * @throws Exception
 */
@Test
public void testsave() throws Exception {
	Tag tag=new Tag();
	tag.setName("福州");
	TenancyUser tenancyUser=new TenancyUser();
	tenancyUser.setName("大大的");
	List<TenancyUser> list=new ArrayList<TenancyUser>();
    list.add(tenancyUser);
    tag.setTenancyUser(list);
    tenancyUser.setTag(tag);
   
   //数据持久化
	tagDao.save(tag);
	tenancyUserDao.save(tenancyUser);
	
}

}
