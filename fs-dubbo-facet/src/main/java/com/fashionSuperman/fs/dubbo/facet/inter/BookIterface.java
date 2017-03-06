package com.fashionSuperman.fs.dubbo.facet.inter;

import com.fashionSuperman.fs.dubbo.facet.entity.Book;

public interface BookIterface {
	public Book queryBook(String id);
	
	public int addBook(Book book);
}
