package com.fashionSuperman.fs.core.log;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 日志操作实体
 * @description 
 * @author FashionSuperman
 * @date 2017年3月1日 下午2:29:34
 * @version 1.0
 */
public class LogQueueItem {
	/**
	 * 切点
	 */
	private ProceedingJoinPoint point;
	/**
	 * 调用异常
	 */
	private Throwable exception;
	/**
	 * 调用者ip
	 */
	private String ip;
	/**
	 * 调用者id
	 */
	private String userId;
	/**
	 * 调用开始时间
	 */
	private Date startTime;
	/**
	 * 调用结束时间
	 */
	private Date endTime;
	public ProceedingJoinPoint getPoint() {
		return point;
	}
	public void setPoint(ProceedingJoinPoint point) {
		this.point = point;
	}
	
	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	
}
