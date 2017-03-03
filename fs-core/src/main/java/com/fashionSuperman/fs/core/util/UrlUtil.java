package com.fashionSuperman.fs.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * url参数处理工具类
 * @description 
 * @author FashionSuperman
 * @date 2017年2月24日 上午8:58:38
 * @version 1.0
 */
public class UrlUtil {

	/**
	   * 将 String 转为 map
	   * 
	   * @param param
	   *            aa=11&bb=22&cc=33
	   * @return
	   */
	  public static Map<String, Object> getUrlParams(String param) {
	      Map<String, Object> map = new HashMap<String, Object>();
	      if ("".equals(param) || null == param) {
	          return map;
	      }
	      String[] params = param.split("&");
	      for (int i = 0; i < params.length; i++) {
	          String[] p = params[i].split("=");
	          if (p.length == 2) {
	              map.put(p[0], p[1]);
	          }
	      }
	      return map;
	  }
	 
	  /**
	   * 将map 转为 string
	   * 
	   * @param map
	   * @return
	   */
	  public static String getUrlParamsByMap(Map<String, Object> map,
	          boolean isSort) {
	      if (map == null) {
	          return "";
	      }
	      StringBuffer sb = new StringBuffer();
	      List<String> keys = new ArrayList<String>(map.keySet());
	      if (isSort) {
	          Collections.sort(keys);
	      }
	      for (int i = 0; i < keys.size(); i++) {
	          String key = keys.get(i);
	          String value = map.get(key).toString();
	          sb.append(key + "=" + value);
	          sb.append("&");
	      }
	      String s = sb.toString();
	      if (s.endsWith("&")) {
	          s = s.substring(0, s.lastIndexOf("&"));
	      }
	      
	      return s;
	  }
	  
	  /**
	   * url拼接参数
	   * @param url
	   * @param map
	   * @return
	   */
	  public static String unionUrlParam(String url, Map<String, Object> map){
		  if(map != null){
			  for (Map.Entry<String, Object> entry : map.entrySet()) {
				  if(url.contains("?")){
					  url = url + "&" + entry.getKey() + "=" + entry.getValue();
				  }
				  else{
					  url = url + "?" + entry.getKey() + "=" + entry.getValue();
				  }
			  }
		  }
		  return url;
	  }
	  /**
	   * 获取url中的参数 string格式
	   * @param url
	   * @return
	   */
	  public static String getUrlParamString(String url){
		  String param = url;
		  int location = url.indexOf("?");
		  if(location >=0){
			  param = url.substring(location + 1);
		  }
		  return param;
	  }
}
