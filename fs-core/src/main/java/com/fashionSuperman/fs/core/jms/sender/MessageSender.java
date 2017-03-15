package com.fashionSuperman.fs.core.jms.sender;

import com.fashionSuperman.fs.core.jms.JMSConverter;
import com.fashionSuperman.fs.core.jms.SendMessage;

public interface MessageSender {
	public void sendMessage(SendMessage sendMessage , JMSConverter jmsConverter);
}
