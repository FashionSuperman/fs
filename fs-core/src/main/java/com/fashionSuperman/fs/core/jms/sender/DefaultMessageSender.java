package com.fashionSuperman.fs.core.jms.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.fashionSuperman.fs.core.jms.JMSConverter;
import com.fashionSuperman.fs.core.jms.SendMessage;
import com.fashionSuperman.fs.core.jms.SendMessageBody;
import com.fashionSuperman.fs.core.jms.SendMessageHeader;
import com.fashionSuperman.fs.core.util.StringUtil;
/**
 * 默认消息发送器
 * @description 
 * @author FashionSuperman
 * @date 2017年3月14日 下午1:47:04
 * @version 1.0
 */
public class DefaultMessageSender implements MessageSender {
	private JmsTemplate jmsTemplate;
	
	private Logger logger = LoggerFactory.getLogger(DefaultMessageSender.class);
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void sendMessage(SendMessage sendMessage , JMSConverter jmsConverter) {
		//检查前置条件
		if(sendMessage == null){
			logger.error("发送的消息为空");
			return;
		}
		SendMessageHeader sendMessageHeader = sendMessage.getSendMessageHeader();
		SendMessageBody sendMessageBody = sendMessage.getSendMessageBody();
		if(sendMessageHeader == null){
			logger.error("发送的消息头为空");
			return;
		}
		if(sendMessageBody == null){
			logger.error("发送的消息体为空");
			return;
		}
		if(jmsTemplate == null){
			logger.error("请设置消息模板");
			return;
		}
		
		//如果配置了消息转换器,转换消息
		if(jmsConverter != null){
			Object obj = sendMessageBody.getMessageBody();
			obj = jmsConverter.convert(obj);
			sendMessageBody.setMessageBody(obj);
		}
		
		//发送逻辑
		logger.info("消息 : " + sendMessageHeader.getMessageId() + " 发送开始=======");
		logger.info("消息内容 : " + StringUtil.toJson(sendMessage));
		
		this.jmsTemplate.send(new DefaultMessageCreator(sendMessage));
		
		logger.info("消息 : " + sendMessageHeader.getMessageId() + " 发送结束=======");
		
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	
	
}
