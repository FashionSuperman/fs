package com.fashionSuperman.fs.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fashionSuperman.fs.dubbo.facet.entity.Book;
import com.fashionSuperman.fs.dubbo.facet.inter.BookIterface;
@Service
public class BookImpl implements BookIterface{

	@Override
	public Book queryBook(String id) {
		Book book = new Book();
		book.setAuthor("FashionSuperman");
		book.setBookeName("论三角形中单打发");
		book.setBookId("111");
		book.setPrice("100");
		return book;
	}

	@Override
	public int addBook(Book book) {
		return 1;
	}

}
