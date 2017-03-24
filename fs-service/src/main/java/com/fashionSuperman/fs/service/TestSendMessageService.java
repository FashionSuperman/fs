package com.fashionSuperman.fs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.annotation.JMSAnnotation;
import com.fashionSuperman.fs.core.constant.JMSSendType;
import com.fashionSuperman.fs.service.converter.BookConverter;
import com.fashionSuperman.fs.service.entity.Book;
import com.fashionSuperman.fs.service.entity.User;
@Service
public class TestSendMessageService {
	@JMSAnnotation(jmsSendType = JMSSendType.RequestBefor
			, projectId = "test"
			, jmsTemplate = "myJmsTemplate_Test_Queue"
			, jmsConverter = BookConverter.class)
	
	public Book sendBefore(Book book) {
		System.out.println("sendBefore 方法执行");
		return null;
	}
	
	@JMSAnnotation(jmsSendType = JMSSendType.RequestAfter
			, projectId = "test"
			, jmsTemplate = "myJmsTemplate_Test_Queue"
			, jmsConverter = BookConverter.class)
	
	public Book sendAfter(Book book) {
		System.out.println("sendAfter 方法执行");
		book.setAuthor("卡兹克");
		book.setName("无敌螳螂");
		return book;
	}

	@JMSAnnotation(jmsSendType = JMSSendType.Response
			, projectId = "test"
			, jmsTemplate = "myJmsTemplate_Test_Queue"
			, jmsConverter = BookConverter.class)
	public Book sendResponse() {
		Book book = new Book();
		book.setAuthor("阿狸");
		book.setName("炸气法师阿狸");
		book.setPrice(100);
		
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setAge(10);
		user.setName("阿狸");
		users.add(user);
		
		book.setUsers(users);
		return book;
	}
}
