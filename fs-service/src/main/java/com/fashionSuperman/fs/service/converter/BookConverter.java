package com.fashionSuperman.fs.service.converter;

import java.util.UUID;

import com.fashionSuperman.fs.core.jms.JMSConverter;
import com.fashionSuperman.fs.service.entity.Book;
import com.fashionSuperman.fs.service.entity.BookConvertered;

public class BookConverter implements JMSConverter<BookConvertered, Book>{

	@Override
	public BookConvertered convert(Book obj) {
		BookConvertered bookConvertered = new BookConvertered();
		bookConvertered.setAuthor(obj.getAuthor());
		bookConvertered.setId(UUID.randomUUID().toString());
		bookConvertered.setName(obj.getName());
		bookConvertered.setPrice(obj.getPrice());
		return bookConvertered;
	}


}
