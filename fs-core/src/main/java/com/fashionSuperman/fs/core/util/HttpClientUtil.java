package com.fashionSuperman.fs.core.util;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.dao.BaseDaoImpl;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.mvc.entity.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * HttpClient工具封装
 * @description 
 * @author FashionSuperman
 * @date 2017年2月23日 下午2:03:53
 * @version 1.0
 */
public class HttpClientUtil {

	private static int TIME_OUT = 20000;

	protected static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	/**
	 * 统一发送post请求
	 * @param url
	 * @param parameters
	 * @return
	 */
	public static String doPost(String url, String parameters) {
		String responseData = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIME_OUT)
				.setConnectionRequestTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build();
		httppost.setConfig(requestConfig);
		httppost.addHeader("Content-type", "application/json; charset=utf-8");
		httppost.setHeader("Accept", "application/json");
		if (StringUtil.isNotEmpty(parameters)) {
			httppost.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));
		}
		try {

			CloseableHttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String responseMessageData = EntityUtils.toString(entity, "UTF-8");

				ObjectMapper objectMapper = new ObjectMapper();
				ResponseMessage<?> responseMessage = objectMapper.readValue(responseMessageData, ResponseMessage.class);
				if (responseMessage.getCode().equals(StatusCode.SUCCESS)) {
					responseData = objectMapper.writeValueAsString(responseMessage.getResponseData());
				} else {
					throw new BizException(StatusCode.FAILURE_CALL_AGENT, "调用接口失败！");
				}
			}

		} catch (Exception e) {
			throw new BizException(StatusCode.FAILURE_CALL_AGENT, "调用接口失败！");
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				throw new BizException(StatusCode.FAILURE_CALL_AGENT, "关闭接口失败！");
			}
		}

		return responseData;
	}
	
	
	
	/**
	 * xml格式调用接口
	 * @param url
	 * @param parameters
	 * @return
	 */
	public static String doPostXml(String url, String parameters) {
		String responseData = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIME_OUT)
				.setConnectionRequestTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build();
		httppost.setConfig(requestConfig);
		httppost.addHeader("Content-type", "application/xml");
		httppost.setHeader("Accept", "application/xml");
		if (StringUtil.isNotEmpty(parameters)) {
			httppost.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));
		}
		try {

			CloseableHttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseData = EntityUtils.toString(entity, "UTF-8");
			}

		} catch (Exception e) {
			throw new BizException(StatusCode.FAILURE_CALL_AGENT, "调用接口失败！");
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				throw new BizException(StatusCode.FAILURE_CALL_AGENT, "关闭接口失败！");
			}
		}

		return responseData;
	}
	/**
	 * 统一发送get请求
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
	
		logger.debug("请求地址："+url);
		String responseData = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIME_OUT)
				.setConnectionRequestTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build();
		get.setConfig(requestConfig);
		try {
			HttpResponse res = httpclient.execute(get);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				if (entity != null) {
					responseData = EntityUtils.toString(entity, "UTF-8");
				}
			}
		} catch (Exception e) {
			throw new BizException(StatusCode.FAILURE_CALL_AGENT, "调用接口失败！");
		} finally {
			try {
				httpclient.close();
			} catch (Exception e) {
				throw new BizException(StatusCode.FAILURE_CALL_AGENT, "关闭接口失败！");
			}
		}
		return responseData;
	}
}
