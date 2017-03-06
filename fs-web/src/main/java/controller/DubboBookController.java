package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fashionSuperman.fs.dubbo.facet.entity.Book;
import com.fashionSuperman.fs.dubbo.facet.inter.BookIterface;

@Controller("/DubboBook")
public class DubboBookController {
	@Autowired
	BookIterface bookI;
	@RequestMapping("/queryBook")
	@ResponseBody
	public Book queryBook(String bookId){
		return bookI.queryBook(bookId);
	};
}