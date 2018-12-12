package org.forum.api.commons;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.forum.api.dto.ErrorMessageMultiple;
import org.forum.api.dto.ErrorMessageSingle;
import org.forum.api.dto.Message;
import org.forum.api.exception.EmptyInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Error management utility class with static methods to provide support for
 * error conditions checks, render correctly formed error messages transfer
 * object when exception is thrown and define correct formed error message for
 * different types of exceptions thrown.
 */
public class ErrorUtility {

	/**
	 * Checks if fields of specified {@link Message} model object are empty.
	 *
	 * @param message
	 *            {@link Message} object to check fields
	 * @return true, if successful
	 */
	public static boolean hasEmptyFields(Message message) {
		boolean hasEmptyFields = false;

		if (message.getHeader().isEmpty() || message.getBody().isEmpty()) {
			hasEmptyFields = true;
		}

		return hasEmptyFields;
	}

	/**
	 * Get corresponding error message map to pass to custom
	 * {@link EmptyInputException} when it is thrown.
	 *
	 * @param message
	 *            {@link Message} object for error message map formation
	 * @return error message map
	 */
	public static Map<String, String> getEmptyInputExceptionMessage(Message message) {
		Map<String, String> messageMap = new HashMap<String, String>();

		if (message.getHeader().isEmpty()) {
			messageMap.put("header", "header is required");
		}
		if (message.getBody().isEmpty()) {
			messageMap.put("body", "body is required");
		}

		return messageMap;
	}

	/**
	 * Gets corresponding error message to pass to custom
	 * {@link org.forum.api.exception.DataNotFoundException} when it is thrown.
	 *
	 * @param id
	 *            id for error message text formation
	 * @return error message for exception
	 */
	public static String getDataNotFoundExceptionMessage(Long id) {
		return "No data with id " + id + " is found";
	}

	/**
	 * Gets the mismatch exception message.
	 *
	 * @param field
	 *            incorrect field for error message text formation
	 * @param datatype
	 *            datatype that is required for error message text formation
	 * @return error message for exception
	 */
	public static String getMismatchExceptionMessage(String field, String datatype) {
		return field + " should be of type " + datatype;
	}

	/**
	 * Return correct message based on {@link ErrorMessageSingle} data model in a
	 * response entity for cases when error message is represented by string.
	 *
	 * @param message
	 *            error message string
	 * @param httpStatus
	 *            HTTP status
	 * @return response entity with correctly formed error message transfer object
	 */
	public static ResponseEntity<ErrorMessageSingle> generateErrorMessage(String message, HttpStatus httpStatus) {
		ResponseEntity<ErrorMessageSingle> singleErrorMessage = new ResponseEntity<ErrorMessageSingle>(
				new ErrorMessageSingle(LocalDateTime.now(), httpStatus.value(), message), httpStatus);
		return singleErrorMessage;
	}

	/**
	 * Return correct message on {@link ErrorMessageMultiple} data model in a
	 * response entity for cases when error message is represented by map.
	 *
	 * @param messageMap
	 *            error message map
	 * @param httpStatus
	 *            HTTP status
	 * @return response entity with correctly formed error message transfer object
	 */
	public static ResponseEntity<ErrorMessageMultiple> generateErrorMessageMap(Map<String, String> messageMap,
			HttpStatus httpStatus) {
		ResponseEntity<ErrorMessageMultiple> errorMessageMultiple = new ResponseEntity<ErrorMessageMultiple>(
				new ErrorMessageMultiple(LocalDateTime.now(), httpStatus.value(), messageMap), httpStatus);
		return errorMessageMultiple;
	}

}
