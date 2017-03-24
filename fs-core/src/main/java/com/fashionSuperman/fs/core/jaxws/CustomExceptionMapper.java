package com.fashionSuperman.fs.core.jaxws;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.mvc.entity.ResponseMessage;
import com.fashionSuperman.fs.core.util.StringUtil;

/**
 * dubbo rest 统一异常捕获
 * 
 * @description
 * @author FashionSuperman
 * @date 2017年3月9日 下午1:53:26
 * @version 1.0
 */
public class CustomExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		ResponseMessage<Object> responseMessage = new ResponseMessage<>();
		String message = "";
		String code = StatusCode.FAILURE;
		if (exception instanceof BizException) {
			BizException bizException = (BizException) exception;
			message = bizException.getMessage();
			int intCode = bizException.getCode();
			if(intCode != 0){
				code = String.valueOf(intCode);
			}
		} else {
			message = exception.getMessage();
			exception.printStackTrace();
			if(StringUtil.isNullOrEmpty(message) || message.length() > 100){
				message = "服务器异常";
			}
		}

		responseMessage.setMessage(message);
		responseMessage.setResponseData("");
		responseMessage.setCode(code);


		return Response.status(Response.Status.NOT_FOUND).entity(responseMessage)
				.type("application/json").build();
	}

}
