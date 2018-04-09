package org.forum.api.exception;

import java.util.Map;

public class EmptyInputException extends RuntimeException {
	
	private Map<String, String> messageMap;

	public EmptyInputException(Map<String, String> messageMap) {
		this.messageMap = messageMap;
	}

	public Map<String, String> getMessageMap() {
		return messageMap;
	}	
	
}
