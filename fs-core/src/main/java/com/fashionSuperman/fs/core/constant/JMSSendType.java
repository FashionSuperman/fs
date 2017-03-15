package com.fashionSuperman.fs.core.constant;
/**
 * jms队列发送类型
 * @description 
 * @author FashionSuperman
 * @date 2017年3月9日 下午3:15:29
 * @version 1.0
 */
public enum JMSSendType {
	Response,//发送方法的返回结果
	RequestAfter,//发送方法的参数(在方法允许之前发送)
	RequestBefor;//发送方法的参数(在方法允许之后发送)
}
