package com.fashionSuperman.fs.core.jms;
/**
 * jms消息封装
 * @description 
 * @author FashionSuperman
 * @date 2017年3月10日 上午9:24:30
 * @version 1.0
 */
public class SendMessage {
	private SendMessageHeader sendMessageHeader;
	private SendMessageBody sendMessageBody;
	public SendMessageHeader getSendMessageHeader() {
		return sendMessageHeader;
	}
	public void setSendMessageHeader(SendMessageHeader sendMessageHeader) {
		this.sendMessageHeader = sendMessageHeader;
	}
	public SendMessageBody getSendMessageBody() {
		return sendMessageBody;
	}
	public void setSendMessageBody(SendMessageBody sendMessageBody) {
		this.sendMessageBody = sendMessageBody;
	}
	
	
}
