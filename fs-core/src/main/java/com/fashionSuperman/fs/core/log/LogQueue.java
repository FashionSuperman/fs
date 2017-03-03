package com.fashionSuperman.fs.core.log;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fashionSuperman.fs.core.util.PropertyUtil;
/**
 * 日志队列
 * @description 
 * @author FashionSuperman
 * @date 2017年3月1日 下午4:14:50
 * @version 1.0
 */
@Component
public class LogQueue {
	private BlockingQueue<LogQueueItem> logQueueItems = new LinkedBlockingQueue<>();
	
	/**
	 * 启动线程池
	 */
	@PostConstruct
	public void initLogQueue(){
		ExecutorService executorService = Executors.newCachedThreadPool();
		Properties properties = PropertyUtil.getInstance().getProperties("/public_system.properties");
		String size = properties.getProperty("threadPoolSize" , "5");
		int intSize = Integer.parseInt(size);
		Runnable logRun = new LogRunnable(this.logQueueItems);
		for(int i = 0 ; i < intSize ; i++){
			Thread thread = new Thread(logRun , i+"");
			thread.setDaemon(true);
			executorService.execute(thread);
		}
		
		executorService.shutdown();
		
	}
	/**
	 * 添加日志条目
	 * @param logQueueItem
	 */
	public void addLogItem(LogQueueItem logQueueItem){
		this.logQueueItems.add(logQueueItem);
		synchronized (LogQueue.class) {
			LogQueue.class.notify();
		}
	}
	
}
