package org.forum.api.exception;

/**
 * Custom exception class, object of exception is thrown when entity with
 * specified parameters can't be found in database.
 */
public class DataNotFoundException extends RuntimeException {

	/** Custom exception message.*/
	private String message;

	/**
	 * @param message
	 *            custom exception message
	 */
	public DataNotFoundException(String message) {
		super(message);
	}

}
