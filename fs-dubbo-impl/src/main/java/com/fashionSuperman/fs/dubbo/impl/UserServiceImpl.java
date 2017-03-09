package com.fashionSuperman.fs.dubbo.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.dubbo.facet.entity.User;
import com.fashionSuperman.fs.dubbo.facet.inter.UserInterface;
@Path("/User")
@Service("userServiceImpl")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public class UserServiceImpl implements UserInterface {
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public User registerUser(User user) {
		System.out.println("注册成功 " + user.getName() + " " + user.getAge());
		return user;
	}
	
	@GET
	@Path("{id : \\d+}")
	public User getUser(@PathParam("id") Long id){
		User user = new User();
		user.setName("石乐志");
		user.setAge("30");
		user.setGender("男");
		return user;
	}
	
	@GET
	@Path("/exeception1")
	public User testExeception1() throws Exception{
		throw new Exception("");
	}
	
	@GET
	@Path("/exeception2")
	public User testExeception2() throws BizException{
		throw new BizException("bizExeception");
	}

}
