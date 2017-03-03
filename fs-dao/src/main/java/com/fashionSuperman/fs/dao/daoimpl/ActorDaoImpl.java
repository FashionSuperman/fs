package com.fashionSuperman.fs.dao.daoimpl;

import org.springframework.stereotype.Repository;

import com.fashionSuperman.fs.core.dao.BaseDaoImpl;
import com.fashionSuperman.fs.dao.daoi.ActorDaoI;
import com.fashionSuperman.fs.dao.entity.Actor;
@Repository("actorDao")
public class ActorDaoImpl extends BaseDaoImpl<Actor> implements ActorDaoI {

}
