package com.polyTweet.node.message;

import com.polyTweet.node.message.data.Data;

import java.io.Serializable;

public class Message implements Serializable {

	private final MessageTypesEnum type;
	private final Data data;
	private final String messageId;

	public Message(MessageTypesEnum pType, String pMessageId, Data pData) {
		type = pType;
		messageId = pMessageId;
		data = pData;
	}

	public MessageTypesEnum getType() {
		return type;
	}

	public Data getData() {
		return data;
	}

	public String getMessageId() {
		return messageId;
	}
}
