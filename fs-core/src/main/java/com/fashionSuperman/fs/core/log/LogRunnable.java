package com.fashionSuperman.fs.core.log;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fashionSuperman.fs.core.annotation.LogAnnotaion;
import com.fashionSuperman.fs.core.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 日志处理线程runnable
 * @description 
 * @author FashionSuperman
 * @date 2017年3月1日 下午4:15:04
 * @version 1.0
 */
public class LogRunnable implements Runnable {
	private Logger logger = LoggerFactory.getLogger(LogRunnable.class);
	private BlockingQueue<LogQueueItem> blockingQueue;
	
	public LogRunnable(BlockingQueue<LogQueueItem> blockingQueue , Logger logger) {
		this.logger = logger;
		this.blockingQueue = blockingQueue;
	}
	
	public LogRunnable(BlockingQueue<LogQueueItem> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}
	
	@Override
	public void run() {
		while(true){
			if(this.blockingQueue.size() == 0){
				synchronized (LogQueue.class) {
					try {
						LogQueue.class.wait();
					} catch (InterruptedException e) {
						logger.error(e.getMessage(),e);
						e.printStackTrace();
					}
				}
			}
			
			LogQueueItem logQueueItem = blockingQueue.poll();
			if(logQueueItem != null){
				writeLog(logQueueItem);
			}
		}
	}
	
	/**
	 * 写日志
	 * @param logQueueItem
	 */
	private void writeLog(LogQueueItem logQueueItem){
		/**
		 * 切点对象
		 */
		ProceedingJoinPoint proceedingJoinPoint = logQueueItem.getPoint();
		
		/**
		 * 调用开始时间
		 */
		Date startTime = logQueueItem.getStartTime();
		/**
		 * 调用结束时间
		 */
		Date endTime = logQueueItem.getEndTime();
		/**
		 * 调用者
		 */
		String userId = logQueueItem.getUserId();
		/**
		 * 调用者ip
		 */
		String invokeIp = logQueueItem.getIp();
		
		Throwable throwable = logQueueItem.getException();
		
		//类名
		String className = proceedingJoinPoint.getThis().getClass().getName();
		if(className.indexOf("$$EnhancerBySpringCGLIB$$") > -1){
			try {
				className = className.substring(0,className.indexOf("$$"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//方法名
		String methodName = className + "." + proceedingJoinPoint.getSignature().getName();
		
		String methodRemark = ""; // 操作备注
		String operateType = ""; // 操作类型
		String funModule = ""; // 功能模块
		
		//获取方法注解信息
		Signature sig = proceedingJoinPoint.getSignature();
		Object target = proceedingJoinPoint.getTarget();
		
		if(!(sig instanceof MethodSignature)){
			logger.error("转换MethodSignature失败,停止日志写入");
			return;
		}
		
		MethodSignature msig = (MethodSignature)sig;
		
		
		Method method = null;
		
		try {
			method = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error("获取method失败,停止日志写入");
			return;
		}
		
		LogAnnotaion logAnnotaion = method.getAnnotation(LogAnnotaion.class);
		methodRemark = logAnnotaion.remark();
		funModule = logAnnotaion.module();
		operateType = logAnnotaion.operateType();
		
		//获取方法参数
		String methodParamString = "";
		Object[] methosParam = proceedingJoinPoint.getArgs();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			methodParamString = objectMapper.writeValueAsString(methosParam);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error("转换参数失败,停止日志写入");
		}
		
		
		logger.info("调用  " + className + "." + methodName + " 开始====");
		logger.info("startTime :" + DateUtil.convertDateToString(startTime));
		logger.info("调用者 : " + userId);
		logger.info("调用者ip : " + invokeIp);
		
		logger.info("methodRemark : " + methodRemark);
		logger.info("operateType : " + operateType);
		logger.info("funModule : " + funModule);
		
		
		
		logger.info("参数 : " + methodParamString);
		
		
		if(throwable != null){
			logger.info("异常信息 : " + throwable.getMessage());
		}
		logger.info("endTime : " + DateUtil.convertDateToString(endTime));
	}

}
