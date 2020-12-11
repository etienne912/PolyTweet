package com.polyTweet.dao.message;

import com.polyTweet.dao.message.data.Data;

import java.io.Serializable;

/**
 * This class represents the message sent between the nodes through the sockets
 */
public class Message implements Serializable {

	private final MessageTypesEnum type;
	private final Data data;
	private final String messageId;

	/**
	 * Message's constructor
	 *
	 * @param pType      The message's type
	 * @param pMessageId The message's identifier
	 * @param pData      The message's data
	 */
	public Message(MessageTypesEnum pType, String pMessageId, Data pData) {
		type = pType;
		messageId = pMessageId;
		data = pData;
	}

	public MessageTypesEnum getType() {
		return type;
	}

	public String getMessageId() {
		return messageId;
	}

	public Data getData() {
		return data;
	}
}
