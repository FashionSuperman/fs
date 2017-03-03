package com.fashionSuperman.fs.core.mvc.wrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;


/**
 * 请求 request 包装类,统一编码 
 * @description 
 * @author FashionSuperman
 * @date 2017年2月24日 下午2:43:12
 * @version 1.0
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper { 
	
	/**
	 * 业务数据
	 */
    private String requestData;
  
    /**
     * 初始化请求数据到requestData
     * @param request
     */
    public HttpRequestWrapper(HttpServletRequest request) {  
        super(request);  
        
        StringBuilder stringBuilder = new StringBuilder();
        try{
        	BufferedReader reader = new BufferedReader(new InputStreamReader(request   
   		         .getInputStream(), "UTF-8"));   
	   		String line = "";
	   	    while ((line = reader.readLine()) != null){
	   	    	stringBuilder.append(line);
	   	    }
	   	    this.requestData = stringBuilder.toString();
        }catch(Exception e){
        	throw new BizException(StatusCode.FAILURE, "请求数据读取失败！");
        }
		
    }  
  
    /**
     * 获取请求数据
     */
    @Override  
    public ServletInputStream getInputStream() {  
          byte[] buffer = null;  
        try {  
            buffer = this.requestData.toString().getBytes("UTF-8");  
        } catch (UnsupportedEncodingException e) {  
        	throw new BizException(StatusCode.FAILURE, "请求数据转换失败！");
        }  
        final ByteArrayInputStream bais = new ByteArrayInputStream(buffer);  
  
        ServletInputStream newStream = new ServletInputStream() {  
            @Override  
            public int read() throws IOException {  
                return bais.read();  
            }  
        };  
        return newStream;  
    }

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}  
    
    
}  
