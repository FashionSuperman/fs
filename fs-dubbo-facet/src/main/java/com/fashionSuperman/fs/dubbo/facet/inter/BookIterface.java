package com.fashionSuperman.fs.dubbo.facet.inter;

import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.dubbo.facet.entity.Book;

public interface BookIterface {
	Book queryBook(String id) throws BizException;
	
	int addBook(Book book) throws BizException;
}
