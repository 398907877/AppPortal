package com.huake.saas.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.huake.saas.BaseTransactionalTestCase;
import com.huake.saas.tcustomer.entity.Tcustomer;
import com.huake.saas.tcustomer.repository.TcustomerDao;

public class TcustomerDaoTest extends BaseTransactionalTestCase{
	
	@Autowired
	private TcustomerDao tcustomerDao;
	
	@Test
	public void testFindTcustomer(){
		Pageable pageable = new PageRequest(0, new Integer(5), new Sort(Direction.DESC, new String[] { "createDate" }));
		Page<Tcustomer> result = tcustomerDao.findTcustomer(Tcustomer.normal,new Long("20140417122551"), pageable);
		
		System.out.println(result.getContent().size());
	}
	
	@Test
	public void testFindTcustomerByType(){
		Pageable pageable = new PageRequest(0, new Integer(5), new Sort(Direction.DESC, new String[] { "createDate" }));
		Page<Tcustomer> result = tcustomerDao.findTcustomerByType(new Long(1),Tcustomer.normal,new Long("20140417122551"), pageable);
		
		System.out.println(result.getContent().size());
	}
	
	@Test
	public void testFindByCondition(){
		Pageable pageable = new PageRequest(0, new Integer(5), new Sort(Direction.DESC, new String[] { "createDate" }));
		Page<Tcustomer> result = tcustomerDao.findByCondition(Tcustomer.normal, new Long("20140417122551"), "%app%", pageable);
		
		System.out.println(result.getContent().get(0).getName());
		System.out.println(result.getContent().size());
	}
}
