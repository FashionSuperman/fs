package com.fashionSuperman.fs.core.mvc.entity;

import com.fashionSuperman.fs.core.constant.StatusCode;

/**
 * mvc返回数据统一格式定义
 * @description 
 * @author FashionSuperman
 * @date 2017年2月23日 下午1:48:58
 * @version 1.0
 * @param <T>
 */
public class ResponseMessage<T> {

	/**
	 * 编码
	 * 处理成功 200
	 * 处理失败 500
	 * 
	 * 登陆失败 40101
	 * token失效 40102
	 * 验证权限失败 40103
	 * 
	 * 空异常 50001
	 * 服务器端逻辑异常 50002
	 * 调用接口异常 50003
	 */
	private String code = StatusCode.SUCCESS;
	/**
	 * 提示信息
	 */
	private String message;
	/**
	 * 返回业务数据
	 */
	private T responseData;

	public ResponseMessage(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 返回业务数据
	 */
	public T getResponseData(){
		return responseData;
	}

	/**
	 * 返回业务数据
	 * 
	 * @param newVal
	 */
	public void setResponseData(T newVal){
		responseData = newVal;
	}

	/**
	 * 编码
	 */
	public String getCode(){
		return code;
	}

	/**
	 * 编码
	 * 
	 * @param newVal
	 */
	public void setCode(String newVal){
		code = newVal;
	}

	/**
	 * 提示信息
	 */
	public String getMessage(){
		return message;
	}

	/**
	 * 提示信息
	 * 
	 * @param newVal
	 */
	public void setMessage(String newVal){
		message = newVal;
	}

}