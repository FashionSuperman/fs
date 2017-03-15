package com.fashionSuperman.fs.core.jms;
/**
 * 默认消息转化器-消息原样返回
 * @description 
 * @author FashionSuperman
 * @date 2017年3月14日 下午3:58:12
 * @version 1.0
 */
public class DefaultJMSConverter implements JMSConverter{

	@Override
	public Object convert(Object obj) {
		return obj;
	}

}
