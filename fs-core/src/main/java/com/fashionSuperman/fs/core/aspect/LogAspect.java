package com.fashionSuperman.fs.core.aspect;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.fashionSuperman.fs.core.log.LogQueue;
import com.fashionSuperman.fs.core.log.LogQueueItem;

/**
 * 日志切面处理
 * 
 * @description
 * @author FashionSuperman
 * @date 2017年3月1日 下午2:09:10
 * @version 1.0
 */
@Aspect
public class LogAspect {
	@Autowired(required = false)
	private HttpServletRequest httpServletRequest;
	@Autowired
	private LogQueue logQueue;

	/**
	 * 日志方法切点
	 */
	@Pointcut("@annotation(com.fashionSuperman.fs.core.annotation.LogAnnotaion)")
	public void methodLogPoitcut() {
	}

	/**
	 * 日志切点环绕通知
	 * 
	 * @param joinPoint
	 * @throws Throwable 
	 */
	@Around(value = "methodLogPoitcut()")
	public Object AroundMethodLogPoitcutAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object object;
		String userId = "";
		if (httpServletRequest != null) {
			userId = (String) httpServletRequest.getSession().getAttribute("userId");
		}

		// 获取调用方ip(兼容负载均衡)
		String ip = httpServletRequest.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = httpServletRequest.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = httpServletRequest.getRemoteAddr();
		}
		ip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
		
		LogQueueItem logQueueItem = new LogQueueItem();
		logQueueItem.setStartTime(new Date());
		logQueueItem.setUserId(userId);
		logQueueItem.setIp(ip);
		logQueueItem.setPoint(joinPoint);
		
		try {
			object = joinPoint.proceed();
			logQueueItem.setEndTime(new Date());
			//提交日志进程处理
			logQueue.addLogItem(logQueueItem);
		} catch (Throwable e) {
			logQueueItem.setException(e);
			logQueueItem.setEndTime(new Date());
			//提交日志进程处理
			logQueue.addLogItem(logQueueItem);
			e.printStackTrace();
			throw e;
		}
		return object;
	}
}
