package com.fashionSuperman.fs.core.jms;
/**
 * jms消息转换接口
 * @description 
 * @author FashionSuperman
 * @date 2017年3月9日 下午4:38:44
 * @version 1.0
 */
public interface JMSConverter<S , T> {
	/**
	 * 转换消息
	 * @param obj
	 * @return
	 */
	public S convert(T obj);
}
