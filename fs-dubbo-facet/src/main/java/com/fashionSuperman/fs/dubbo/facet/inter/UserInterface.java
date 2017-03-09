package com.fashionSuperman.fs.dubbo.facet.inter;


import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.dubbo.facet.entity.User;

public interface UserInterface {
	public User registerUser(User user);
	User getUser(Long id);
	public User testExeception1() throws Exception;
	public User testExeception2() throws BizException;
}
