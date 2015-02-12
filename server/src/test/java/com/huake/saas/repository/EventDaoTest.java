package com.huake.saas.repository;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.huake.saas.BaseTransactionalTestCase;
import com.huake.saas.activity.entity.Event;
import com.huake.saas.activity.repository.EventDao;
import com.huake.saas.activity.service.EventService;

public class EventDaoTest extends BaseTransactionalTestCase{

	@Autowired
	private EventService eventService;
	@Autowired
	private EventDao eventDao;
	
	@Test
	public void testQueryAll(){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		eventService.getAllEventsByUid(new Long("20140417122551"), searchParams, 1, 1, "crtDate");
	}
	
	//最新活动信息
	@Test
	public void testFindByStatus() throws Exception {
		
		Pageable pageable = new PageRequest(0, new Integer(5), new Sort(Direction.DESC, new String[] { "crtDate" }));
		Page<Event> events = eventDao.findByStatus(Event.STATUS_VALID,new Long("20140417122551"),pageable);
		
		System.out.println(events.getContent().size());
}
}
