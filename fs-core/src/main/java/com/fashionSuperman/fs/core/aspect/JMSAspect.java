package com.fashionSuperman.fs.core.aspect;

import java.lang.reflect.Method;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.core.JmsTemplate;

import com.fashionSuperman.fs.core.annotation.JMSAnnotation;
import com.fashionSuperman.fs.core.constant.JMSSendType;
import com.fashionSuperman.fs.core.jms.JMSConverter;
/**
 * jms切面
 * @description 
 * @author FashionSuperman
 * @date 2017年3月9日 下午3:26:04
 * @version 1.0
 */
import com.fashionSuperman.fs.core.jms.SendMessage;
import com.fashionSuperman.fs.core.jms.SendMessageBody;
import com.fashionSuperman.fs.core.jms.SendMessageHeader;
import com.fashionSuperman.fs.core.jms.sender.DefaultMessageSender;
@Aspect
public class JMSAspect extends DefaultMessageSender implements ApplicationContextAware{
	private Logger logger = LoggerFactory.getLogger(JMSAspect.class);
//	@Autowired(required = false)
//	private HttpServletRequest httpServletRequest;
	
	private JmsTemplate jmsTemplate;
	
	ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.applicationContext = context;
	}
	
	/**
	 * 定义jms切点
	 */
	@Pointcut("@annotation(com.fashionSuperman.fs.core.annotation.JMSAnnotation)")
	public void jmsPointcut(){}
	
	/**
	 * jms切点环绕通知
	 * @return
	 * @throws Throwable 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Around(value = "jmsPointcut()")
	public Object AroundJmsPointcutAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
		
		Object result = null;
		
		Class targetClass = joinPoint.getTarget().getClass();
		Method method = null;
		
		JMSAnnotation jmsAnnotation = null;
		
		Signature sig = joinPoint.getSignature();
		MethodSignature msig = null;
		if(sig instanceof MethodSignature){
			msig = (MethodSignature) sig;
			try {
				method = targetClass.getMethod(msig.getName(), msig.getParameterTypes());
				jmsAnnotation = method.getAnnotation(JMSAnnotation.class);
			} catch (NoSuchMethodException|SecurityException e) {
				logger.error("获取到method失败,停止消息发送");
				e.printStackTrace();
				return joinPoint.proceed();
			}
		}else{//注解用于类上
			logger.error("切点不是方法,无法获取参数");
			return joinPoint.proceed();
		}
		
		if(jmsAnnotation == null){
			logger.error("未获取到jmsAnnotation,停止消息发送");
			return joinPoint.proceed();
		}
		
		//获取注解上配置的消息发送模板
		JMSSendType jmsSendType = jmsAnnotation.jmsSendType();
		String jmsTemplate = jmsAnnotation.jmsTemplate();
		
		Object jmsTemplateBean = applicationContext.getBean(jmsTemplate);
		if(jmsTemplateBean == null){
			logger.error("没有对应的 "+ jmsTemplate +" jms模板,无法发送消息");
			return joinPoint.proceed();
		}
		this.jmsTemplate = (JmsTemplate) jmsTemplateBean;
		
		//判断发送类型,根据类型发送消息
		switch(jmsSendType){
			case Response:
				result = sendJms_response(joinPoint, method, jmsAnnotation);
				break;
			case RequestAfter:
				result = sendJms_requestAfter(joinPoint, method, jmsAnnotation);
				break;
			case RequestBefor:
				result = sendJms_requestBefor(joinPoint, method, jmsAnnotation);
			default:
				break;
		}
		
		return result;
	}

	/**
	 * 发送运行结果
	 * @param joinPoint
	 * @param method
	 * @param jmsAnnotation
	 * @return
	 * @throws Throwable
	 */
	private Object sendJms_response(ProceedingJoinPoint joinPoint,Method method , JMSAnnotation jmsAnnotation) throws Throwable{
		Object response = joinPoint.proceed();
		Class<?> retureType = method.getReturnType();
		if(!"void".equals(retureType.getName()) && !"boolean".equals(retureType.getName())){
			String sender = getSender(joinPoint, method);
			String projectId = jmsAnnotation.projectId();
			SendMessage sendMessage = buildSendMessage(response, sender, projectId);
			super.setJmsTemplate(this.jmsTemplate);
			super.sendMessage(sendMessage , getConverter(jmsAnnotation));
		}
		return response;
	}
	
	/**
	 * 发送运行之后的参数
	 * @param joinPoint
	 * @param method
	 * @param jmsAnnotation
	 * @return
	 * @throws Throwable
	 */
	private Object sendJms_requestAfter(ProceedingJoinPoint joinPoint,Method method , JMSAnnotation jmsAnnotation) throws Throwable{
		Object response = joinPoint.proceed();
		Object param = this.getArgs(joinPoint);
		if(param != null){
			String sender = getSender(joinPoint, method);
			String projectId = jmsAnnotation.projectId();
			SendMessage sendMessage = buildSendMessage(param, sender, projectId);
			super.setJmsTemplate(this.jmsTemplate);
			super.sendMessage(sendMessage , getConverter(jmsAnnotation));
		}
		return response;
	}
	
	/**
	 * 在方法执行之前发送参数
	 * @param joinPoint
	 * @param method
	 * @param jmsAnnotation
	 * @return
	 * @throws Throwable
	 */
	private Object sendJms_requestBefor(ProceedingJoinPoint joinPoint,Method method , JMSAnnotation jmsAnnotation) throws Throwable{
		Object param = this.getArgs(joinPoint);
		if(param != null){
			String sender = getSender(joinPoint, method);
			String projectId = jmsAnnotation.projectId();
			SendMessage sendMessage = buildSendMessage(param, sender, projectId);
			super.setJmsTemplate(this.jmsTemplate);
			super.sendMessage(sendMessage , getConverter(jmsAnnotation));
		}
		return joinPoint.proceed();
	}
	
	/**
	 * 获取切点方法的参数,如果没有参数返回null
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	private Object getArgs(ProceedingJoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		if(args.length == 0){
			logger.error("没有参数");
			return null;
		}
		Object arg0 = args[0];
		
		return arg0;
	}
	
	/**
	 * 获取消息转换器实例
	 * @param jmsAnnotation
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private JMSConverter getConverter(JMSAnnotation jmsAnnotation){
		Class<? extends JMSConverter> jmsConverterClass = jmsAnnotation.jmsConverter();
		JMSConverter jmsConverter = null;
		if(jmsConverterClass != null){
			try {
				jmsConverter = jmsConverterClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				logger.error("实例化消息转换器失败");
				return jmsConverter;
			}
		}
		return jmsConverter;
	}
	
	/**
	 * 获取发送者名字  类名+方法名
	 * @param joinPoint
	 * @param method
	 * @return
	 */
	private String getSender(ProceedingJoinPoint joinPoint,Method method){
		String className = joinPoint.getThis().getClass().getName();
		if(className.indexOf("$$EnhancerBySpringCGLIB$$") > -1){
			try {
				className = className.substring(0,className.indexOf("$$"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String methodName = method.getName();
		return className + "." + methodName;
	}
	
	/**
	 * 构建发送消息
	 * @return
	 */
	private SendMessage buildSendMessage(Object response , String sender , String projectId){
		SendMessage sendMessage = new SendMessage();
		
		SendMessageHeader sendMessageHeader = new SendMessageHeader();
		sendMessageHeader.setMessageId(UUID.randomUUID().toString());
		sendMessageHeader.setMessageType(response.getClass().getName());
		sendMessageHeader.setProjectId(projectId);
		sendMessageHeader.setSender(sender);
		sendMessageHeader.setTimeStamp(System.currentTimeMillis());
		sendMessage.setSendMessageHeader(sendMessageHeader);
		
		SendMessageBody sendMessageBody = new SendMessageBody();
		sendMessageBody.setMessageBody(response);
		
		sendMessage.setSendMessageBody(sendMessageBody);
		
		return sendMessage;
	}

}
