package com.fashionSuperman.fs.core.util;

import javax.servlet.http.Cookie;
/**
 * 
 * @description cookie操作工具类
 * @author FashionSuperman
 * @date 2017年2月23日 上午10:04:36
 * @version 1.0
 */
public class CookieUtil {
	/**
	 * cookie默认过期时间  1天
	 */
	private static int COOKIE_TIME_OUT = 24*60*60;
	/**
	 * 获取对应的cookie值
	 * @param cookieList
	 * @param key
	 * @return
	 */
	public static String getCookie(Cookie[] cookieList, String key){
		String value = null;
		if(cookieList != null && cookieList.length > 0){
			for(Cookie item : cookieList){
				if(item.getName().equals(key)){
					value = item.getValue();
					break;
				}
			}
		}
		return value;
	}
	
	/**
	 * 获取cookie值
	 * @param cookieList
	 * @param key
	 * @return
	 */
	public static Cookie getCookieObj(Cookie[] cookieList, String key){
		if(cookieList != null && cookieList.length > 0){
			for(Cookie item : cookieList){
				if(item.getName().equals(key)){
					return item;
				}
			}
		}
		return null;
	}
	
	/**
	 * 添加cookie
	 * @param key
	 * @param value
	 * @return
	 */
	public static Cookie addCookie(String key, String value){
		return addCookie(key, value, COOKIE_TIME_OUT);
	}
	
	/**
	 * 添加cookie
	 * @param key
	 * @param value
	 * @param timeOut
	 * @return
	 */
	public static Cookie addCookie(String key, String value, int timeOut){
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(timeOut);
		
		return cookie;
	}

}
