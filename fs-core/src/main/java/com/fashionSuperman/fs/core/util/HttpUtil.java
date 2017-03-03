package com.fashionSuperman.fs.core.util;

import javax.servlet.http.HttpServletRequest;
/**
 * http工具类
 * @description 
 * @author FashionSuperman
 * @date 2017年2月23日 下午2:06:43
 * @version 1.0
 */
public class HttpUtil {
	/**
	 * 获取调用者ip地址
	 * @param request
	 * @return
	 */
	public static String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
	
	
	/**
	 * 判断ip地址是否是内网ip
	 * 包括以下四类:
	 * A类  10.0.0.0-10.255.255.255  
	 * B类  172.16.0.0-172.31.255.255 
	 * C类  192.168.0.0-192.168.255.255  
	 * 127环回测试地址 
	 * 
	 * @param ipAddress
	 * @return
	 */
	public static boolean isInnerIP(String ipAddress){   
        boolean isInnerIp = false;   
        
        if(ipAddress.equals("localhost")){
        	return true;
        }
        
        long ipNum;
        try{
        	ipNum = getIpNum(ipAddress);
        }catch(Exception e){return false;}
           
        long aBegin = getIpNum("10.0.0.0");   
        long aEnd = getIpNum("10.255.255.255");   
        long bBegin = getIpNum("172.16.0.0");   
        long bEnd = getIpNum("172.31.255.255");   
        long cBegin = getIpNum("192.168.0.0");   
        long cEnd = getIpNum("192.168.255.255");   
        isInnerIp = isInner(ipNum,aBegin,aEnd) || isInner(ipNum,bBegin,bEnd) || isInner(ipNum,cBegin,cEnd) || ipAddress.equals("127.0.0.1") ;   
        return isInnerIp;              
    }  
    private static long getIpNum(String ipAddress) {   
        String [] ip = ipAddress.split("\\.");   
        long a = Integer.parseInt(ip[0]);   
        long b = Integer.parseInt(ip[1]);   
        long c = Integer.parseInt(ip[2]);   
        long d = Integer.parseInt(ip[3]);   
      
        long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;   
        return ipNum;   
    }  
    private static boolean isInner(long userIp,long begin,long end){   
         return (userIp>=begin) && (userIp<=end);   
    }

    
}
