package com.fashionSuperman.fs.dao.actortest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fashionSuperman.fs.dao.daoi.ActorDaoI;
import com.fashionSuperman.fs.dao.entity.Actor;
import com.fashionSuperman.fs.dao.mapper.ActorMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/root-context.xml" })
public class ActorTest {
	@Autowired
	private ActorMapper actorMapper;
	@Autowired
	private ActorDaoI actorDao;
	@Test
	public void testGetOne_mapper(){
		String id = "1";
		Actor actor = actorMapper.selectByPrimaryKey(Short.valueOf(id));
		System.out.println(actor.getFirstName());
	}
	@Test
	public void testGetOne_dao(){
		String id = "1";
		Actor actor = actorDao.getById(id);
		System.out.println(actor.getFirstName());
	}
}
