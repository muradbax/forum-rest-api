package org.forum.api.exception;

import java.util.Map;

/**
 * Custom exception class, object of exception is thrown when fields of
 * specified data model are empty.
 */
public class EmptyInputException extends RuntimeException {

	/** Custom exception message map. */
	private Map<String, String> messageMap;

	/**
	 * @param messageMap
	 *            custom exception message map
	 */
	public EmptyInputException(Map<String, String> messageMap) {
		this.messageMap = messageMap;
	}

	/**
	 * Returns custom exception message map.
	 *
	 * @return custom exception message map
	 */
	public Map<String, String> getMessageMap() {
		return messageMap;
	}

}
