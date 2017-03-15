package com.fashionSuperman.fs.core.jms;
/**
 * jms消息头封装
 * @description 
 * @author FashionSuperman
 * @date 2017年3月10日 上午9:25:14
 * @version 1.0
 */
public class SendMessageHeader {
	private String sender;//发送者,默认类名+方法名
	private String projectId;//发送者所属项目
	private String messageType;//消息数据类型
	private long timeStamp;//时间戳
	private String messageId;
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	
}
