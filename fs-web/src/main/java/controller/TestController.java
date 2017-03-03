package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.service.TestLogService;

import bean.Addr;
import bean.TestBean;

@Controller
@RequestMapping("/testController")
public class TestController {
	@Autowired
	TestLogService testLogService;
	
	@RequestMapping(method = RequestMethod.POST , value = "/test/{id}")
	@ResponseBody
	public List<String> testList(@RequestBody String body , @PathVariable String id){
		
		System.out.println("pathvariable is " + id);
		
		List<String> result =  new ArrayList<>();
		result.add("test1");
		result.add("你好");
		result.add("test3");
		result.add("test4");
		
		return result;
	}
	
	@RequestMapping(method = {RequestMethod.POST , RequestMethod.GET} , value = "/testThrow")
	@ResponseBody
	public String testThrow(@RequestBody String body){
		System.out.println("body is "+body);
		throw new BizException("测试异常抛出");
	}
	
	@RequestMapping(method = {RequestMethod.POST , RequestMethod.GET} , value = "/testBean")
	@ResponseBody
	public TestBean testBean(){
		TestBean testBean = new TestBean();
		testBean.setName("动感超人");
		Addr addr = new Addr();
		addr.setCity("青岛");
		addr.setProvince("山东");
		
		List<Addr> list = new ArrayList<>();
		list.add(addr);
		list.add(addr);
		
		
		
		testBean.setAddr(addr);
		testBean.setAddrs(list);
		return testBean;
	}
	
	
	@RequestMapping(method = {RequestMethod.POST , RequestMethod.GET} , value = "/testLogAspect")
	@ResponseBody
	public String testLogAspect(){
		String s = testLogService.getString("FashionSuperman");
		return s;
	}
}
