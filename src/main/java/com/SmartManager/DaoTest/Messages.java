package com.SmartManager.DaoTest;

public class Messages {

	private String content;
	private String messageType;
	public Messages(String content, String messageType) {
		super();
		this.content = content;
		this.messageType = messageType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
}
