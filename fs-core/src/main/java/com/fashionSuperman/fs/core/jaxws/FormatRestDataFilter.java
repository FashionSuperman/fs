package com.fashionSuperman.fs.core.jaxws;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

import com.fashionSuperman.fs.core.mvc.entity.ResponseMessage;
/**
 * javax.ws rest返回数据统一格式封装
 * @description 
 * @author FashionSuperman
 * @date 2017年3月9日 上午11:19:16
 * @version 1.0
 */
public class FormatRestDataFilter implements ContainerResponseFilter{

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		
		MultivaluedMap<String, String> headers = requestContext.getHeaders();
		if(headers.containsKey("jump")){
			return;
		}
		
//		Set<String> allowedMethods = responseContext.getAllowedMethods();
//		for(String s : allowedMethods){
//			System.out.println("allowedMethod is :" + s);
//		}
//		
//		Date date = responseContext.getDate();
//		System.out.println("the message date is :"+DateUtil.convertDateToString(date));
		
		Object entity = responseContext.getEntity();
		if(entity instanceof ResponseMessage){//已经抛出业务异常
			return;
		}
//		System.out.println("the entity class is : " + entity.getClass());
		
//		Class<?> entityClass = responseContext.getEntityClass();
//		System.out.println("the entity class is " + entityClass.getName());
//		
//		OutputStream entityStream = responseContext.getEntityStream();
		
		ResponseMessage<Object> response = new ResponseMessage<>();
		response.setResponseData(entity == null ? "":entity);
		response.setMessage("");
		responseContext.setEntity(response);
		
	}

}
