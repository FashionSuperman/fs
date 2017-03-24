package com.fashionSuperman.fs.core.jms.consumer;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fashionSuperman.fs.core.jms.JMSConstant;
import com.fashionSuperman.fs.core.jms.JMSConverter;
import com.fashionSuperman.fs.core.jms.SendMessageHeader;
import com.fashionSuperman.fs.core.util.StringUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReceiveMessageListener implements MessageListener{
	private Logger Logger = LoggerFactory.getLogger(ReceiveMessageListener.class);
	
	private List<MessageHandler> messagehandlerList;
	
	private ObjectMapper objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onMessage(Message message) {
		SendMessageHeader sendMessageHeader = new SendMessageHeader();
		try {
			sendMessageHeader.setMessageId(message.getStringProperty(JMSConstant.MessageId));
			sendMessageHeader.setMessageType(message.getStringProperty(JMSConstant.MessageType));
			sendMessageHeader.setProjectId(message.getStringProperty(JMSConstant.ProjectId));
			sendMessageHeader.setSender(message.getStringProperty(JMSConstant.Sender));
			sendMessageHeader.setTimeStamp(message.getLongProperty(JMSConstant.TimeStamp));
		} catch (JMSException e) {
			Logger.error("消息接收失败!!!!!");
			e.printStackTrace();
			return;
		}
		
		//
		MessageHandler messageHandler = null;
		for(MessageHandler mh : messagehandlerList){
			String sender = sendMessageHeader.getSender();
			if(sender.equalsIgnoreCase(mh.getSender())){
				messageHandler = mh;
				break;
			}
		}
		
		if(messageHandler == null){
			Logger.warn("没有找到消息 " + sendMessageHeader.getSender() + " 的处理器");
			return;
		}
		MessageConsumer messageConsumer = messageHandler.getMessageConsumer();
		if(messageConsumer == null){
			Logger.warn("没有找到消息 " + sendMessageHeader.getSender() + " 的处理器消费器");
			return;
		}
		
		String paramType = messageHandler.getParamType();
		if(StringUtil.isNullOrEmpty(paramType)){
			paramType = sendMessageHeader.getMessageType();
		}
		
		//反序列化消息
		TextMessage textMessage = (TextMessage) message;
		Object messageObje = null;
		try {
			String text = textMessage.getText();
			Logger.info("消息" + sendMessageHeader.getMessageId() + "接收开始body：" + text);
			Class<?> clazz = Class.forName(paramType);
			messageObje = this.objectMapper.readValue(text, clazz);
			
			JMSConverter jmsConverter = messageHandler.getMessageConverter();
			if(jmsConverter != null){
				messageObje = jmsConverter.convert(messageObje);
			}
			
			messageConsumer.consume(messageObje);
			
		} catch (JMSException e) {
			Logger.error("消息 " + sendMessageHeader.getSender() + " 获取消息text失败！！！");
			e.printStackTrace();
			return;
		} catch(ClassNotFoundException e){
			Logger.error("类型 " + paramType + " 反射失败！！！");
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public List<MessageHandler> getMessagehandlerList() {
		return messagehandlerList;
	}

	public void setMessagehandlerList(List<MessageHandler> messagehandlerList) {
		this.messagehandlerList = messagehandlerList;
	}

	
	
}
