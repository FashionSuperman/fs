package com.fashionSuperman.fs.dubbo.facet.entity;

import java.io.Serializable;

public class Book implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String bookId;
	private String bookeName;
	private String author;
	private String price;
	
	
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getBookeName() {
		return bookeName;
	}
	public void setBookeName(String bookeName) {
		this.bookeName = bookeName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
