package com.fashionSuperman.fs.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.fashionSuperman.fs.core.constant.JMSSendType;
import com.fashionSuperman.fs.core.jms.DefaultJMSConverter;
import com.fashionSuperman.fs.core.jms.JMSConverter;
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JMSAnnotation {
	/**
	 * jms模板
	 * @return
	 */
	String jmsTemplate() default "";
	/**
	 * jms 发送类型(默认将返回值发送到队列)
	 * @return
	 */
	JMSSendType jmsSendType() default JMSSendType.Response;
	/**
	 * jms 消息转换器类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Class<? extends JMSConverter> jmsConverter() default DefaultJMSConverter.class;
	/**
	 * 项目id
	 * @return
	 */
	String projectId() default "";
}
