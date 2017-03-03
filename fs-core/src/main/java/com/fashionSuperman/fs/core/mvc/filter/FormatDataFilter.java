package com.fashionSuperman.fs.core.mvc.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.mvc.wrapper.HttpRequestWrapper;
import com.fashionSuperman.fs.core.mvc.wrapper.HttpResponseWrapper;
import com.fashionSuperman.fs.core.util.PropertyUtil;
import com.fashionSuperman.fs.core.util.StringUtil;

public class FormatDataFilter implements Filter {

	private PropertyUtil propertyUtil = null;
	private Properties publicSystemProperties = null;
	private String baseServiceIp = "http://localhost";
	private Logger logger = LoggerFactory.getLogger(FormatDataFilter.class);

	public FormatDataFilter() {
		super();
		propertyUtil = PropertyUtil.getInstance();
		publicSystemProperties = propertyUtil.getProperties("public_system.properties");
		baseServiceIp = publicSystemProperties.getProperty("baseServiceIp", "http://localhost");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String accept = httpServletRequest.getHeader("accept");
		String contentType = httpServletRequest.getContentType();
		// 判断是否是api请求
		if ((StringUtil.isNotEmpty(accept) && accept.toLowerCase().contains("application/json"))
				|| (StringUtil.isNotEmpty(contentType) && contentType.toLowerCase().contains("application/json"))
				|| (StringUtil.isNotEmpty(contentType) && contentType.toLowerCase().contains("form-data"))
				|| (StringUtil.isNotEmpty(contentType)
						&& contentType.toLowerCase().contains("x-www-form-urlencoded"))) {
			 this.apiRequest(httpServletRequest, httpServletResponse, chain);
		} else if (StringUtil.isNotEmpty(accept) && accept.toLowerCase().contains("text/html")) {// 判断是否是html请求
			this.htmlRequest(httpServletRequest, httpServletResponse, chain);
		} else {// 资源请求(图片 js文件等)
			this.resourceRequest(httpServletRequest, httpServletResponse, chain);
		}

	}

	private void apiRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain chain) throws IOException {
		httpServletRequest.setCharacterEncoding("UTF-8");
		httpServletResponse.setCharacterEncoding("UTF-8");
		String code = StatusCode.SUCCESS;
		String message = "";
		String responseData = "{}";

		String currentLog = "";// 当前输出日志
		String totalLog = "";// 当前请求所有日志

		String currentUrl = httpServletRequest.getServletPath();

		long beginTime = System.currentTimeMillis();
		currentLog = "接口" + currentUrl + "调用开始*************************************************************";
		totalLog = "接口" + currentUrl + "所有日志输出：" + " ";
		;
		logger.info(currentLog);

		// 打印请求报文
		String contentType = httpServletRequest.getContentType();
		HttpRequestWrapper requestWrapper = null;
		if (contentType != null && !contentType.contains("form-data")
				&& !contentType.contains("x-www-form-urlencoded")) {
			requestWrapper = new HttpRequestWrapper(httpServletRequest);
			currentLog = "接口" + currentUrl + " RequestMessage:" + requestWrapper.getRequestData();
			totalLog = totalLog + " " + currentLog;
			logger.info(currentLog);
		}

		// 逻辑处理
		HttpResponseWrapper responseWrapper = new HttpResponseWrapper(httpServletResponse);

		try {

			if (requestWrapper != null) {
				chain.doFilter(requestWrapper, responseWrapper);
			} else {
				chain.doFilter(httpServletRequest, responseWrapper);
			}

		} catch (Exception e) {// 统一异常捕获

			if (e instanceof BizException) {
				BizException bizException = (BizException) e;
				code = bizException.getCode() + "";
				message = bizException.getMessage();
				// 错误时返回业务数据
				if (bizException.getData() != null) {
					responseData = StringUtil.toJson(bizException.getData());
				}
				logger.error(e.getMessage(), e);
			} else if (e.getCause() instanceof BizException) {
				BizException bizException = (BizException) e.getCause();
				code = bizException.getCode() + "";
				message = bizException.getMessage();
				// 错误时返回业务数据
				if (bizException.getData() != null) {
					responseData = StringUtil.toJson(bizException.getData());
				}
				logger.error(e.getMessage(), e);
			} else {
				code = StatusCode.FAILURE;
				message = "系统内部异常！";

				// logger.error(e);

				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);

				try {
					e.printStackTrace(pw);
					currentLog = "接口" + currentUrl + " 系统内部异常:" + sw.toString();
					totalLog = totalLog + " " + currentLog;
					logger.error(currentLog);
				} finally {
					pw.close();
				}
			}
		}

//		if(message != null){
//			message = new String(message.getBytes(), "UTF-8");
//		}
		
		
		//统一格式  写回数据
		if (responseWrapper != null) {
			byte[] data = responseWrapper.getResponseData();
			if (data != null && data.length > 0) {
				responseData = new String(data, "UTF-8");
			}
		}
		String responseMessage = new String(
				"{\"code\":\"" + code + "\", \"message\":\"" + message + "\", \"responseData\":" + responseData + "}");

		currentLog = "接口" + currentUrl + " ResposneMessage:" + responseMessage;
		totalLog = totalLog + " " + currentLog;
		logger.info(currentLog);

		if (contentType != null && contentType.contains("form-data")) {
			httpServletResponse.setContentType("text/html");
		}

		ServletOutputStream out = httpServletResponse.getOutputStream();
		out.write(responseMessage.getBytes("UTF-8"));
		out.flush();
		
		
		
		currentLog = "接口" + currentUrl + "调用结束**********************************************************************";
		totalLog = totalLog + " " + currentLog;
		logger.info(totalLog);

	}
	
	private void htmlRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain){
		String code = StatusCode.SUCCESS;
		String message = "";
		
		try {
			chain.doFilter(httpServletRequest, httpServletResponse);
		} catch (Exception e) {
			if (e instanceof BizException) {
				BizException bizException = (BizException) e;
				code = bizException.getCode() + "";
				message = bizException.getMessage();
				logger.error(e.getMessage(), e);
			} else if (e.getCause() instanceof BizException) {
				BizException bizException = (BizException) e.getCause();
				code = bizException.getCode() + "";
				message = bizException.getMessage();
				logger.error(e.getMessage(), e);
			}/*else if (e instanceof AuthException) {
				authException = (AuthException) e;
				code = authException.getCode();
				message = authException.getMessage();
				logger.error(authException);
			} else if (e.getCause() instanceof AuthException) {
				authException = (AuthException) e.getCause();
				code = authException.getCode();
				message = authException.getMessage();
				logger.error(authException);
			}*/
			else {
				code = StatusCode.FAILURE;
				message = "系统内部异常！";
				// logger.error(e);

				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);

				try {
					e.printStackTrace(pw);
					logger.error(sw.toString());
				} finally {
					pw.close();
				}
			}
		}
		
		
		//统一跳转错误页面或者登录页面
//		if (!StatusCode.SUCCESS.equals(code)) {
//
//			if (code.contains(StatusCode.FAILURE_AUTH) && authException != null) {
//				response.sendRedirect(BASE_API_DOMAIN + "User/loginFrame?redirect=" + authException.getRedirectUrl());
//			} else {
//				response.sendRedirect(BASE_API_DOMAIN + "User/Error?code=" + code + "&message=" + message);
//			}
//
//		}
		
		
	}
	
	
	private void resourceRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(httpServletRequest, httpServletResponse);
	}

}
