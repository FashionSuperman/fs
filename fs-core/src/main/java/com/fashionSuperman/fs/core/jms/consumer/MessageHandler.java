package com.fashionSuperman.fs.core.jms.consumer;

import com.fashionSuperman.fs.core.jms.JMSConverter;
@SuppressWarnings("rawtypes")
public class MessageHandler {
	
	private String sender;
	
	private String paramType;
	
	private MessageConsumer messageConsumer;
	
	private JMSConverter messageConverter;
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public MessageConsumer getMessageConsumer() {
		return messageConsumer;
	}
	public void setMessageConsumer(MessageConsumer messageConsumer) {
		this.messageConsumer = messageConsumer;
	}
	public JMSConverter getMessageConverter() {
		return messageConverter;
	}
	public void setMessageConverter(JMSConverter messageConverter) {
		this.messageConverter = messageConverter;
	}
	
	
	
}
