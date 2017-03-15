package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fashionSuperman.fs.service.TestSendMessageService;
import com.fashionSuperman.fs.service.entity.Book;

@Controller("/testJms")
public class TestJmsController {
	@Autowired
	private TestSendMessageService sendMessageService;
	
	@RequestMapping(value = "/before")
	@ResponseBody
	public void before(){
		Book book = new Book();
		book.setAuthor("大司马");
		book.setName("论正方形打野 ");
		book.setPrice(100);
		sendMessageService.sendBefore(book);
	}
	
	@RequestMapping(value = "/after")
	@ResponseBody
	public void after(){
		Book book = new Book();
		book.setAuthor("大司马");
		book.setName("论正方形打野 ");
		book.setPrice(100);
		sendMessageService.sendAfter(book);
	}
	
	@RequestMapping(value = "/response")
	@ResponseBody
	public Book response(){
		return sendMessageService.sendResponse();
	}
	
}
