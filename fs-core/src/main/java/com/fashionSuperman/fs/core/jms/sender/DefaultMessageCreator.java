package com.fashionSuperman.fs.core.jms.sender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.MessageCreator;

import com.fashionSuperman.fs.core.jms.JMSConstant;
import com.fashionSuperman.fs.core.jms.SendMessage;
import com.fashionSuperman.fs.core.jms.SendMessageBody;
import com.fashionSuperman.fs.core.jms.SendMessageHeader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultMessageCreator implements MessageCreator {
	private SendMessage sendMessage = null;
	private static ObjectMapper objectMapper = new ObjectMapper();
	private Logger logger = LoggerFactory.getLogger(DefaultMessageCreator.class);
	
	public DefaultMessageCreator(SendMessage sendMessage) {
		this.sendMessage = sendMessage;
	}
	
	@Override
	public Message createMessage(Session session) throws JMSException {
		TextMessage msg = session.createTextMessage();
		SendMessageHeader sendMessageHeader = this.sendMessage.getSendMessageHeader();
		SendMessageBody sendMessageBody = this.sendMessage.getSendMessageBody();
		if(sendMessageHeader != null){
			msg.setStringProperty(JMSConstant.MessageId, sendMessageHeader.getMessageId());
			msg.setStringProperty(JMSConstant.MessageType, sendMessageHeader.getMessageType());
			msg.setStringProperty(JMSConstant.ProjectId, sendMessageHeader.getProjectId());
			msg.setStringProperty(JMSConstant.Sender, sendMessageHeader.getSender());
			msg.setLongProperty(JMSConstant.TimeStamp, sendMessageHeader.getTimeStamp());
		}
		if(sendMessageBody != null && sendMessageBody.getMessageBody() != null){
			String data;
			try {
				data = objectMapper.writeValueAsString(sendMessageBody.getMessageBody());
				msg.setText(data);
			} catch (JsonProcessingException e) {
				logger.error("消息转化失败");
				e.printStackTrace();
				return null;
			}
		}
		return msg;
	}

}
