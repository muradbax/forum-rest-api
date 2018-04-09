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

public class ErrorUtility {
	
	public static void checkIfEmpty(Message message) {
		boolean hasEmptyField = false;

		Map<String, String> messageMap = new HashMap<String, String>();
		if (message.getHeader().isEmpty()) {
			messageMap.put("header", "header is required");
			hasEmptyField = true;
		}
		if (message.getBody().isEmpty()) {
			messageMap.put("body", "body is required");
			hasEmptyField = true;
		}

		if (hasEmptyField) {
			throw new EmptyInputException(messageMap);
		}
	}
	
	public static String getNoDataFoundExceptionMessage(Long id) {
		return "No data with id " + id + " is found";
	}
	
	public static String getMismatchExceptionMessage(String field, String datatype) {
		return field + " should be of type " + datatype;
	}

	public static ResponseEntity<ErrorMessageSingle> generateErrorMessage(String message, HttpStatus httpsStatus) {
		ResponseEntity<ErrorMessageSingle> singleErrorMessage = new ResponseEntity<ErrorMessageSingle>(
				new ErrorMessageSingle(LocalDateTime.now(), httpsStatus.value(), message), httpsStatus);
		return singleErrorMessage;
	}

	public static ResponseEntity<ErrorMessageMultiple> generateErrorMessageMap(Map<String, String> messageMap,
			HttpStatus httpStatus) {
		ResponseEntity<ErrorMessageMultiple> errorMessageMultiple = new ResponseEntity<ErrorMessageMultiple>(
				new ErrorMessageMultiple(LocalDateTime.now(), httpStatus.value(), messageMap), httpStatus);
		return errorMessageMultiple;
	}

}
